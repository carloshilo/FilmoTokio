package com.tokioschool.filmotokio.domain.dto;

import static com.tokioschool.filmotokio.domain.enums.TypePerson.ACTOR;
import static com.tokioschool.filmotokio.domain.enums.TypePerson.DIRECTOR;
import static com.tokioschool.filmotokio.domain.enums.TypePerson.MUSICIAN;
import static com.tokioschool.filmotokio.domain.enums.TypePerson.PHOTOGRAPHER;
import static com.tokioschool.filmotokio.domain.enums.TypePerson.SCREENWRITER;
import static java.util.stream.Collectors.toSet;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.Person;
import com.tokioschool.filmotokio.domain.Review;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private UUID uri;
  private int duration;
  private String poster;
  private float avgScore;
  private String synopsis;
  private Set<Person> directors = new HashSet<>();
  private Set<Person> actors = new HashSet<>();
  private Set<Person> screenwriters = new HashSet<>();
  private Set<Person> musicians = new HashSet<>();
  private Set<Person> photographers = new HashSet<>();

  private List<Review> reviews = new ArrayList<>();

  public FilmDTO(Film film, List<Review> reviews) {
    this(film);
    reviews = reviews;
  }

  public FilmDTO(Film film) {
    var people = film.getPeople();
    actors = people.stream().filter(person -> ACTOR.equals(person.getType())).collect(toSet());
    directors = people.stream().filter(person -> DIRECTOR.equals(person.getType()))
        .collect(toSet());
    screenwriters = people.stream().filter(person -> SCREENWRITER.equals(person.getType()))
        .collect(toSet());
    photographers = people.stream().filter(person -> PHOTOGRAPHER.equals(person.getType()))
        .collect(toSet());
    musicians =
        people.stream().filter(person -> MUSICIAN.equals(person.getType())).collect(toSet());
    avgScore = film.getAvgScore();
    id = film.getId();
    title = film.getTitle();
    uri = film.getUri();
    poster = film.getPoster();
    synopsis = film.getSynopsis();
    duration = film.getDuration();
    year = film.getYear();
  }
}
