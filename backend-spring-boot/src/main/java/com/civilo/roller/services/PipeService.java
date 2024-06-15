package com.civilo.roller.services;

import com.civilo.roller.Entities.PipeEntity;
import com.civilo.roller.repositories.PipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PipeService {
    @Autowired
    PipeRepository pipeRepository;

    // Get all
    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los tubos
    public List<PipeEntity> getPipes(){
        return (List<PipeEntity>) pipeRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de un pipe en especifico.
    public Optional<PipeEntity> getPipe(Long id){
        return pipeRepository.findById(id);
    }

    // Create
    // Permite guardar un objeto del tipo "PipeEntity" en la base de datos.
    public PipeEntity createPipe(PipeEntity pipe){   
        return pipeRepository.save(pipe);  
    }

    // Permite verificar si existe el tipo de tubo ingresado.
    public Optional<PipeEntity> validatePipeName(String pipeName){
        return Optional.ofNullable(pipeRepository.findByPipeName(pipeName));  
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "PipeEntity" en la base de datos.
    public PipeEntity updatePipe(Long pipeID, PipeEntity pipe){
        PipeEntity existingPipe = pipeRepository.findById(pipeID)
            .orElseThrow(() -> new EntityNotFoundException("Pipe no encontrado con el ID: " + pipeID));

        existingPipe.setPipeName(pipe.getPipeName());
        
        PipeEntity updatedPipe = pipeRepository.save(existingPipe);
        return updatedPipe;
    }

    // Delete all
    // Permite eliminar todos los tubos de un sistema.
    public void deletePipes() {
        pipeRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar un tubo en especifico del sistema.
    public void deletePipeById(Long id){
        pipeRepository.deleteById(id);
    }
    
    // Permite verificar si existe un tubo en el sistema, segun el id ingresado.
    public boolean existsPipeById(Long id){
        return pipeRepository.findById(id).isPresent();
    }
}
