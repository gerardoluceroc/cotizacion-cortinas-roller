package com.civilo.roller.Entities;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestEntity {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long requestID;
    private String description;
    private LocalDate deadline;
    private LocalDate admissionDate;
    private LocalDate closingDate;
    private String reason;
    private int sellerId;

    @ElementCollection
    private List<Integer> userID;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "USERS")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "COVERAGES")
    CoverageEntity coverage;

    @ManyToOne
    @JoinColumn(name = "CURTAINS")
    CurtainEntity curtain;

    @ManyToOne
    @JoinColumn(name = "STATUS")
    StatusEntity status;
}
