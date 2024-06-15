package com.civilo.roller.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "quotes_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuoteSummaryEntity {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long quoteSummaryID;
    private String description;
    private float totalCostOfProduction;
    private float totalSaleValue;
    private float valueAfterDiscount;
    private float netTotal;
    private float percentageDiscount;
    private float total;
    private Date date;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "SELLER_sellerid")
    private SellerEntity seller;

    @ManyToOne
    @JoinColumn(name = "currentIVA")
    IVAEntity currentIVA;
}
