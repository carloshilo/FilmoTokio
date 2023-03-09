package com.tokioschool.filmotokio.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

  @Serial
  private static final long serialVersionUID = 1382241345373591627L;

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
  @Default
  @Column(name = "active", nullable = false)
  private boolean active = true;

  @OneToMany(mappedBy = "user")
  private Set<Film> films;
  @OneToMany(mappedBy = "user")
  private Set<Review> filmReviews;
  @OneToMany(mappedBy = "user")
  private Set<Score> filmScores;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;

  public void update(User user) {
    if (Objects.nonNull(user.getUsername())) {
      this.username = user.getUsername();
    }
    if (Objects.nonNull(user.getEmail())) {
      this.email = user.getEmail();
    }
    if (Objects.nonNull(user.getName())) {
      this.name = user.getName();
    }
    if (Objects.nonNull(user.getSurname())) {
      this.surname = user.getSurname();
    }
  }
}
