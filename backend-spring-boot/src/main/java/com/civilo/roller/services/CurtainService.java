package com.civilo.roller.services;

import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.repositories.CurtainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CurtainService {
    @Autowired
    CurtainRepository curtainRepository;

    // Get all 
    // Permite obtener un listado con toda la informacion asociada a las cortinas.
    public List<CurtainEntity> getCurtains(){
        return (List<CurtainEntity>) curtainRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de una cortina en especifico.
    public Optional<CurtainEntity> getCurtainById(Long id){
        return curtainRepository.findById(id);
    } 

    // Create
    // Permite guardar un objetivo del tipo "CurtainEntity" en la base de datos.
    public CurtainEntity createCurtain(CurtainEntity curtain){
        return curtainRepository.save(curtain);
    }

    // Permite verificar si existe una cortina registrada con el mismo nombre.
    public Optional<CurtainEntity> validateCurtain(String curtainName){
        return Optional.ofNullable(curtainRepository.findByCurtainType(curtainName));
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "CurtainEntity" en la base de datos.
    public CurtainEntity updateCurtain(Long curtainID, CurtainEntity curtain){

        CurtainEntity existingCurtain = curtainRepository.findById(curtainID)
            .orElseThrow(() -> new EntityNotFoundException("Cortina no encontrada con el ID: " + curtainID));

            existingCurtain.setCurtainType(curtain.getCurtainType());

            CurtainEntity updatedCurtain = curtainRepository.save(existingCurtain);
            return updatedCurtain;
    }

    // Delete all
    // Permite eliminar todas las cortinas de un sistema.
    public void deleteCurtains(){
        curtainRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar una cortina en especifico del sistema.
    public void deleteCurtainById(Long id){
        curtainRepository.deleteById(id);
    }

    // Permite verificar si existe una cortina en el sistema, segun el id ingresado.
    public boolean existCurtainById(Long id){
        return curtainRepository.findById(id).isPresent();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------//

    /* 
    //Se obtiene la ID de la cortina dependiendo del tipo de cortina
    public Long getCurtainIdByCurtainType(String curtainType){
        return curtainRepository.findIdByCurtainType(curtainType);

    }
    */

}
