package com.civilo.roller.Entities;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long userID;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String rut;
    private String phoneNumber;
    private String commune;
    private LocalDate birthDate;
    private int age;
    private LocalTime startTime;
    private LocalTime endTime;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "ROLE")
    RoleEntity role;
    
    //@JsonIgnore
    //RoleEntity role;

}
