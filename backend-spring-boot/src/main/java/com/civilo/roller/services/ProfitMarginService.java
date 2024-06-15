package com.civilo.roller.services;

import com.civilo.roller.Entities.ProfitMarginEntity;
import com.civilo.roller.repositories.ProfitMarginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service

public class ProfitMarginService {
    @Autowired
    ProfitMarginRepository profitMarginRepository;

    // Get the last
    public ProfitMarginEntity getLastProfitMargin(){
        List<ProfitMarginEntity> profitMarginEntities = (List<ProfitMarginEntity>) profitMarginRepository.findAll();
        int size = profitMarginEntities.size();
        return profitMarginEntities.get(size - 1);
    }

    // Get All
    // El siguiente método retorna un listado el cual contiene TODA la información asociada a los margenes de utilidad
    public List<ProfitMarginEntity> getProfitMargins(){
        return (List<ProfitMarginEntity>) profitMarginRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de un margen de utilidad en especifico.
    public Optional<ProfitMarginEntity> getProfitMarginById(Long id){
        return profitMarginRepository.findById(id);
    }

    // Create
    // Permite guardar un objeto del tipo "ProfitMarginEntity" en la base de datos.
    public ProfitMarginEntity createProfitMargin(ProfitMarginEntity profitMargin){   
        return profitMarginRepository.save(profitMargin);  
    }

    // Permite verificar si existe el valor decimal del margen de utilidad ingresado.
    public Optional<ProfitMarginEntity> validateDecimalProfitMargin(float decimalProfitMargin){
        return Optional.ofNullable(profitMarginRepository.findByDecimalProfitMargin(decimalProfitMargin)); 
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "ProfitMarginEntity" en la base de datos.
    public ProfitMarginEntity updateProfitMargin(Long profitMarginID, ProfitMarginEntity profitMargin){
        ProfitMarginEntity existingProfitMargin = profitMarginRepository.findById(profitMarginID)
            .orElseThrow(() -> new EntityNotFoundException("Margen de utilidad no encontrado con el ID: " + profitMarginID));

        existingProfitMargin.setProfitMarginPercentaje(profitMargin.getProfitMarginPercentaje());
        existingProfitMargin.setDecimalProfitMargin(profitMargin.getDecimalProfitMargin());
        
        ProfitMarginEntity updatedProfitMargin = profitMarginRepository.save(existingProfitMargin);
        return updatedProfitMargin;
    }

    // Delete all
    // Permite eliminar todos los margenes de utilidad de un sistema.
    public void deleteProfitMargins() {
        profitMarginRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar un margen de utilidad en especifico del sistema.
    public void deleteProfitMarginById(Long id){
        profitMarginRepository.deleteById(id);
    }

    // Permite verificar si existe un margen de utilidad en el sistema, segun el id ingresado.
    public boolean existsProfitMarginById(Long id){
        return profitMarginRepository.findById(id).isPresent();
    }
}
