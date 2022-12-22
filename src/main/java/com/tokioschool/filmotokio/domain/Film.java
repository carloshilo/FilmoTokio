package com.tokioschool.filmotokio.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.tokioschool.filmotokio.domain.enums.TypePerson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REMOVE;

/**
 * Una película
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "films")
public class Film implements Serializable {

  @Serial
  private static final long serialVersionUID = 7292086160344139346L;

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
  @Column(name = "date_migrate", columnDefinition = "TIMESTAMP")
  private Date dateMigrate;
  @Column(name = "avg_score")
  private float avgScore;

  @ManyToOne(cascade = {MERGE})
  @JoinColumn(name = "user_id")
  private User user;
  @OneToMany(mappedBy = "film", cascade = {REMOVE, MERGE}, orphanRemoval = true)
  private List<Score> scores = new ArrayList<>();
  @OneToMany(mappedBy = "film", cascade = {REMOVE, MERGE}, orphanRemoval = true)
  private Set<Review> reviews = new HashSet<>();
  @ManyToOne
  @JoinColumn(name = "director_id")
  private Person filmDirector;
  @ManyToMany
  @JoinTable(name = "film_actors", joinColumns = @JoinColumn(name = "film_id"),
          inverseJoinColumns = @JoinColumn(name = "actor_id"))
  private Set<Person> filmActors = new HashSet<>();
  @ManyToMany
  @JoinTable(name = "film_composers", joinColumns = @JoinColumn(name = "film_id"),
          inverseJoinColumns = @JoinColumn(name = "musician_id"))
  private Set<Person> filmComposers = new HashSet<>();
  @ManyToMany
  @JoinTable(name = "film_screenwriters", joinColumns = @JoinColumn(name = "film_id"),
          inverseJoinColumns = @JoinColumn(name = "screenwriter_id"))
  private Set<Person> filmScreenwriters = new HashSet<>();
  @ManyToMany
  @JoinTable(name = "film_cinematographers", joinColumns = @JoinColumn(name = "film_id"),
          inverseJoinColumns = @JoinColumn(name = "photographer_id"))
  private Set<Person> filmPhotographers = new HashSet<>();

  public void calculateAverageScore() {
    this.avgScore = scores.stream().map(Score::getValue).reduce(0.0f, Float::sum) / scores.size();
  }

  public void addScore(Score score) {
    score.setFilm(this);
    scores.add(score);
    calculateAverageScore();
  }

  public void removeScore(Score score) {
    scores.remove(score);
    calculateAverageScore();
  }

  public void addReview(Review review) {
    reviews.add(review);
  }

  public boolean removeReview(Review review) {
    return reviews.remove(review);
  }

  public void addActor(Person actor) {
    if (actor.getType() == TypePerson.ACTOR) {
      filmActors.add(actor);
    }
  }

  public boolean removeActor(Person actor) {
    return filmActors.remove(actor);
  }

  public boolean removeMusician(Person musician) {
    return filmComposers.remove(musician);
  }

  public void addScreenwriter(Person screenwriter) {
    if (screenwriter.getType() == TypePerson.SCREENWRITER) {
      filmScreenwriters.add(screenwriter);
    }
  }

  public boolean removeScreenwriter(Person screenwriter) {
    return filmScreenwriters.remove(screenwriter);
  }

  public void addPhotographer(Person photographer) {
    if (photographer.getType() == TypePerson.PHOTOGRAPHER) {
      filmPhotographers.add(photographer);
    }
  }

  public boolean removePhotographer(Person photographer) {
    return filmPhotographers.remove(photographer);
  }

  @ManyToMany()
  @JoinTable(name = "rel_person_film", joinColumns = @JoinColumn(name = "film"),
      inverseJoinColumns = @JoinColumn(name = "person"))
  private Set<Person> people = new HashSet<>();

}
