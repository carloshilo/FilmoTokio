package com.tokioschool.filmotokio.exception;

public class FilmNotFoundException extends RuntimeException {

  public FilmNotFoundException() {
    super("Película no encontrada.");
  }
}
