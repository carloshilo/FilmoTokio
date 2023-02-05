package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Review;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query("SELECT r FROM reviews r JOIN FETCH r.user u WHERE u.username = ?1")
  List<Review> findByUserUsername(String username);

  List<Review> findByFilmId(long id);


}

