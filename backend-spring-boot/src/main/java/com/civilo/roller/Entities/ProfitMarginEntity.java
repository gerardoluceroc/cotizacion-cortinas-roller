package com.civilo.roller.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profitMargin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfitMarginEntity {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long profitMarginID;
    private float profitMarginPercentaje;
    private float decimalProfitMargin;
}
