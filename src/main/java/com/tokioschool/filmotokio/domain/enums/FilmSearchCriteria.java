package com.tokioschool.filmotokio.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum FilmSearchCriteria {
  TITLE("Title"),
  YEAR("Year"),
  MAX_DURATION("Max Length"),
  AVG_SCORE("Average Score"),
  ACTOR("Actor"),
  DIRECTOR("Director"),
  SCREENWRITER("Screenwriter"),
  CINEMATOGRAPHER("Cinematographer"),
  COMPOSER("Composer");

  private final String displayValue;

}
