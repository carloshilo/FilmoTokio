package com.tokioschool.filmotokio.service.filmsearch;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.enums.FilmSearchCriteria;

import java.util.Set;

public interface FilmSearch {

    Set<Film> searchFilm(String searchParam, FilmSearchCriteria searchCriteria);

}
