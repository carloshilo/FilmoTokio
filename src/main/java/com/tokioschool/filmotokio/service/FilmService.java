package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.dto.FilmDTO;
import java.util.Set;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface FilmService {

  Film getByUri(UUID uri);

  Set<FilmDTO> getAll();

  Set<Film> findAllBy(String searchParam);

  Film savePoster(Film film, MultipartFile posterImage);

  Film add(FilmDTO film, String username);

  void updateAvgScore(UUID uri, int avgScore);
}
