package com.tokioschool.filmotokio.controller.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.tokioschool.filmotokio.domain.Review;
import com.tokioschool.filmotokio.domain.dto.ReviewDTO;
import com.tokioschool.filmotokio.service.ReviewService;
import com.tokioschool.filmotokio.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api", produces = APPLICATION_JSON_VALUE)
@Slf4j
public class ApiController {

  private final @NonNull ReviewService reviewService;
  private final @NonNull UserService userService;

  @Operation(summary = "Creates a new review for a film", responses = {
      @ApiResponse(responseCode = "200", description = "Created review")
  })
  @PostMapping(path = "/new-review", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewDTO> addReview(@RequestBody @Valid ReviewDTO reviewDTO) {
    Review added = reviewService.add(reviewDTO);
    ReviewDTO addedDTO = convertToDto(added);
    return ResponseEntity.status(HttpStatus.CREATED).body(addedDTO);
  }

  @Operation(summary = "Get a list of user's review", responses = {
      @ApiResponse(responseCode = "200", description = "List of reviews")
  })
  @GetMapping("/user/{id}/reviews")
  public ResponseEntity<List<ReviewDTO>> getUserReviews(
      @Parameter(name = "id", required = true, description = "User's identifier") @PathVariable Long id,
      Authentication auth) {
    var user = userService.getById(id).orElseThrow(
        () -> {
          log.error(String.format("User with id %d not found", id));
          throw new UsernameNotFoundException(String.format("User with id %d not found", id));
        });
    var username = user.getUsername();
    List<Review> userReviews = reviewService.findByUsername(username);
    return ResponseEntity.ok(convertToDtos(userReviews));
  }

  private ReviewDTO convertToDto(Review review) {
    return new ReviewDTO(review);
  }

  private List<ReviewDTO> convertToDtos(List<Review> reviews) {
    return reviews.stream().map(this::convertToDto).toList();
  }

}
