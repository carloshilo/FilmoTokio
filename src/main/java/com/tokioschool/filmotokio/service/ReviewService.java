package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Review;
import com.tokioschool.filmotokio.domain.dto.ReviewDTO;

import java.util.List;
import java.util.Set;

public interface ReviewService {

    List<Review> getAllByFilm(long id);

    Review add(ReviewDTO reviewDTO);

    Set<Review> findByUsername(String username);
}
