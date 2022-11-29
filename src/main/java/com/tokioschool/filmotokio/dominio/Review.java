package com.tokioschool.filmotokio.dominio;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @NotNull
  @Column(nullable = false)
  private String title;
  @NotNull
  @Column(name = "text_review", nullable = false)
  private String textReview;
  @CreatedDate
  @Column(nullable = false)
  private Date date;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne
  @JoinColumn(name = "film_id")
  private Film film;

}
