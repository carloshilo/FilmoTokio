package com.tokioschool.filmotokio.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum FilmSearchCriteria {
  TITLE("Título"),
  YEAR("Año"),
  MAX_DURATION("Duración máxima"),
  AVG_SCORE("Puntación media"),
  ACTOR("Actor"),
  DIRECTOR("Director"),
  SCREENWRITER("Guionista"),
  PHOTOGRAPHER("Fotografía"),
  MUSICIAN("Música");

  private final String displayValue;

}
