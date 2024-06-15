package com.civilo.roller.controllers;

import com.civilo.roller.Entities.IVAEntity;
import com.civilo.roller.services.IVAService;
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
    @RequestMapping("/iva")
public class IVAController {
    @Autowired
    IVAService ivaService;

    // Permite obtener el IVA
    @GetMapping()
    public float getIVAPercentage(){
        return ivaService.getIVAPercentage();
    }

    
    // Permite obtener todos los IVA del sistema.
    @GetMapping("/all")                             // Javi: En otros crud este get es sin especificacion en la ruta, pero como está el de arriba lo dejé como "all" para que no fuera a afectar en otra parte
    public List<IVAEntity> getIVAs(){
        return ivaService.getIVAs();
    }

    // Permite obtener un IVA en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<IVAEntity> getIVAById(@PathVariable long id){
        Optional<IVAEntity> iva = ivaService.getIVAById(id);
        if(!iva.isPresent()){
            System.out.println("NO SE ENCONTRO EL IVA \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<IVAEntity>(iva.get(), HttpStatus.OK);
    }

    // Permite guardar un nuevo iva en el sistema.
    @PostMapping()
    public ResponseEntity<?> createIVA(@RequestBody IVAEntity iva){
        ivaService.createIVA(iva);
        System.out.println("IVA GUARDADO CON EXITO\n");
        return ResponseEntity.ok().build();
    }

    // Permite actualizar información de un iva.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateIVA(@PathVariable long id, @RequestBody IVAEntity iva) {
        
        Optional<IVAEntity> checkIVA = ivaService.getIVAById(id);
        
        if(!checkIVA.isPresent()){
            System.out.println("NO SE ENCONTRO EL IVA CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El iva con el ID especificado no existe."); 
        }

        ivaService.updateIVA(id,iva);
        System.out.println("IVA ACTUALIZADO CON EXITO\n");
        return ResponseEntity.ok().build(); 
    }

    // Permite eliminar todos los ivas del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deleteIVAs(){
        ivaService.deleteIVAs();
        return ResponseEntity.ok("SE ELIMINARON LOS IVAS CORRECTAMENTE");
    }

    // Permite eliminar un iva en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIVAById(@PathVariable Long id){
        if(!ivaService.existsIVAById(id)){
            System.out.println("NO SE ENCONTRO UN IVA CON EL ID: "+ id + "\n");
            return ResponseEntity.notFound().build();
        }
        ivaService.deleteIVAById(id);
        return ResponseEntity.ok("IVA CON ID " + id + " ELIMINADO CORRECTAMENTE\n");

    }
}
