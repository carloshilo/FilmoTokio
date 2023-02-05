package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.domain.Score;
import java.util.UUID;

public interface ScoreService {

  void add(Score score);

  int getAvgScore(UUID film);
}
