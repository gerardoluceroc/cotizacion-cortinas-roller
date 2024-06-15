package com.civilo.roller.controllers;

import com.civilo.roller.Entities.StatusEntity;
import com.civilo.roller.services.StatusService;
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
@RequestMapping("/status")
public class StatusController {
    @Autowired
    StatusService statusService;

    // Permite obtener todos los estados del sistema.
    @GetMapping()
    public List<StatusEntity> getStatus(){
        return statusService.getStatus();
    }

    // Permite obtener un estado en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<StatusEntity> getStatusById(@PathVariable long id){
        Optional<StatusEntity> status = statusService.getStatusById(id);
        if(!status.isPresent()){
            System.out.println("NO SE ENCONTRO EL ESTADO \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<StatusEntity>(status.get(), HttpStatus.OK);
    }

    // Permite guardar un nuevo estado en el sistema.
    @PostMapping()
    public ResponseEntity<?> createStatus(@RequestBody StatusEntity status){

        Optional<StatusEntity> existingStatus = statusService.validateStatusName(status.getStatusName());

        //Se verifica si el estado ya existe
        if(existingStatus.isPresent()){
            System.out.println("ESTADO YA EXISTE\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El estado ingresado ya existe"); 
        }

        statusService.createStatus(status);
        System.out.println("ESTADO GUARDADO CON EXITO\n");
        return ResponseEntity.ok().build();
    }

    // Permite actualizar información de un estado.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable long id, @RequestBody StatusEntity status) {
        
        Optional<StatusEntity> checkStatus = statusService.getStatusById(id);
        
        if(!checkStatus.isPresent()){
            System.out.println("NO SE ENCONTRO EL ESTADO CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El estado con el ID especificado no existe."); 
        }

        Optional<StatusEntity> checkStatusName = statusService.validateStatusName(status.getStatusName());

        if(checkStatusName.isPresent()){
            System.out.println("ESTADO INGRESADO YA EXISTE\n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El estado ingresado ya existe"); 
        }

        statusService.updateStatus(id,status);
        System.out.println("ESTADO ACTUALIZADO CON EXITO\n");
        return ResponseEntity.ok().build(); 
    }

    // Permite eliminar todos los estados del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deleteStatus(){
        statusService.deleteStatus();
        return ResponseEntity.ok("SE ELIMINARON LOS ESTADOS CORRECTAMENTE");
    }

    // Permite eliminar un estado en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStatusById(@PathVariable Long id){
        if(!statusService.existsStatusById(id)){
            System.out.println("NO SE ENCONTRO UN ESTADO CON EL ID: "+ id + "\n");
            return ResponseEntity.notFound().build();
        }
        statusService.deleteStatusById(id);
        return ResponseEntity.ok("ESTADO CON ID " + id + " ELIMINADO CORRECTAMENTE\n");

    }
}
