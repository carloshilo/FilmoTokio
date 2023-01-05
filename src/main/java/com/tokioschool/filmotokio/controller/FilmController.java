package com.tokioschool.filmotokio.controller;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.Score;
import com.tokioschool.filmotokio.domain.User;
import com.tokioschool.filmotokio.domain.dto.FilmDTO;
import com.tokioschool.filmotokio.domain.enums.FilmSearchCriteria;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.PersonService;
import com.tokioschool.filmotokio.service.UserService;
import com.tokioschool.filmotokio.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.tokioschool.filmotokio.domain.enums.TypePerson.*;

@Controller
@Slf4j
public class FilmController {

    private final FilmService filmService;
    private final UserService userService;
    private final PersonService personService;

    public FilmController(FilmService filmService, UserService userService, PersonService personService) {
        this.filmService = filmService;
        this.userService = userService;
        this.personService = personService;
    }

    @GetMapping("films/search")
    public String searchFilm(@RequestParam(name = "query") String query,
                             @RequestParam(name = "criteria") String criteria,
                             Model model) {
        Set<Film> results = filmService.searchFilms(query, criteria);
        model.addAttribute("films", results);
        return "searched-film";
    }

    @GetMapping("search")
    public String search(Model model) {
        model.addAttribute("criteria", FilmSearchCriteria.values());
        return "search-film";
    }

    @GetMapping("films/{filmUri}")
    public String filmInfo(@PathVariable("filmUri") String filmUri,
                           Model model,
                           Authentication authentication) {
        Film film = filmService.getFilmByUri(filmUri);
        model.addAttribute("film", film);

        if (authentication != null) {
            User authenticatedUser = (User) authentication.getPrincipal();
            Optional<Score> userScore = film.getScores()
                    .stream()
                    .filter(scr -> Objects.equals(scr.getUser().getId(), authenticatedUser.getId()))
                    .findFirst();
            if (userScore.isPresent()) {
                model.addAttribute("score", userScore.get());
            } else {
                model.addAttribute("newScore", new Score());
            }
        }
        return "film";
    }

    @PostMapping("films/{filmUri}/score")
    public String filmInfo(@PathVariable("filmUri") String filmUri,
                           @ModelAttribute("newScore") @Valid Score score,
                           BindingResult result,
                           Authentication auth) {
        if (result.hasErrors()) {
            return "film";
        }
        score.setUser((User) auth.getPrincipal());
        filmService.addScore(filmUri, score);

        return "redirect:/films/" + filmUri;
    }

    @GetMapping("films/add")
    public String createFilm(Model model) {
        model.addAttribute("film", new FilmDTO());
        model.addAttribute("directors", personService.getPeopleByType(DIRECTOR));
        model.addAttribute("actors", personService.getPeopleByType(ACTOR));
        model.addAttribute("screenwriters", personService.getPeopleByType(SCREENWRITER));
        model.addAttribute("photographers", personService.getPeopleByType(PHOTOGRAPHER));
        model.addAttribute("musicians", personService.getPeopleByType(MUSICIAN));
        return "new-film";
    }

    @PostMapping("films/add")
    public String createFilm(@RequestParam("posterImage") MultipartFile posterImage,
                             @ModelAttribute("film") @Valid FilmDTO film,
                             BindingResult result,
                             Model model,
                             Principal principal)
    {
        if (result.hasErrors()) {
            return "new-film";
        }
        else {
            Film createdFilm = filmService.addFilm(film, principal.getName());
            if (!posterImage.isEmpty()) {
                createdFilm = filmService.savePoster(createdFilm, posterImage);
            }
            model.addAttribute("film", createdFilm);
            return "redirect:/films/" + StringUtil.getFilmUri(createdFilm.getTitle(), createdFilm.getYear());
        }
    }
}
