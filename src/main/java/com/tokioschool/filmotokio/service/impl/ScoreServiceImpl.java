package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Score;
import com.tokioschool.filmotokio.repository.ScoreRepository;
import com.tokioschool.filmotokio.service.ScoreService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScoreServiceImpl implements ScoreService {

  private final @NonNull ScoreRepository scoreRepository;

  @Override
  public void add(Score score) {
    scoreRepository.save(score);
  }
}
