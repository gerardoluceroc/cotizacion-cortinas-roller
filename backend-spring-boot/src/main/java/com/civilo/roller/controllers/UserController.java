package com.civilo.roller.controllers;

import com.civilo.roller.Entities.DataTransferObjectEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.services.PermissionService;
import com.civilo.roller.services.RoleService;
import com.civilo.roller.services.SellerService;
import com.civilo.roller.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;

    @Autowired 
    RoleService roleService;

    @Autowired
    SellerService sellerService;

    // Permite obtener todos los usuarios del sistema.
    @GetMapping()
    public List<UserEntity> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{email}")
    public UserEntity getUserByEmail(@PathVariable("email") String email){
        return this.userService.getUserByEmail(email);
    }
 
    /*
    // Permite obtener un usuario en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable long id){
        Optional<UserEntity> user = userService.getUserById(id);
        if(!user.isPresent()){
            System.out.println("NO SE ENCONTRO EL USUARIO \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<UserEntity>(user.get(), HttpStatus.OK);
    }
    */

    // Permite guardar entidad usuario.
    @PostMapping()
    public UserEntity saveUser(@RequestBody UserEntity user) {
        return this.userService.createUser(user);
    }

    // Permite guardar un nuevo usuario en el sistema.
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user){

        Optional<UserEntity> existingUser = userService.validateEmail(user.getEmail());

        //Se verifica si el email esta registrado
        if(existingUser.isPresent()){
            System.out.println("CORREO YA REGISTRADO\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ingresado ya existe"); 
        }

        //Se obtiene la id del rol dependiendo del tipo de cuenta
        String accountType = user.getRole().getAccountType();
        Long IdRol = roleService.getRoleIdByAccountType(accountType);

        //Se guarda la id del rol dentro del usuario
        user.getRole().setRoleID(IdRol);

        if (accountType.equals("Vendedor")){
            SellerEntity seller = new SellerEntity(null, user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getRut(), user.getPhoneNumber(), user.getCommune(), user.getBirthDate(), user.getAge(), user.getStartTime(), user.getEndTime(), user.getRole(), null, false, null, null, 0);
            sellerService.saveSeller(seller);
            System.out.println("GUARDADO CON EXITO\n");
            return ResponseEntity.ok().build();
        }
        else {
            userService.createUser(user);
            System.out.println("GUARDADO CON EXITO\n");
            return ResponseEntity.ok().build();
        }
    }

    // Permite actualizar información de un usuario.
    @PostMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserEntity user) {
        Optional<UserEntity> checkUser = userService.getUserById(id);
        
        if(!checkUser.isPresent()){
            System.out.println("NO SE ENCONTRO EL USUARIO CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario con el ID especificado no se encuentra registrado."); 
        }

        Optional<UserEntity> checkEmail = userService.validateEmail(user.getEmail());
        /*
        if(checkEmail.isPresent()){
            System.out.println("CORREO EN USO \n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El email a modificar ya se encuentra registrado"); 
        }*/

        userService.updateUser(id,user);
        System.out.println("ACTUALIZADO CON EXITO \n");
        return ResponseEntity.ok().build(); 
    }

    // Permite eliminar todos los usuarios del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deleteUsers(){
        userService.deleteUsers();
        return ResponseEntity.ok("SE ELIMINARON LOS USUARIOS CORRECTAMENTE");
    }

    // Permite eliminar un usuario en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        if(!userService.existsUserById(id)){
            System.out.println("NO SE ENCONTRO UN USUARIO CON EL ID: "+ id + "\n");
            return ResponseEntity.notFound().build();
        }
        userService.deleteUserById(id);
        return ResponseEntity.ok("USUARIO CON ID " + id + " ELIMINADO CORRECTAMENTE \n");
    }
 
    //------------------------------------------------------------------------------------------------------------------------------------------------//
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity userDTO, HttpServletRequest request){
        UserEntity user = userService.validateUser(userDTO.getEmail(), userDTO.getPassword());
        if (user == null){
            System.out.println("CORREO O CONTRASEÑA INCORRECTA\n");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        System.out.println("SESIÓN INICIADA CORRECTAMENTE");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            System.out.println("SESIÓN CERRADA CORRECTAMENTE");
            session.invalidate();
            return ResponseEntity.ok().build();
        }
        System.out.println("NO HAY SESIÓN INICIADA");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/session")
    public String getSession(HttpSession session) {
        String sessionId = session.getId();
        UserEntity user = (UserEntity) session.getAttribute("user");
        return "Session ID: " + sessionId + "\n" +
                "User email      : " + user.getEmail() + "\n" +
                "User full name  : " + user.getName() + " " + user.getSurname() + "\n" +
                "User role       : " + user.getRole().getAccountType() + "\n" +
                "User role ID    : " + user.getRole().getRoleID() + "\n" +
                "User permissions: " + permissionService.rolePermissions(user.getRole().getRoleID()) + "\n";
    }

    @GetMapping("/currentSession") 
    public UserEntity getCurrentSession(@RequestParam("email") String emailSession, @RequestParam("password") String passwordSession) { 
        UserEntity user = userService.validateUser(emailSession, passwordSession); 
        return user; 
    }

    @PostMapping("/loginExecutive")
    public ResponseEntity<?> loginExecutive(@RequestBody UserEntity userDTO, HttpServletRequest request){
        UserEntity user = userService.validateUser(userDTO.getEmail(), userDTO.getPassword());
        if (user == null){
            System.out.println("CORREO O CONTRASEÑA INCORRECTA\n");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else if (user.getRole().getAccountType().equals("Ejecutivo")){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            System.out.println("SESIÓN INICIADA CORRECTAMENTE");
            return ResponseEntity.ok().build();
        }
        System.out.println("NO FIGURA CON EL ROL INGRESADO\n");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/loginAdmin")
    public ResponseEntity<?> loginAdmin(@RequestBody UserEntity userDTO, HttpServletRequest request){
        UserEntity user = userService.validateUser(userDTO.getEmail(), userDTO.getPassword());
        if (user == null){
            System.out.println("CORREO O CONTRASEÑA INCORRECTA\n");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else if (user.getRole().getAccountType().equals("Administrador")){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            System.out.println("SESIÓN INICIADA CORRECTAMENTE");
            return ResponseEntity.ok().build();
        }
        System.out.println("NO FIGURA CON EL ROL INGRESADO\n");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
