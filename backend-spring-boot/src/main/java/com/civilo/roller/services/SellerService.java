package com.civilo.roller.services;

import com.civilo.roller.Entities.SellerEntity;
import com.civilo.roller.Entities.UserEntity;
import com.civilo.roller.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class SellerService {
    @Autowired
    SellerRepository sellerRepository;

    // Get all
    // Permite obtener un listado con toda la informacion asociada a los vendedores.
    public List<SellerEntity> getSellers(){
        return (List<SellerEntity>) sellerRepository.findAll();
    }

    // Get by id
    // Permite obtener la informacion de un vendedor en especifico.
    public Optional<SellerEntity> getSellerById(Long id){
        return sellerRepository.findById(id);
    } 

    // Create
    // Permite guardar un objeto del tipo "SellerEntity" en la base de datos.
    public SellerEntity saveSeller(SellerEntity seller){
        return sellerRepository.save(seller);
    }

    // Delete all
    // Permite eliminar todos los vendedores de un sistema.
    public void deleteSellers() {
        sellerRepository.deleteAll();
    }

    // Delete by id
    // Permite eliminar un vendedor en especifico del sistema.
    public void deleteSellerById(Long id){
        sellerRepository.deleteById(id);
    }
    
    // Permite verificar si existe un vendedor en el sistema, segun el id ingresado.
    public boolean existsSellerById(Long id){
        return sellerRepository.findById(id).isPresent();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------//

    // Login
    // Permite verificar si el email y password de un usuario son correctos.
    public SellerEntity validateSeller(String email, String password){
        SellerEntity seller = sellerRepository.findByEmail(email);
        if (seller != null && seller.getPassword().equals(password)){
            return seller;
        }
        return null;
    }

    // Permite actualizar el id de cobertura y el nombre de compañia
    public SellerEntity updateCoverageIdAndCompanyNameSellerByEmail(String email, String companyName, List<Integer> coverageID, String bank, String bankAccountType, Integer bankAccountNumber){
        email = email.replaceAll("\"", "");
        SellerEntity seller = sellerRepository.findByEmail(email);
        seller.setCompanyName(companyName);
        seller.setCoverageID(coverageID);
        seller.setBank(bank);
        seller.setBankAccountType(bankAccountType);
        seller.setBankAccountNumber(bankAccountNumber);
        return sellerRepository.save(seller);
    }

}
