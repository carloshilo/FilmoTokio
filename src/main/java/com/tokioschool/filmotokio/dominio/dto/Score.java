package com.tokioschool.filmotokio.dominio.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

}
