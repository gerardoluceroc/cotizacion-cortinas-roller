package com.civilo.roller.controllers;

import com.civilo.roller.Entities.PipeEntity;
import com.civilo.roller.services.PipeService;
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
@RequestMapping("/pipes")
public class PipeController {
    @Autowired
    PipeService pipeService;

    // Permite obtener todos los tubos del sistema.
    @GetMapping()
    public List<PipeEntity> getPipes(){
        return pipeService.getPipes();
    }

    // Permite obtener un tubo en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<PipeEntity> getPipeById(@PathVariable long id){
        Optional<PipeEntity> pipe = pipeService.getPipe(id);
        if(!pipe.isPresent()){
            System.out.println("NO SE ENCONTRO EL TUBO \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<PipeEntity>(pipe.get(), HttpStatus.OK);
    }

    // Permite guardar un nuevo tubo en el sistema.
    @PostMapping()
    public ResponseEntity<?> createPipe(@RequestBody PipeEntity pipe){

        Optional<PipeEntity> existingPipe = pipeService.validatePipeName(pipe.getPipeName());

        //Se verifica si el tubo ya existe
        if(existingPipe.isPresent()){
            System.out.println("TUBO YA EXISTE\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El tubo ingresado ya existe"); 
        }

        pipeService.createPipe(pipe);
        System.out.println("TUBO GUARDADO CON EXITO\n");
        return ResponseEntity.ok().build();
    }

    // Permite actualizar información de un tubo.
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePipe(@PathVariable long id, @RequestBody PipeEntity pipe) {
        
        Optional<PipeEntity> checkPipe = pipeService.getPipe(id);
        
        if(!checkPipe.isPresent()){
            System.out.println("NO SE ENCONTRO EL TUBO CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El tubo con el ID especificado no existe."); 
        }

        Optional<PipeEntity> checkPipeName = pipeService.validatePipeName(pipe.getPipeName());

        if(checkPipeName.isPresent()){
            System.out.println("TUBO INGRESADO YA EXISTE\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El tubo ingresado ya existe"); 
        }

        pipeService.updatePipe(id,pipe);
        System.out.println("TUBO ACTUALIZADO CON EXITO\n");
        return ResponseEntity.ok().build(); 
    }

    // Permite eliminar todos los tubos del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deletePipes(){
        pipeService.deletePipes();
        return ResponseEntity.ok("SE ELIMINARON LOS TUBOS CORRECTAMENTE");
    }

    // Permite eliminar un tubo en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePipeById(@PathVariable Long id){
        if(!pipeService.existsPipeById(id)){
            System.out.println("NO SE ENCONTRO UN TUBO CON EL ID: "+ id + "\n");
            return ResponseEntity.notFound().build();
        }
        pipeService.deletePipeById(id);
        return ResponseEntity.ok("TUBO CON ID " + id + " ELIMINADO CORRECTAMENTE\n");

    }
}
