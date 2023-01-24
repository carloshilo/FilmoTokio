package com.tokioschool.filmotokio.controller;

import static com.tokioschool.filmotokio.domain.enums.TypePerson.ACTOR;
import static com.tokioschool.filmotokio.domain.enums.TypePerson.DIRECTOR;
import static com.tokioschool.filmotokio.domain.enums.TypePerson.MUSICIAN;
import static com.tokioschool.filmotokio.domain.enums.TypePerson.PHOTOGRAPHER;
import static com.tokioschool.filmotokio.domain.enums.TypePerson.SCREENWRITER;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.Score;
import com.tokioschool.filmotokio.domain.dto.FilmDTO;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.PersonService;
import com.tokioschool.filmotokio.service.ReviewService;
import com.tokioschool.filmotokio.service.ScoreService;
import com.tokioschool.filmotokio.service.UserService;
import java.security.Principal;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

  private final @NonNull FilmService filmService;
  private final @NonNull UserService userService;
  private final @NonNull PersonService personService;
  private final @NonNull ReviewService reviewService;
  private final @NonNull ScoreService scoreService;

  @GetMapping("/search")
  public String searchFilm(@RequestParam(name = "query") String query,
      @RequestParam(name = "criteria") String criteria,
      Model model) {
    Set<Film> results = filmService.findAllBy(query, criteria);
    model.addAttribute("films", results);
    return "searched-film";
  }

  @GetMapping("/{filmUri}")
  public String filmInfo(@PathVariable("filmUri") UUID uri,
      Model model,
      Authentication authentication) {
    var film = filmService.getByUri(uri);

    model.addAttribute("film", new FilmDTO(film, reviewService.getAllByFilm(film.getId())));

    if (Objects.nonNull(authentication)) {
      var user = userService.getByUsernameOrThrow(authentication.getName());
      var userScore = film.getScores()
          .stream()
          .filter(scr -> Objects.equals(scr.getUser().getId(), user.getId()))
          .findFirst();
      if (userScore.isPresent()) {
        model.addAttribute("score", userScore.get());
      } else {
        model.addAttribute("newScore", new Score());
      }
    }
    return "film";
  }

  @PostMapping("/score/{filmUri}")
  public String filmInfo(@PathVariable("filmUri") UUID filmUri,
      @ModelAttribute("newScore") @Valid Score score,
      BindingResult result,
      Authentication auth) {
    if (result.hasErrors()) {
      return "film";
    }

    score.setUser(userService.getByUsernameOrThrow(auth.getName()));
    score.setFilm(filmService.getByUri(filmUri));
    scoreService.add(score);

    return "redirect:/films/" + filmUri;
  }

  @GetMapping("/add")
  public String createFilm(Model model) {
    model.addAttribute("film", new FilmDTO());
    model.addAttribute("directors", personService.getByType(DIRECTOR));
    model.addAttribute("actors", personService.getByType(ACTOR));
    model.addAttribute("screenwriters", personService.getByType(SCREENWRITER));
    model.addAttribute("photographers", personService.getByType(PHOTOGRAPHER));
    model.addAttribute("musicians", personService.getByType(MUSICIAN));
    return "new-film";
  }

  @PostMapping("/add")
  public String createFilm(@RequestParam("posterImage") MultipartFile posterImage,
      @ModelAttribute("film") @Valid FilmDTO film,
      BindingResult result,
      Model model,
      Principal principal) {
    if (result.hasErrors()) {
      return "new-film";
    } else {
      var createdFilm = filmService.add(film, principal.getName());
      if (!posterImage.isEmpty()) {
        createdFilm = filmService.savePoster(createdFilm, posterImage);
      }
      model.addAttribute("film", new FilmDTO(createdFilm));
      return "redirect:/films/" + createdFilm.getUri();
    }
  }
}
