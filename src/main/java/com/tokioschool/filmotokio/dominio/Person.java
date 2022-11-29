package com.tokioschool.filmotokio.dominio;

import com.tokioschool.filmotokio.dominio.enums.TypePerson;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
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
public class Person implements Serializable {

  @Serial
  private static final long serialVersionUID = 1096082925448104965L;

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
  private TypePerson type;

  @ManyToMany(mappedBy = "people")
  private Set<Film> films = new HashSet<>();

}
