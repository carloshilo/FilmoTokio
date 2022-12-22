package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Set<Review> findByFilmTitle(String title);

    @Query("SELECT r FROM reviews r JOIN FETCH r.user u WHERE u.username = ?1")
    Set<Review> findByUserUsername(String username);

}

