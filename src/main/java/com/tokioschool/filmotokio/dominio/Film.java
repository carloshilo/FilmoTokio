package com.tokioschool.filmotokio.dominio;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Una película
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
  @Column
  private Date dateMigrate;

  @OneToMany(mappedBy = "film")
  private Set<Review> reviews = new HashSet<>();
  @OneToMany(mappedBy = "film")
  private Set<Score> scores = new HashSet<>();

  @ManyToMany()
  @JoinTable(name = "rel_person_film", joinColumns = @JoinColumn(name = "film"),
      inverseJoinColumns = @JoinColumn(name = "person"))
  private Set<Person> people = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
