package com.tokioschool.filmotokio.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TypePerson {
  SCREENWRITER("Guionista"),
  MUSICIAN("Música"),
  PHOTOGRAPHER("Fotografía"),
  ACTOR("Actor"),
  DIRECTOR("Director");

  @Getter
  private final String displayValue;

}