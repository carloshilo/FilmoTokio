package com.tokioschool.filmotokio.dominio;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "people")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false)
    private String surname;
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypePersonEnum type;
    
    @ManyToMany(mappedBy = "people")
    private Set<Film> films;
    
}
