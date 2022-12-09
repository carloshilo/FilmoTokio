package com.tokioschool.filmotokio.dominio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TypePerson {
  SCREENWRITER("Screenwriter"),
  MUSICIAN("Musician"),
  PHOTOGRAPHER("Photographer"),
  ACTOR("Actor"),
  DIRECTOR("Director");

  @Getter
  private final String displayValue;

}