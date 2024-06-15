package com.civilo.roller.controllers;

import com.civilo.roller.Entities.ProfitMarginEntity;
import com.civilo.roller.services.ProfitMarginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/profitMargins")
public class ProfitMarginController {
    @Autowired
    ProfitMarginService profitMarginService;

    // Permite obtener todos los margenes de utilidad utilizados en el sistema.
    @GetMapping()
    public List<ProfitMarginEntity> getProfitMargins(){
        return profitMarginService.getProfitMargins();
    }

    // Permite obtener un margenes de utilidad en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<ProfitMarginEntity> getProfitMarginById(@PathVariable long id){
        Optional<ProfitMarginEntity> profitMargin = profitMarginService.getProfitMarginById(id);
        if(!profitMargin.isPresent()){
            System.out.println("NO SE ENCONTRO EL MARGEN DE UTILIDAD \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<ProfitMarginEntity>(profitMargin.get(), HttpStatus.OK);
    }

    // Permite guardar un nuevo margen de utilidad en el sistema.
    @PostMapping()
    public ResponseEntity<?> createProfitMargin(@RequestBody ProfitMarginEntity profitMargin){

        Optional<ProfitMarginEntity> existingProfitMargin = profitMarginService.validateDecimalProfitMargin(profitMargin.getDecimalProfitMargin());

        //Se verifica si el margen de utilidad ya existe
        if(existingProfitMargin.isPresent()){
            System.out.println("ESTE MARGEN DE UTILIDAD YA EXISTE\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe el margen de utilidad ingresado"); 
        }

        profitMarginService.createProfitMargin(profitMargin);
        System.out.println("MARGEN DE UTILIDAD GUARDADO CON EXITO\n");
        return ResponseEntity.ok().build();
    }

    // Permite actualizar información de un margen de utilidad.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfitMargin(@PathVariable long id, @RequestBody ProfitMarginEntity profitMargin) {
        
        Optional<ProfitMarginEntity> checkProfitMargin = profitMarginService.getProfitMarginById(id);
        
        if(!checkProfitMargin.isPresent()){
            System.out.println("NO SE ENCONTRO EL MARGEN DE UTILIDAD CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El margen de utilidad con el ID especificado no existe."); 
        }

        Optional<ProfitMarginEntity> checkDecimalProfitMargin = profitMarginService.validateDecimalProfitMargin(profitMargin.getDecimalProfitMargin());

        if(checkDecimalProfitMargin.isPresent()){
            System.out.println("MARGEN DE UTILIDAD INGRESADO YA EXISTE\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El margen de utilidad ingresado ya existe"); 
        }

        profitMarginService.updateProfitMargin(id,profitMargin);
        System.out.println("MARGEN DE UTILIDAD ACTUALIZADO CON EXITO\n");
        return ResponseEntity.ok().build(); 
    }

    // Permite eliminar todos los margenes de utilidad del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deleteProfitMargins(){
        profitMarginService.deleteProfitMargins();
        return ResponseEntity.ok("SE ELIMINARON LOS MERGENES DE UTILIDAD CORRECTAMENTE");
    }

    // Permite eliminar un margen de utilidad en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfitMarginById(@PathVariable Long id){
        if(!profitMarginService.existsProfitMarginById(id)){
            System.out.println("NO SE ENCONTRO UN MARGEN DE UTILIDAD CON EL ID: "+ id + "\n");
            return ResponseEntity.notFound().build();
        }
        profitMarginService.deleteProfitMarginById(id);
        return ResponseEntity.ok("MARGEN DE UTILIDAD CON ID " + id + " ELIMINADO CORRECTAMENTE\n");

    }
}
