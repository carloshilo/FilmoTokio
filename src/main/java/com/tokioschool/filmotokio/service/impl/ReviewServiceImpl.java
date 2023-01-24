package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.Review;
import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.exception.ReviewAlreadyExistsException;
import com.tokioschool.filmotokio.repository.ReviewRepository;
import com.tokioschool.filmotokio.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final @NonNull ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllByFilm(long id) {
        return reviewRepository.findByFilmId(id);
    }

    @Override
    public Review addReview(Review review) {
        User reviewingUser = review.getUser();
        Film reviewedFilm = review.getFilm();

        if (reviewingUser.getFilmReviews().stream().anyMatch(r -> r.getFilm().equals(reviewedFilm))) {
            throw new ReviewAlreadyExistsException(reviewingUser.getUsername(), reviewedFilm.getTitle());
        } else {

            review.setUser(reviewingUser);
            review.setFilm(reviewedFilm);
            return reviewRepository.save(review);
        }
    }
}
