package com.tokioschool.filmotokio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.filmotokio.dominio.dto.Score;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Long> {

}

