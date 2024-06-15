package com.civilo.roller.services;

import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;

    // Get All
    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los estados
    public List<StatusEntity> getStatus(){
        return (List<StatusEntity>) statusRepository.findAll();
    }
    
    // Get by id
    // Permite obtener la informacion de un estado en especifico.
    public Optional<StatusEntity> getStatusById(Long id){
        return statusRepository.findById(id);
    }

    // Create
    // Permite guardar un objeto del tipo "StatusEntity" en la base de datos.
    public StatusEntity createStatus(StatusEntity status){   
        return statusRepository.save(status);  
    }

    // Permite verificar si existe el tipo de cuenta ingresado.
    public Optional<StatusEntity> validateStatusName(String statusName){
        return Optional.ofNullable(statusRepository.findByStatusName(statusName));  
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "StatusEntity" en la base de datos.
    public StatusEntity updateStatus(Long statusID, StatusEntity status){
        StatusEntity existingStatus = statusRepository.findById(statusID)
            .orElseThrow(() -> new EntityNotFoundException("Estado no encontrado con el ID: " + statusID));

        existingStatus.setStatusName(status.getStatusName());
        
        StatusEntity updatedStatus = statusRepository.save(existingStatus);
        return updatedStatus;
    }

    // Delete all
    // Permite eliminar todos los estados de un sistema.
    public void deleteStatus() {
        statusRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar un estado en especifico del sistema.
    public void deleteStatusById(Long id){
        statusRepository.deleteById(id);
    }
    
    // Permite verificar si existe un estado en el sistema, segun el id ingresado.
    public boolean existsStatusById(Long id){
        return statusRepository.findById(id).isPresent();
    }

    //Se obtiene la ID del estado dependiendo del nombre del estado
    public Long getStatusIdByStatusName(String statusName){
        return statusRepository.findIdByStatusName(statusName);

    }
}
