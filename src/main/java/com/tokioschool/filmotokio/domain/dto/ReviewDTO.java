package com.tokioschool.filmotokio.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.Review;
import com.tokioschool.filmotokio.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDTO {
    private String title;
    private String text;
    private LocalDate date;
    private User user;
    private Film film;

    public ReviewDTO(Review review) {
    }
}
