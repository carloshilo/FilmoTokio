package com.tokioschool.filmotokio.batch;

import com.tokioschool.filmotokio.domain.Film;
import org.springframework.batch.item.ItemProcessor;

public class FilmItemProcessor implements ItemProcessor<Film, Film> {

  @Override
  public Film process(Film film) {
    if (!film.isMigrate()) {
      return null;
    }
    return film;
  }
}
