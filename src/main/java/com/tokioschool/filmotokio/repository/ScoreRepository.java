package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Score;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

  @Query("select avg(s.value) from scores s where s.film.uri = :film")
  float getAvgByFilm(@Param("film") UUID film);
}

