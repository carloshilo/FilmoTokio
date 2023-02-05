package com.tokioschool.filmotokio.controller;

import com.tokioschool.filmotokio.domain.dto.ReviewDTO;
import com.tokioschool.filmotokio.repository.ReviewRepository;
import com.tokioschool.filmotokio.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ReviewController {
    private final @NonNull ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            reviewService.add(reviewDTO);
            return new ResponseEntity<>("La review ha sido creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Ya existe una review para esta película por este usuario", HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
