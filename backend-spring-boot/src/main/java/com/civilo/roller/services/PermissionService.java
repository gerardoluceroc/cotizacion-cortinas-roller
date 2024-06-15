package com.civilo.roller.services;

import com.civilo.roller.Entities.PermissionEntity;
import com.civilo.roller.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;

    // Get all
    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los permisos
    public List<PermissionEntity> getPermissions(){
        return (List<PermissionEntity>) permissionRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de un permiso en especifico.
    public Optional<PermissionEntity> getPermissionById(Long id){
        return permissionRepository.findById(id);
    }

    // El siguiente método permite guardar un objeto del tipo "PermissionEntity" en la base de datos
    public PermissionEntity savePermission(PermissionEntity permission){
        return permissionRepository.save(permission);
    }

    // Create
    // Permite guardar un objeto del tipo "PermissionEntity" en la base de datos.
    public PermissionEntity createPermission(PermissionEntity permission){   
        return permissionRepository.save(permission);  
    }

    // Permite verificar si existe el tipo de cuenta ingresado.
    public Optional<PermissionEntity> validatePermission(String permission){
        return Optional.ofNullable(permissionRepository.findByPermission(permission));  
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "PermissionEntity" en la base de datos.
    public PermissionEntity updatePermission(Long permissionID, PermissionEntity permission){
        PermissionEntity existingPermission = permissionRepository.findById(permissionID)
            .orElseThrow(() -> new EntityNotFoundException("Permiso no encontrado con el ID: " + permissionID));

        existingPermission.setPermission(permission.getPermission()); //No deberia poder cambiarse
        
        PermissionEntity updatedPermission = permissionRepository.save(existingPermission);
        return updatedPermission;
    }

    // Delete all
    // Permite eliminar todos los permisos del sistema.
    public void deletePermissions() {
        permissionRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar un permiso en especifico del sistema.
    public void deletePermissionById(Long id){
        permissionRepository.deleteById(id);
    }
    
    // Permite verificar si existe un permiso en el sistema, segun el id ingresado.
    public boolean existsPermissionById(Long id){
        return permissionRepository.findById(id).isPresent();
    }

    //----------------------------------------------------------------------------------------------

    public String rolePermissions(Long roleId){
        List<PermissionEntity> permissionEntityList = getPermissions();
        String permisos = "";
        for (int i = 0; i < permissionEntityList.size(); i++){
            if (permissionEntityList.get(i).getRole().getRoleID() == roleId){
                permisos = permisos + permissionEntityList.get(i).getPermission() + "\n                  ";
            }
        }
        return permisos;
    }

}

