package com.tokioschool.filmotokio.controller.api;

import com.tokioschool.filmotokio.domain.Review;
import com.tokioschool.filmotokio.domain.dto.ReviewDTO;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.ReviewService;
import com.tokioschool.filmotokio.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/review", produces = APPLICATION_JSON_VALUE)
@Slf4j
public class ApiReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final FilmService filmService;

    @PostMapping(path = "/new", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO, Principal principal) {
        if (!reviewDTO.getUser().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Review received = convertToEntity(reviewDTO);
        Review added = reviewService.addReview(received);
        ReviewDTO addedDTO = convertToDto(added);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedDTO);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Set<ReviewDTO>> getUserReviews(@PathVariable String username, Authentication auth) {
        if (!username.equals(auth.getName())) {
            if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
        }
        Set<Review> userReviews = reviewService.findByUsername(username);
        return ResponseEntity.ok(convertToDtos(userReviews));
    }

    private Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = new Review(reviewDTO);
        review.setTextReview(reviewDTO.getText());
        review.setUser(userService.getByUsernameOrThrow(reviewDTO.getUser()));
        review.setFilm(filmService.getByUri(reviewDTO.getFilm()));
        return review;
    }

    private ReviewDTO convertToDto(Review review) {
        return new ReviewDTO(review);
    }

    private Set<ReviewDTO> convertToDtos(Set<Review> reviews) {
        return reviews.stream().map(this::convertToDto).collect(Collectors.toSet());
    }

}
