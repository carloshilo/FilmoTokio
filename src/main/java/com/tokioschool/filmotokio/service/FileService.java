package com.tokioschool.filmotokio.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  void save(MultipartFile file, String directory, String fileName);
}
