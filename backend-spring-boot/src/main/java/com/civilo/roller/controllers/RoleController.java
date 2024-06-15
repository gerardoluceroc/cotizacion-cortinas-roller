package com.civilo.roller.controllers;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.services.PermissionService;
import com.civilo.roller.services.RoleService;
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
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    // Permite obtener todos los roles del sistema.
    @GetMapping()
    public List<RoleEntity> getRoles(){
        return roleService.getRoles();
    }

    // Permite obtener un rol en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<RoleEntity> getRoleById(@PathVariable long id){
        Optional<RoleEntity> role = roleService.getRoleById(id);
        if(!role.isPresent()){
            System.out.println("NO SE ENCONTRO EL ROL \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<RoleEntity>(role.get(), HttpStatus.OK);
    }

    // Permite guardar un nuevo rol en el sistema.
    // (Se definió como alcance del proyecto que la plataforma contaria solo con 4 roles definidos)
    @PostMapping()
    public ResponseEntity<?> createRole(@RequestBody RoleEntity role){

        Optional<RoleEntity> existingRole = roleService.validateAccountType(role.getAccountType());

        //Se verifica si ya está definido un rol para el tipo de cuenta ingresado.
        if(existingRole.isPresent()){
            System.out.println("ESTE ROL YA EXISTE\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe el rol para el tipo de cuenta ingresado"); 
        }

        //PENDIENTE: ¿De que manera se le asignaran los permisos a un nuevo rol desconocido?

        roleService.createRole(role);
        System.out.println("ROL GUARDADO CON EXITO\n");
        return ResponseEntity.ok().build();
    }

    // Permite actualizar información de un rol.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable long id, @RequestBody RoleEntity role) {
        
        Optional<RoleEntity> checkRole = roleService.getRoleById(id);
        
        if(!checkRole.isPresent()){
            System.out.println("NO SE ENCONTRO EL ROL CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El rol con el ID especificado no existe."); 
        }

        Optional<RoleEntity> checkAccountType = roleService.validateAccountType(role.getAccountType());

        if(checkAccountType.isPresent()){
            System.out.println("TIPO DE CUENTA INGRESADO YA EXISTE\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El tipo de cuenta ingresado ya existe"); 
        }

        roleService.updateRole(id,role);
        System.out.println("ROL ACTUALIZADO CON EXITO\n");
        return ResponseEntity.ok().build(); 
    }

    // Permite eliminar todos los roles del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deleteRoles(){
        roleService.deleteRoles();
        return ResponseEntity.ok("SE ELIMINARON LOS ROLES CORRECTAMENTE");
    }

    // Permite eliminar un rol en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable Long id){
        if(!roleService.existsRoleById(id)){
            System.out.println("NO SE ENCONTRO UN ROL CON EL ID: "+ id + "\n");
            return ResponseEntity.notFound().build();
        }
        roleService.deleteRoleById(id);
        return ResponseEntity.ok("ROLE CON ID " + id + " ELIMINADO CORRECTAMENTE\n");

    }
    
}