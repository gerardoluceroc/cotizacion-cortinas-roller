package com.civilo.roller.services;

import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.repositories.CoverageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civilo.roller.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CoverageService {
    @Autowired
    CoverageRepository coverageRepository;

    // Get all
    // Permite obtener un listado con toda la informacion asociada a las coberturas.
    public List<CoverageEntity> getCoverages(){
        return (List<CoverageEntity>) coverageRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de una cobertura en especifico.
    public Optional<CoverageEntity> getCoverageById(Long id){
        return coverageRepository.findById(id);
    }   

    // Create
    // Permite guardar un objeto del tipo "CoverageEntity" en la base de datos.
    public CoverageEntity createCoverage(CoverageEntity coverage){
        return coverageRepository.save(coverage);
    }
 
    // Permite verificar si existe una comuna registrada con el mismo nombre.
    public Optional<CoverageEntity> validateCoverage(String commune){
        return Optional.ofNullable(coverageRepository.findByCommune(commune));
    }

    // Update
    // Permite actualizar los datos de un objeto del tipo "CoverageEntity" en la base de datos.
    public CoverageEntity updateCoverage(Long coverageID, CoverageEntity coverage){

        CoverageEntity existingCommune = coverageRepository.findById(coverageID)
            .orElseThrow(() -> new EntityNotFoundException("Comuna no encontrada con el ID: " + coverageID));

            existingCommune.setCommune(coverage.getCommune());

            CoverageEntity updatedCoverage = coverageRepository.save(existingCommune);
            return updatedCoverage;
    }

    // Delete all
    // Permite eliminar todas las comunas de un sistema.
    public void deleteCoverages(){
        coverageRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar una comuna en especifico del sistema.
    public void deleteCoverageById(Long id){
        coverageRepository.deleteById(id);
    }

    // Permite verificar si existe una comuna en el sistema, segun el id ingresado.
    public boolean existCoverageById(Long id){
        return coverageRepository.findById(id).isPresent();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------//

    public Optional<CoverageEntity> getCoverage(Long id){
        return coverageRepository.findById(id);
    }

    public Long getCoverageIdByCommune(String commune){
        return coverageRepository.findByCommune(commune).getCoverageID();
    }
}
