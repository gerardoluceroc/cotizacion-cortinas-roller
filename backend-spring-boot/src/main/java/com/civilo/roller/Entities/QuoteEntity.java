package com.civilo.roller.Entities;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "quotes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuoteEntity {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long quoteID;
    private int amount;

    private float valueSquareMeters;
    private float width;
    private float height;
    private float totalSquareMeters;
    private float totalFabrics;

    private float bracketValue;
    private float capValue;
    private float pipeValue;
    private float counterweightValue;
    private float bandValue;
    private float chainValue;
    private float totalMaterials;

    private float assemblyValue;
    private float installationValue;
    private float totalLabor;

    private float percentageDiscount;
    private float productionCost;
    private float saleValue;

    private Date date;  

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "SELLER_sellerid")
    private SellerEntity seller;

    @ManyToOne
    @JoinColumn(name = "CURTAINS")
    CurtainEntity curtain;

    @ManyToOne
    @JoinColumn(name = "PIPES")
    PipeEntity pipe;

    @ManyToOne
    @JoinColumn(name = "QUOTE_SUMMARY")
    QuoteSummaryEntity quoteSummary;

    @ManyToOne
    @JoinColumn(name = "PROFIT_MARGIN")
    ProfitMarginEntity profitMarginEntity;

    @ManyToOne
    @JoinColumn(name = "REQUESTS")
    RequestEntity RequestEntity;
}