package com.tokioschool.filmotokio.service.filmsearch.strategy.impl;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.repository.FilmRepository;
import com.tokioschool.filmotokio.service.filmsearch.strategy.FilmSearchStrategy;

import java.util.Set;

public class FilmPhotographerSearch implements FilmSearchStrategy {

    private final FilmRepository repository;

    public FilmPhotographerSearch(FilmRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Film> searchFilm(String searchParam) {
        return repository.findByFilmPhototographersNameContainsOrFilmCinematographersSurnameContainsAllIgnoreCase(searchParam, searchParam);
    }
}
