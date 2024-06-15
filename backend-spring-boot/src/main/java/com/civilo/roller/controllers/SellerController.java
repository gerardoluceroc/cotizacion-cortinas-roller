package com.civilo.roller.controllers;

import com.civilo.roller.Entities.CoverageEntity;
import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.services.SellerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional; 
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired
    SellerService sellerService;

    // Permite obtener todos los vendedores del sistema.
    @GetMapping()
    public List<SellerEntity> getSellers(){
        return sellerService.getSellers();
    }

    //Permite obtener un vendedor en especifico del sistema.
    @GetMapping("/{id}")
    public ResponseEntity<SellerEntity> getSellerById(@PathVariable long id){
        Optional<SellerEntity> seller = sellerService.getSellerById(id);
        if(!seller.isPresent()){
            System.out.println("NO SE ENCONTRO EL VENDEDOR \n");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<SellerEntity>(seller.get(), HttpStatus.OK);
    }
    
    // Permite guardar entidad vendedor.
    @PostMapping()
    public SellerEntity saveSeller(@RequestBody SellerEntity seller){
        return this.sellerService.saveSeller(seller);
    }

    //**Funciones register y update son implementadas directamente en UserController.**//

    // Permite eliminar todos los vendedores del sistema
    @DeleteMapping()
    public ResponseEntity<String> deleteSellers(){
        sellerService.deleteSellers();
        return ResponseEntity.ok("SE ELIMINARON LOS VENDEDORES CORRECTAMENTE");
    }

    // Permite eliminar un vendedor en especifico del sistema.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSellerById(@PathVariable Long id){
        if(!sellerService.existsSellerById(id)){
            System.out.println("NO SE ENCONTRO UN VENDEDOR CON EL ID: "+ id + "\n");
            return ResponseEntity.notFound().build();
        }
        sellerService.deleteSellerById(id);
        return ResponseEntity.ok("VENDEDOR CON ID " + id + " ELIMINADO CORRECTAMENTE\n");
    }
    
    //------------------------------------------------------------------------------------------------------------------------------------------------//

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SellerEntity userDTO, HttpServletRequest request){
        SellerEntity seller = sellerService.validateSeller(userDTO.getEmail(), userDTO.getPassword());
        if (seller == null){
            System.out.println("CORREO O CONTRASEÑA INCORRECTA\n");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        HttpSession session = request.getSession();
        session.setAttribute("seller", seller);
        System.out.println("SESIÓN INICIADA CORRECTAMENTE");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sellerInformation")
    public ResponseEntity<?> sellerInformationUpdateCompanyName(@RequestBody SellerEntity userDTO){
        sellerService.updateCoverageIdAndCompanyNameSellerByEmail(userDTO.getEmail(), userDTO.getCompanyName(), userDTO.getCoverageID(), userDTO.getBank(), userDTO.getBankAccountType(), userDTO.getBankAccountNumber());
        return ResponseEntity.ok().build();
    }

}
