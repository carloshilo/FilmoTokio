package com.tokioschool.filmotokio.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tokioschool.filmotokio.domain.Review;
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
    private static final long serialVersionUID = -865713560112571094L;
    
    private String title;
    private String text;
    private LocalDate date;
    private String user;
    private UUID film;

    public ReviewDTO(Review review) {
        this.title = review.getTitle();
        this.text = review.getTextReview();
        this.date = review.getDate();
        this.user = review.getUser().getUsername();
        this.film = review.getFilm().getUri();
    }
}
