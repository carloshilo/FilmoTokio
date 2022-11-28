package com.tokioschool.filmotokio.dominio;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * Una película
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(nullable = false)
    private String title;
    @NotNull
    @Column(nullable = false)
    private int year;
    @NotNull
    @Column(nullable = false)
    private int duration;
    @Column
    private String synopsis;
    @NotNull
    @Column(nullable = false)
    private String poster;
    @NotNull
    @Column(nullable = false)
    private boolean migrate;
    @Column
    private Date dateMigrate;

    @OneToMany(mappedBy = "film")
    private Set<Review> reviews;
    @OneToMany(mappedBy = "film")
    private Set<Score> scores;
    
    @ManyToMany()
    @JoinTable(name = "rel_person_film", joinColumns = @JoinColumn(name = "film"),
    inverseJoinColumns = @JoinColumn(name = "person"))
    private Set<Person> people;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
