package com.civilo.roller.Entities;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.*;


@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PermissionEntity {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long permissionID;
    private String permission;

    //Relaciones

    @ManyToOne
    @JoinColumn(name = "roles")
    RoleEntity role;


}