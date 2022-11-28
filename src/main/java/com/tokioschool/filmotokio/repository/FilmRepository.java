package com.tokioschool.filmotokio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.filmotokio.dominio.dto.Film;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {

}
