package com.tokioschool.filmotokio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.filmotokio.dominio.dto.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

}

