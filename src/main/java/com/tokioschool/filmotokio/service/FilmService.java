package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.Score;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface FilmService {

    Film getFilmByUri(String filmUri);

    Set<Film> getAll();
}
