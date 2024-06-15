package com.civilo.roller.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "sellers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SellerEntity extends UserEntity {
    //Atributos
    private String companyName;
    private boolean disponibility;
    private String bank;
    private String bankAccountType;
    private Integer bankAccountNumber; 
    

    @ElementCollection
    private List<Integer> coverageID;

    //Relaciones


    @OneToMany(mappedBy = "quoteID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuoteEntity> quoteEntities;



    //Constructor
    public SellerEntity(Long userID, String name, String surname, String email,
                        String password, String rut, String phoneNumber, String commune,
                        LocalDate birthDate, int age, LocalTime startTime, LocalTime endTime, RoleEntity role, String companyName, boolean disponibility,
                        String bank, String bankAccountType, int bankAccountNumber) {
        super(userID, name, surname, email, password, rut, phoneNumber, commune, birthDate, age, startTime, endTime, role);
        this.companyName = companyName;
        this.disponibility = disponibility;
        this.bank = bank;
        this.bankAccountType = bankAccountType;
        this.bankAccountNumber = bankAccountNumber;
    }

}
