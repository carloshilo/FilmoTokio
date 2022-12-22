package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    Set<Score> findByFilmTitle(String title);

    @Query("SELECT AVG(s.value) FROM scores s WHERE s.film.title = ?1")
    double findAvgScoreByFilmTitle(String title);

}

