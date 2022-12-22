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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Set;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepo;
    private final FilmSearch filmSearch;
    private final FileService fileService;

    public FilmServiceImpl(FilmRepository filmRepo,
                           FilmSearch filmSearch, @Qualifier("film-poster-file-service")
                           FileService fileService) {
        this.filmRepo = filmRepo;
        this.filmSearch = filmSearch;
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public Film addFilm(Film film) {
        film.setUri(StringUtil.getFilmUri(film.getTitle(), film.getYear()));
        log.info("Saving Film {}", film);
        return filmRepo.save(film);
    }

    @Override
    @Transactional
    public Film savePoster(Film film, MultipartFile posterImage) {
        Film toUpdate = filmRepo.findByTitleIgnoreCase(film.getTitle())
                .orElseThrow(() -> new FilmNotFoundException());

        String fileName = StringUtil.getFilmPosterFilename(film.getTitle(), film.getYear(), posterImage.getContentType());
        fileService.saveFile(posterImage, fileName);

        toUpdate.setPoster(fileName);
        return filmRepo.save(toUpdate);
    }

    @Override
    @Transactional()
    public Film getFilmByUri(String filmUri) {
        String filmTitle = StringUtil.getFilmTitleFromUri(filmUri);
        return filmRepo.findByTitleIgnoreCase(filmTitle)
                .orElseThrow(() -> new FilmNotFoundException());
    }

    @Override
    @Transactional
    public Film addScore(String filmUri, Score score) {
        Film toUpdate = getFilmByUri(filmUri);
        toUpdate.addScore(score);
        return filmRepo.save(toUpdate);
    }

    @Override
    public Set<Film> searchByTitle(String title) {
        log.info("Searching for Film with title like {}", title);
        return filmRepo.findByTitleContainsIgnoreCase(title);
    }

    @Override
    public Set<Film> getAll() {
        return filmRepo.findAll();
    }

    @Override
    public Set<Film> searchFilms(String searchParam, String searchCriteria) {
        return filmSearch.searchFilm(searchParam, FilmSearchCriteria.fromString(searchCriteria));
    }

    @Override
    public Film findByTitleExact(String title) {
        return filmRepo.findByTitleIgnoreCase(title).orElseThrow(FilmNotFoundException::new);
    }
}
