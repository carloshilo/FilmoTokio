package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.exception.FilmNotFoundException;
import com.tokioschool.filmotokio.repository.FilmRepository;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.utils.StringUtil;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {

  private final @NonNull FilmRepository filmRepository;

  @Override
  @Transactional()
  public Film getFilmByUri(String filmUri) {
    String filmTitle = StringUtil.getFilmTitleFromUri(filmUri);
    return filmRepository.findByTitleIgnoreCase(filmTitle)
        .orElseThrow(FilmNotFoundException::new);
  }

  @Override
  public Set<Film> getAll() {
    return new HashSet<>(filmRepository.findAll());
  }
}
