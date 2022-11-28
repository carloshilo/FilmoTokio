package com.tokioschool.filmotokio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.filmotokio.dominio.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

}

