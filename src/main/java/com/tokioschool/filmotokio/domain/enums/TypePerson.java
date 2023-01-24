package com.tokioschool.filmotokio.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypePerson {
  SCREENWRITER("Guionista"),
  MUSICIAN("Música"),
  PHOTOGRAPHER("Fotografía"),
  ACTOR("Actor"),
  DIRECTOR("Director");

  private final String displayValue;

}