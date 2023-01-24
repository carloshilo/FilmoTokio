package com.tokioschool.filmotokio.exception;

public class UserNotFoundException extends RuntimeException {

  private static final String MESSAGE = "username or password incorrect";

  public UserNotFoundException() {
    super(MESSAGE);
  }
}
