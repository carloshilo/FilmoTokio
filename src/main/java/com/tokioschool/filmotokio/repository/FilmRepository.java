package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    Optional<Film> findByTitleIgnoreCase(String title);

    Set<Film> findByYear(int year);

    Set<Film> findByDurationLessThanEqual(int duration);

    Set<Film> findByFilmDirectorNameContainsOrFilmDirectorSurnameContainsAllIgnoreCase(String name, String surname);

    Set<Film> findByFilmActorsNameContainsOrFilmActorsSurnameContainsAllIgnoreCase(String name, String surname);

    Set<Film> findByFilmPhototographersNameContainsOrFilmCinematographersSurnameContainsAllIgnoreCase(String name, String surname);

    Set<Film> findByFilmScreenwritersNameContainsOrFilmScreenwritersSurnameContainsAllIgnoreCase(String name, String surname);

    Set<Film> findByAvgScoreGreaterThanEqual(int score);


    @Query("SELECT f FROM films f WHERE f.filmDirector.name = ?1 AND f.filmDirector.surname = ?2")
    Set<Film> findByDirectorNameAndDirectorSurname(String name, String surname);

    @Query("SELECT f FROM films f JOIN FETCH f.filmActors a WHERE a.name = ?1 AND a.surname = ?2")
    Set<Film> findByActorsNameAndActorsSurname(String name, String surname);

    Set<Film> findByTitleContainsIgnoreCase(String title);

}
