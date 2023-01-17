package com.tokioschool.filmotokio.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  void saveFile(MultipartFile file, String directory, String fileName);


  void deleteFile(String directory, String fileName);
}
