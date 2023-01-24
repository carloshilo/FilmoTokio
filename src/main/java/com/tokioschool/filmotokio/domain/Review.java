package com.tokioschool.filmotokio.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.tokioschool.filmotokio.domain.dto.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reviews")
public class Review implements Serializable {

  @Serial
  private static final long serialVersionUID = -8936769226103417764L;
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
  private LocalDate date;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne
  @JoinColumn(name = "film_id")
  private Film film;

  public Review(ReviewDTO reviewDTO) {
    date = reviewDTO.getDate();
    title = reviewDTO.getTitle();
    textReview = reviewDTO.getText();
  }
}
