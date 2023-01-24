package com.tokioschool.filmotokio.utils;

import java.util.UUID;

public class StringUtil {

  private static String getFileExtension(String contentType) {
    return "." + contentType.split("/")[1];
  }


  public static String getFilmPosterFilename(UUID uri, String contentType) {
    return uri.toString() + getFileExtension(contentType);
  }
}
