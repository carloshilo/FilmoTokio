package com.tokioschool.filmotokio.domain.dto;

import com.tokioschool.filmotokio.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3799241297762460904L;

    private long id;
    private String title;
    private int year;
    private int duration;
    private String poster;
    private float avgScore;
    private String synopsis;
    private Set<Person> directors = new HashSet<>();
    private Set<Person> actors = new HashSet<>();
    private Set<Person> screenwriters = new HashSet<>();
    private Set<Person> musicians = new HashSet<>();
    private Set<Person> photographers = new HashSet<>();
}
