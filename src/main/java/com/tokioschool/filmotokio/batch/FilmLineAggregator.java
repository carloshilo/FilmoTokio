package com.tokioschool.filmotokio.batch;

import com.tokioschool.filmotokio.domain.Film;
import org.springframework.batch.item.file.transform.LineAggregator;

public class FilmLineAggregator implements LineAggregator<Film> {

  private static final String CSV_DELIMITER = ",";

  @Override
  public String aggregate(Film film) {
    return film.getId()
        + CSV_DELIMITER
        + film.getTitle()
        + CSV_DELIMITER
        + film.getYear()
        + CSV_DELIMITER
        + film.getDuration()
        + CSV_DELIMITER
        + film.getSynopsis()
        + CSV_DELIMITER
        + film.getPoster()
        + CSV_DELIMITER
        + film.getAvgScore()
        + CSV_DELIMITER
        + film.getUser().getId();
  }
}
