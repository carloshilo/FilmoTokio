package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Review;
import java.util.List;
import java.util.Set;

public interface ReviewService {

  List<Review> getAllByFilm(long id);

  Review addReview(Review review);

  Set<Review> findByUsername(String username);
}
