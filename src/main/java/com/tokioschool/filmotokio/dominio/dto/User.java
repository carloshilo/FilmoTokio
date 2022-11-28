package com.tokioschool.filmotokio.dominio.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

/**
 * Usuario de la aplicación
 */
@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(nullable = false)
    private String username;
    @NotNull
    @Column(nullable = false)
    private String password;
    @NotNull
    @Column(nullable = false)
    private String name;
    @Column
    private String surname;
    @NotNull
    @Column(nullable = false)
    private String email;
    @Column
    private String image;
    @NotNull
    @Column(nullable = false)
    private Date birthdate;
    @CreatedDate
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    @NotNull
    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "user")
    private Set<Film> films;
    @OneToMany(mappedBy = "user")
    private Set<Review> filmReviews;
    @OneToMany(mappedBy = "user")
    private Set<Score> filmScores;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
