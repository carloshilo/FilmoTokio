package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.dominio.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

}
