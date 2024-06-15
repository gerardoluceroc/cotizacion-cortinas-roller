package com.civilo.roller.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "currentIVA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IVAEntity {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long ivaID;
    private float ivaPercentage;

}
