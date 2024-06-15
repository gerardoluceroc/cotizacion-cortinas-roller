package com.civilo.roller.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "curtains")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurtainEntity {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long curtainID;
    private String curtainType;
}
