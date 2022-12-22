package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.Score;
import com.tokioschool.filmotokio.domain.enums.FilmSearchCriteria;
import com.tokioschool.filmotokio.exception.FilmNotFoundException;
import com.tokioschool.filmotokio.repository.FilmRepository;
import com.tokioschool.filmotokio.service.FileService;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.filmsearch.FilmSearch;
import com.tokioschool.filmotokio.utils.StringUtil;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {

  private final @NonNull FilmRepository filmRepository;
  private final @NonNull FilmSearch filmSearch;
  private final @NonNull FileService fileService;

  @Override
  @Transactional
  public Film addFilm(Film film) {
    log.info("Saving Film {}", film);
    return filmRepository.save(film);
  }

  @Override
  @Transactional
  public Film savePoster(Film film, MultipartFile posterImage) {
    Film toUpdate = filmRepository.findByTitleIgnoreCase(film.getTitle())
        .orElseThrow(() -> new FilmNotFoundException());

    String fileName = StringUtil.getFilmPosterFilename(film.getTitle(), film.getYear(),
        posterImage.getContentType());
    fileService.saveFile(posterImage, fileName);

    toUpdate.setPoster(fileName);
    return filmRepository.save(toUpdate);
  }

  @Override
  @Transactional()
  public Film getFilmByUri(String filmUri) {
    String filmTitle = StringUtil.getFilmTitleFromUri(filmUri);
    return filmRepository.findByTitleIgnoreCase(filmTitle)
        .orElseThrow(() -> new FilmNotFoundException());
  }

  @Override
  @Transactional
  public Film addScore(String filmUri, Score score) {
    Film toUpdate = getFilmByUri(filmUri);
    toUpdate.getScores().add(score);
    return filmRepository.save(toUpdate);
  }

  @Override
  public Set<Film> searchByTitle(String title) {
    log.info("Searching for Film with title like {}", title);
    return filmRepository.findByTitleContainsIgnoreCase(title);
  }

  @Override
  public Set<Film> getAll() {
    return new HashSet<>(filmRepository.findAll());
  }

  @Override
  public Set<Film> searchFilms(String searchParam, String searchCriteria) {
    return filmSearch.searchFilm(searchParam, FilmSearchCriteria.fromString(searchCriteria));
  }

  @Override
  public Film findByTitleExact(String title) {
    return filmRepository.findByTitleIgnoreCase(title).orElseThrow(FilmNotFoundException::new);
  }
}
