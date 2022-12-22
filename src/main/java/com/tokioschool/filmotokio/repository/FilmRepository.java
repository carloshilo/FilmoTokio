package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {


}
