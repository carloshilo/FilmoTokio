package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.Score;
import com.tokioschool.filmotokio.domain.dto.FilmDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface FilmService {

    Film getFilmByUri(String filmUri);

    Set<FilmDTO> getAll();
    Set<Film> searchFilms(String searchParam, String searchCriteria);

    Film addScore(String filmUri, Score score);

    Film savePoster(Film film, MultipartFile posterImage);

    Film addFilm(FilmDTO film, String username);
}
