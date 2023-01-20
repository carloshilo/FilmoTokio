package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.Score;
import com.tokioschool.filmotokio.domain.dto.FilmDTO;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface FilmService {

    Film getByUri(UUID uri);

    Set<FilmDTO> getAll();
    Set<Film> findAllBy(String searchParam, String searchCriteria);

    Film addScore(UUID filmUri, Score score);

    Film savePoster(Film film, MultipartFile posterImage);

    Film add(FilmDTO film, String username);
}
