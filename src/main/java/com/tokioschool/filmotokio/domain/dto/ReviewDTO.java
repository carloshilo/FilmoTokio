package com.tokioschool.filmotokio.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tokioschool.filmotokio.domain.Review;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1459158248671683348L;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @NotBlank
    private String user;

    @NotNull
    private UUID film;

    public ReviewDTO(Review review) {
        this.title = review.getTitle();
        this.text = review.getTextReview();
        this.user = review.getUser().getUsername();
        this.film = review.getFilm().getUri();
    }
}
