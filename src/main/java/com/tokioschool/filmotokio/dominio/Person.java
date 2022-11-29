package com.tokioschool.filmotokio.dominio;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
