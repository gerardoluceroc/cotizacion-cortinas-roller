package com.civilo.roller.controllers;

import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.services.CoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/coverages")
public class CoverageController {
    @Autowired
    CoverageService coverageService;

    // Permite obtener todas las coberturas del sistema.
    @GetMapping()
    public List<CoverageEntity> getCoverages(){
        return coverageService.getCoverages();
    }
    
    // Permite obtener un usuario en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<CoverageEntity> getCoverageById(@PathVariable long id){
        Optional<CoverageEntity> coverage = coverageService.getCoverageById(id);
        if(!coverage.isPresent()){
            System.out.println("NO SE ENCONTRO LA COMUNA \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<CoverageEntity>(coverage.get(), HttpStatus.OK);
    } 

    // Permite guardar entidad cobertura.
    @PostMapping()
    public CoverageEntity saveCoverage(@RequestBody CoverageEntity coverage){
        return this.coverageService.createCoverage(coverage);
    }

    // Permite guardar una nueva comuna en el sistema.
    @PostMapping("/register")
    public ResponseEntity<?> createCoverage(@RequestBody CoverageEntity coverage){

        Optional<CoverageEntity> existingCommune = coverageService.validateCoverage(coverage.getCommune());
        
        //Se verifica si la comuna esta registrada
        if(existingCommune.isPresent()){
            System.out.println("COMUNA YA REGISTRADA \n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La comuna ingresada ya existe");
        }

        coverageService.createCoverage(coverage);
        System.out.println("GUARDADO CON EXITO \n");
        return ResponseEntity.ok().build();
    }

    // Permite actualizar informacion de una cobertura (comuna).
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCoverage(@PathVariable long id, @RequestBody CoverageEntity coverage){

        Optional<CoverageEntity> checkCommune = coverageService.getCoverageById(id);

        if(!checkCommune.isPresent()){
            System.out.println("NO SE ENCONTRO LA COMUNA CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La comuna con el ID especificado no se encuentra registrada.");
        }

        Optional<CoverageEntity> checkName = coverageService.validateCoverage(coverage.getCommune());

        if(checkName.isPresent()){
            System.out.println("NOMBRE EN USO \n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre a modificar ya se encuentra registrado");
        }

        coverageService.updateCoverage(id,coverage);
        System.out.println("ACTUALIZADO CON EXITO \n");
        return ResponseEntity.ok().build();
    }
    
    // Permite eliminar todas las comunas del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deleteCoverages(){
        coverageService.deleteCoverages();
        return ResponseEntity.ok("SE ELIMINARON LAS COMUNAS CORRECTAMENTE");
    }

    // Permite eliminar una comuna en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoverageById(@PathVariable Long id){
        if(!coverageService.existCoverageById(id)){
            System.out.println("NO SE ENCONTRO UNA COMUNA CON EL ID: " + id + " \n");
            return ResponseEntity.notFound().build();
        }
        coverageService.deleteCoverageById(id);
        return ResponseEntity.ok("COMUNA CON ID " + id + " ELIMINADA CORRECTAMENTE \n");
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------//


}
