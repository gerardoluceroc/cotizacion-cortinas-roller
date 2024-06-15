package com.civilo.roller.controllers;

import com.civilo.roller.Entities.QuoteSummaryEntity;
import com.civilo.roller.services.QuoteSummaryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/quoteSummarys")
public class QuoteSummaryController {
    @Autowired
    QuoteSummaryService quoteSummaryService;

    // Permite obtener todos los resumenes de cotizacion del sistema.
    @GetMapping()
    public List<QuoteSummaryEntity> getQuoteSummarys(){
        return quoteSummaryService.getQuoteSummarys();
    }

    // Permite obtener un resumen de cotizacon en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<QuoteSummaryEntity> getQuoteSummaryById(@PathVariable long id){
        Optional<QuoteSummaryEntity> quoteSummary = quoteSummaryService.getQuoteSummaryById(id);
        if(!quoteSummary.isPresent()){
            System.out.println("NO SE ENCONTRO EL RESUMEN DE COTIZACION \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<QuoteSummaryEntity>(quoteSummary.get(), HttpStatus.OK);
    }

    // Permite guardar un nuevo quoteSummary en el sistema.
    @PostMapping()
    public ResponseEntity<?> createQuoteSummary(@RequestBody QuoteSummaryEntity quoteSummary){

        quoteSummaryService.createQuoteSummary(quoteSummary);
        System.out.println("RESUMEN DE COTIZACION GUARDADO CON EXITO\n");
        return ResponseEntity.ok().build();
    }

    // Permite actualizar información de un resumen de cotizacon.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuoteSummary(@PathVariable long id, @RequestBody QuoteSummaryEntity quoteSummary) {
        
        Optional<QuoteSummaryEntity> checkQuoteSummary = quoteSummaryService.getQuoteSummaryById(id);
        
        if(!checkQuoteSummary.isPresent()){
            System.out.println("NO SE ENCONTRO EL RESUMEN DE COTIZACION CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El resumen de cotizacon con el ID especificado no existe."); 
        }

        quoteSummaryService.updateQuoteSummary(id,quoteSummary);
        System.out.println("ACTUALIZADO CON EXITO\n");
        return ResponseEntity.ok().build(); 
    }

    // Permite eliminar todos los resumenes de cotizacion del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deleteQuoteSummary(){
        quoteSummaryService.deleteQuoteSummary();
        return ResponseEntity.ok("SE ELIMINARON LOS RESUMENES DE COTIZACION CORRECTAMENTE");
    }

    // Permite eliminar un resumen de cotizacon en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuoteSummaryById(@PathVariable Long id){
        if(!quoteSummaryService.existsQuoteSummaryById(id)){
            System.out.println("NO SE ENCONTRO UN RESUMEN DE COTIZACION CON EL ID: "+ id + "\n");
            return ResponseEntity.notFound().build();
        }
        quoteSummaryService.deleteQuoteSummaryById(id);
        return ResponseEntity.ok("RESUMEN DE COTIZACION CON ID " + id + " ELIMINADO CORRECTAMENTE\n");

    }


}