package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.dto.FilmDTO;
import com.tokioschool.filmotokio.exception.FilmNotFoundException;
import com.tokioschool.filmotokio.properties.FileDirectoryProperties;
import com.tokioschool.filmotokio.repository.FilmRepository;
import com.tokioschool.filmotokio.service.FileService;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.service.UserService;
import com.tokioschool.filmotokio.utils.StringUtil;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
  private final @NonNull FileService fileService;
  private final @NonNull UserService userService;

  private final @NonNull FileDirectoryProperties directoryProperties;

  @Override
  public Film getByUri(UUID uri) {
    return filmRepository.findByUri(uri)
        .orElseThrow(FilmNotFoundException::new);
  }

  @Override
  public Set<FilmDTO> getAll() {
    return filmRepository.findAll().stream()
        .map(FilmDTO::new)
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Film> findAllBy(String title) {
    return filmRepository.findByTitleContainsIgnoreCase(title);
  }

  @Override
  public Film savePoster(Film film, MultipartFile posterImage) {

    var fileName = StringUtil.getFilmPosterFilename(film.getUri(),
        posterImage.getContentType());
    fileService.save(posterImage, directoryProperties.films(), fileName);

    var toUpdate = filmRepository.findByUri(film.getUri())
        .orElseThrow(FilmNotFoundException::new);
    toUpdate.setPoster(fileName);
    return filmRepository.save(toUpdate);
  }

  @Override
  public Film add(FilmDTO dto, String username) {
    var film = Film.builder()
        .people(Stream.of(dto.getActors(), dto.getDirectors(), dto.getMusicians(),
                dto.getPhotographers(), dto.getScreenwriters())
            .flatMap(Collection::stream)
            .collect(Collectors.toSet()))
        .duration(dto.getDuration())
        .year(dto.getYear())
        .title(dto.getTitle())
        .poster(dto.getPoster())
        .synopsis(dto.getSynopsis())
        .uri(UUID.randomUUID())
        .user(userService.getByUsernameOrThrow(username))
        .build();
    log.info("Saving Film {}", film);
    return filmRepository.save(film);
  }

  @Override
  public void updateAvgScore(UUID uri, int avgScore) {
    var film = filmRepository.findByUri(uri).orElse(new Film());
    film.setAvgScore(avgScore);
    filmRepository.save(film);
  }
}
