package com.tokioschool.filmotokio.service.filmsearch.strategy;

import com.tokioschool.filmotokio.domain.Film;

import java.util.Set;

public interface FilmSearchStrategy {

    Set<Film> searchFilm(String searchParam);

}
