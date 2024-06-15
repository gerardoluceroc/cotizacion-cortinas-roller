package com.civilo.roller.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long pipeID;
    private String pipeName;
}
