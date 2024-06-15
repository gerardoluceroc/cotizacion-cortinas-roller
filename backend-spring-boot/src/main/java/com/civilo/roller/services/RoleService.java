package com.civilo.roller.services;

import com.civilo.roller.Entities.RoleEntity;
import com.civilo.roller.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    // Get all
    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los roles
    public List<RoleEntity> getRoles(){
        return (List<RoleEntity>) roleRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de un rol en especifico.
    public Optional<RoleEntity> getRoleById(Long id){
        return roleRepository.findById(id);
    }

    // Create
    // Permite guardar un objeto del tipo "RoleEntity" en la base de datos.
    public RoleEntity createRole(RoleEntity role){   
        return roleRepository.save(role);  
    }

    // Permite verificar si existe el tipo de cuenta ingresado.
    public Optional<RoleEntity> validateAccountType(String accountType){
        return Optional.ofNullable(roleRepository.findByAccountType(accountType));  
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "RoleEntity" en la base de datos.
    public RoleEntity updateRole(Long roleID, RoleEntity role){
        RoleEntity existingRole = roleRepository.findById(roleID)
            .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con el ID: " + roleID));

        existingRole.setAccountType(role.getAccountType()); //No deberia poder cambiarse
        
        RoleEntity updatedRole = roleRepository.save(existingRole);
        return updatedRole;
    }

    // Delete all
    // Permite eliminar todos los roles del sistema.
    public void deleteRoles() {
        roleRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar un rol en especifico del sistema.
    public void deleteRoleById(Long id){
        roleRepository.deleteById(id);
    }
    
    // Permite verificar si existe un usario en el sistema, segun el id ingresado.
    public boolean existsRoleById(Long id){
        return roleRepository.findById(id).isPresent();
    }

    //Se obtiene la ID del rol dependiendo del tipo de cuenta
    public Long getRoleIdByAccountType(String accountType){
        return roleRepository.findIdByAccountType(accountType);

    }

}
