package com.tokioschool.filmotokio.repository;

import com.tokioschool.filmotokio.domain.Film;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

  Optional<Film> findByTitleIgnoreCase(String title);

  Optional<Film> findByUri(UUID uri);
}
