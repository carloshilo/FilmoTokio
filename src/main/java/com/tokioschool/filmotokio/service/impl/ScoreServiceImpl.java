package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Score;
import com.tokioschool.filmotokio.repository.ScoreRepository;
import com.tokioschool.filmotokio.service.ScoreService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScoreServiceImpl implements ScoreService {

  private final @NonNull ScoreRepository scoreRepository;

  @Override
  public void add(Score score) {
    scoreRepository.save(score);
  }

  @Override
  public int getAvgScore(UUID film) {
    var avg = scoreRepository.getAvgByFilm(film);
    return Math.round(avg);
  }
}
