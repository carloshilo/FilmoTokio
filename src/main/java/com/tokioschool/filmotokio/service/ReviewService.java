package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Review;
import java.util.List;

public interface ReviewService {

  List<Review> getAllByFilm(long id);

}
