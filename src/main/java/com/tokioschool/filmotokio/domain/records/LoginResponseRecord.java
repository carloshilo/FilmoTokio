package com.tokioschool.filmotokio.domain.records;

import java.io.Serial;
import java.io.Serializable;

public record LoginResponseRecord(String token) implements Serializable {

  @Serial
  private static final long serialVersionUID = -6663478338925398273L;
}

