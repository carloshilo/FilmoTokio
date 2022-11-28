package com.tokioschool.filmotokio.dominio.dto;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Rol de usuario
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(nullable = false)
    private String name;
}
