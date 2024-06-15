package com.civilo.roller.controllers;

import com.civilo.roller.Entities.RequestEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    RequestService requestService;

    @Autowired
    CoverageService coverageService;

    @Autowired
    StatusService statusService;

    @Autowired
    CurtainService curtainService;

    @Autowired
    UserService userService;

    // Permite obtener todas las solicitudes del sistema.
    @GetMapping()
    public List<RequestEntity> getRequests(){
        return requestService.getRequests();
    }

    // Permite obtener una solicitud en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<RequestEntity> getRequestById(@PathVariable long id){
        Optional<RequestEntity> request = requestService.getRequestById(id);
        if(!request.isPresent()){
            System.out.println("NO SE ENCONTRO LA SOLICITUD \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<RequestEntity>(request.get(), HttpStatus.OK);
    }

    @GetMapping("/clientRequest/{id_cliente}")
    public ArrayList<RequestEntity> getRequestByUserId(@PathVariable long id_cliente){
        return requestService.getRequestByUserId(id_cliente);
    }
    
    // Permite guardar una nueva solicitud en el sistema.
    @PostMapping("/clientRequest")
    public ResponseEntity<?> createRequest(@RequestBody RequestEntity requestEntity) {
        if (userService.validateUser(requestEntity.getUser().getEmail(), requestEntity.getUser().getPassword()) == null) {
            System.out.println("NO EXISTE SESIÓN ACTIVA -> NO SE ENVÍA LA SOLICITUD");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Validamos que el usuario que envía la solicitud es del tipo cliente
        if (requestEntity.getUser().getRole().getAccountType().equals("Cliente")){
            requestEntity.setStatus(statusService.getStatus().get(0));
            requestService.saveRequest(requestEntity);
            System.out.println("SOLICITUD ENVIADA CORRECTAMENTE");
            return ResponseEntity.ok().build();
        }
        System.out.println("USUARIO SIN PERMISOS PARA ESTA ACCIÓN -> NO SE ENVIA LA SOLICITUD");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    // Permite actualizar información de un usuario.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable long id, @RequestBody RequestEntity request) {
        
        Optional<RequestEntity> checkRequest = requestService.getRequestById(id);
        
        if(!checkRequest.isPresent()){
            System.out.println("NO SE ENCONTRO LA SOLICITUD CON ID: " + id + " \n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La solicitud con el ID especificado no existe."); 
        }

        requestService.updateRequest(id,request);
        System.out.println("SOLICITUD ACTUALIZADA CON EXITO\n");
        return ResponseEntity.ok().build(); 
    } 

    // Permite eliminar todas las solicitudes del sistema.
    @DeleteMapping()
    public ResponseEntity<String> deleteRequest(){
        requestService.deleteRequest();
        return ResponseEntity.ok("SE ELIMINARON LAS SOLICITUDES CORRECTAMENTE");
    }

    // Permite eliminar un usuario en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequestById(@PathVariable Long id){
        if(!requestService.existsRequestById(id)){
            System.out.println("NO SE ENCONTRO UNA SOLICITUD CON EL ID: "+ id + "\n");
            return ResponseEntity.notFound().build();
        }
        requestService.deleteRequestById(id);
        return ResponseEntity.ok("SOLICITUD CON ID " + id + " ELIMINADA CORRECTAMENTE\n");

    }

    @GetMapping("/sellerRequest/{sellerId}")
    public List<RequestEntity> getRequestsBySellerId(@PathVariable("sellerId") Long sellerId){
        return requestService.getRequestBySellerId(sellerId);
    }

    @PostMapping("/updateRequest/{requestID}/{sellerID}")
    public ResponseEntity<?> manualAssignment(@PathVariable int requestID, @PathVariable int sellerID){
        Optional<RequestEntity> request = requestService.getRequestById(Long.valueOf(String.valueOf(requestID)));
        request.get().setSellerId(sellerID);
        request.get().setStatus(statusService.getStatus().get(1));
        requestService.saveRequest(request.get());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/automaticAssignment")
    public ResponseEntity<?> automaticAssignment(){
        requestService.automaticAssignment();
        return ResponseEntity.ok().build();
    }



}