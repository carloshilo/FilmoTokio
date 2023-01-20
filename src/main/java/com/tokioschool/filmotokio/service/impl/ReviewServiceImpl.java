package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Review;
import com.tokioschool.filmotokio.repository.ReviewRepository;
import com.tokioschool.filmotokio.service.ReviewService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final @NonNull ReviewRepository reviewRepository;

  @Override
  public List<Review> getAllByFilm(long id) {
    return reviewRepository.findByFilmId(id);
  }
}
