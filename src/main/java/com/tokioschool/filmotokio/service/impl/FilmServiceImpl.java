package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Film;
import com.tokioschool.filmotokio.domain.dto.FilmDTO;
import com.tokioschool.filmotokio.exception.FilmNotFoundException;
import com.tokioschool.filmotokio.repository.FilmRepository;
import com.tokioschool.filmotokio.service.FilmService;
import com.tokioschool.filmotokio.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<FilmDTO> getAll() {
        return filmRepository.findAll().stream()
                .map(film -> FilmDTO.builder()
                        .id(film.getId())
                        .year(film.getYear())
                        .title(film.getTitle())
                        .poster(film.getPoster())
                        .avgScore(film.getAvgScore())
                        .build()).collect(Collectors.toSet());
    }
}
