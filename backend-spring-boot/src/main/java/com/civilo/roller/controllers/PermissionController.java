package com.civilo.roller.controllers;

import com.civilo.roller.Entities.PermissionEntity;
import com.civilo.roller.services.PermissionService;
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
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    // Permite obtener todos los permisos del sistema.
    @GetMapping()
    public List<PermissionEntity> getPermissions(){
        return permissionService.getPermissions();
    }

    // Permite obtener un permiso en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<PermissionEntity> getPermissionById(@PathVariable long id){
        Optional<PermissionEntity> permission = permissionService.getPermissionById(id);
        if(!permission.isPresent()){
            System.out.println("NO SE ENCONTRO EL PERMISO \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<PermissionEntity>(permission.get(), HttpStatus.OK);
    }

    // Permite guardar un nuevo permission en el sistema.
    @PostMapping()
    public ResponseEntity<?> createPermission(@RequestBody PermissionEntity permission){

        Optional<PermissionEntity> existingPermission = permissionService.validatePermission(permission.getPermission());

        //Se verifica si el email esta registrado
        if(existingPermission.isPresent()){
            System.out.println("PERMISO YA REGISTRADO\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El permiso ingresado ya existe"); 
        }

        permissionService.createPermission(permission);
        System.out.println("PERMISO GUARDADO CON EXITO\n");
        return ResponseEntity.ok().build();
    }

    // Permite actualizar información de un permiso.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody PermissionEntity permission) {
        
        Optional<PermissionEntity> checkPermission = permissionService.getPermissionById(id);
        
        if(!checkPermission.isPresent()){
            System.out.println("NO SE ENCONTRO EL PERMISO CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El permiso con el ID especificado no existe."); 
        }
        
        Optional<PermissionEntity> checkAttributePermission = permissionService.validatePermission(permission.getPermission());
        
        if(checkAttributePermission.isPresent()){
            System.out.println("PERMISO YA EXISTE\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El permiso ingresado ya existe"); 
        }

        permissionService.updatePermission(id,permission);
        System.out.println("ACTUALIZADO CON EXITO\n");
        return ResponseEntity.ok().build(); 
    }

    // Permite eliminar todos los permisos del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deletePermissions(){
        permissionService.deletePermissions();
        return ResponseEntity.ok("SE ELIMINARON LOS PERMISOS CORRECTAMENTE");
    }

    // Permite eliminar un permiso en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePermissionById(@PathVariable Long id){
        if(!permissionService.existsPermissionById(id)){
            System.out.println("NO SE ENCONTRO UN PERMISO CON EL ID: "+ id + "\n");
            return ResponseEntity.notFound().build();
        }
        permissionService.deletePermissionById(id);
        return ResponseEntity.ok("PERMISO CON ID " + id + " ELIMINADO CORRECTAMENTE\n");

    }


}