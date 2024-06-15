package com.civilo.roller.controllers;

import com.civilo.roller.Entities.CurtainEntity;
import com.civilo.roller.services.CurtainService;
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
@RequestMapping("/curtains")
public class CurtainController {
    @Autowired
    CurtainService curtainService;

    // Permite obtener todas las cortinas del sistema.
    @GetMapping()
    public List<CurtainEntity> getCurtains(){
        return curtainService.getCurtains();
    }

    // Permite obtener una cortina en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<CurtainEntity> getCurtainById(@PathVariable long id){
        Optional<CurtainEntity> curtain = curtainService.getCurtainById(id);
        if(!curtain.isPresent()){
            System.out.println("NO SE ENCONTRO LA CORTINA \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CurtainEntity>(curtain.get(), HttpStatus.OK);
    }

    // Permite guardar entidad cortina.
    @PostMapping()
    public CurtainEntity saveCurtain(@RequestBody CurtainEntity curtain){
        return this.curtainService.createCurtain(curtain);
    }

    // Permite guardar una nueva cortina en el sistema.
    @PostMapping("/register")
    public ResponseEntity<?> createCurtain(@RequestBody CurtainEntity curtain){

        Optional<CurtainEntity> existingCurtain = curtainService.validateCurtain(curtain.getCurtainType()); 
    
        //Se verifica si la cortina esta registrada
        if(existingCurtain.isPresent()){
            System.out.println("CORTINA YA REGISTRADA \n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La cortina ingresada ya existe");
        }

        curtainService.createCurtain(curtain);
        System.out.println("GUARDADO CON EXITO \n");
        return ResponseEntity.ok().build();    
    }

    // Permite actualizar informacion de una cortina.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCurtain(@PathVariable long id, @RequestBody CurtainEntity curtain){

        Optional<CurtainEntity> checkCurtainType = curtainService.getCurtainById(id);

        if(!checkCurtainType.isPresent()){
            System.out.println("NO SE ENCONTRO LA CORTINA CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cortina con el ID especificado no se encuentra registrada.");
        }

        Optional<CurtainEntity> checkName = curtainService.validateCurtain(curtain.getCurtainType());
    
        if(checkName.isPresent()){
            System.out.println("NOMBRE EN USO \n");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre a modificar ya se encuentra registrado");
        }

        curtainService.updateCurtain(id,curtain);
        System.out.println("ACTUALIZADO CON EXITO \n");
        return ResponseEntity.ok().build();
    }

    // Permite eliminar todas las cortinas del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deleteCurtains(){
        curtainService.deleteCurtains();
        return ResponseEntity.ok("SE ELIMINARON LAS CORTINAS CORRECTAMENTE");
    }

    // Permite eliminar una cortina en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCurtainById(@PathVariable Long id){
        if(!curtainService.existCurtainById(id)){
            System.out.println("NO SE ENCONTRO LA CORTINA CON EL ID: " + id + " \n");
            return ResponseEntity.notFound().build();
        }
        curtainService.deleteCurtainById(id);
        return ResponseEntity.ok("CORTINA CON ID " + id + " ELIMINADA CORRECTAMENTE \n");
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------//

 
}
