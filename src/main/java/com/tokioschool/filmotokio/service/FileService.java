package com.tokioschool.filmotokio.service;

import com.tokioschool.filmotokio.exception.ImageUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  void saveUserImage(MultipartFile file, String fileName) throws ImageUploadException;

  void saveFilmImage(MultipartFile file, String fileName) throws ImageUploadException;


  void deleteUserImage(String fileName);

  void deleteFilmImage(String fileName);

}
