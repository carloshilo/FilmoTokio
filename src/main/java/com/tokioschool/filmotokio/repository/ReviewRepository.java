package com.tokioschool.filmotokio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.filmotokio.dominio.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}

