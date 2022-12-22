package com.tokioschool.filmotokio.repository;


import com.tokioschool.filmotokio.domain.User;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Set<User> findByActive(boolean active);

  void deleteByUsername(String username);
}
