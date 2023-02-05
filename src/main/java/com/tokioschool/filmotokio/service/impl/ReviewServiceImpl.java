package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Review;
import com.tokioschool.filmotokio.domain.dto.ReviewDTO;
import com.tokioschool.filmotokio.exception.ReviewAlreadyExistsException;
import com.tokioschool.filmotokio.repository.ReviewRepository;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.ReviewService;
import com.tokioschool.filmotokio.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final @NonNull ReviewRepository reviewRepository;
    private final @NonNull UserService userService;
    private final @NonNull FilmService filmService;

    @Override
    public List<Review> getAllByFilm(long id) {
        return reviewRepository.findByFilmId(id);
    }

    @Override
    public Review add(ReviewDTO reviewDTO) {
        var username = reviewDTO.getUser();
        var filmUUID = reviewDTO.getFilm();

        var user = userService.getByUsernameOrThrow(username);
        var film = filmService.getByUri(filmUUID);

        if (user.getFilmReviews().stream().anyMatch(r -> r.getFilm().equals(film))) {
            throw new ReviewAlreadyExistsException(username, film.getTitle());
        } else {
            var review = Review.builder()
                    .title(reviewDTO.getTitle())
                    .textReview(reviewDTO.getText())
                    .date(LocalDate.now())
                    .film(film)
                    .user(user)
                    .build();
            return reviewRepository.save(review);
        }
    }

    @Override
    public Set<Review> findByUsername(String username) {
        return reviewRepository.findByUserUsername(username);
    }
}
