package com.civilo.roller.Entities;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.*;

@Entity
@Table(name = "coverages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoverageEntity {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long coverageID;
    private String commune;
}
