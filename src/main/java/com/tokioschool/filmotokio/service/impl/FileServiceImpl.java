package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.exception.ImageUploadException;
import com.tokioschool.filmotokio.service.FileService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Slf4j
@AllArgsConstructor
@Service
public class FileServiceImpl implements FileService {


    private final @NonNull ResourceLoader resourceLoader;

    @Override
    public void saveFile(MultipartFile file, String directory, String fileName)
            throws ImageUploadException {
        log.info("Saving file {} as {}", file, fileName);
        try (InputStream fileStream = file.getInputStream()) {
            Path imagePath = getResourcePath(directory, fileName);
            if (Files.exists(imagePath)) {
                Files.copy(fileStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.write(imagePath, fileStream.readAllBytes(), StandardOpenOption.CREATE_NEW,
                        StandardOpenOption.WRITE);
            }
        } catch (IOException e) {
            log.error("Error saving file {}", fileName, e);
            throw new ImageUploadException("Image Upload Failed", e);
        }
    }

    @Override
    public void deleteFile(String directory, String fileName) {
        log.info("Deleting file {}", fileName);
        try {
            Path toDelete = getResourcePath(directory, fileName);
            Files.deleteIfExists(toDelete);
        } catch (IOException e) {
            log.error("Error when deleting image file {}", fileName);
            throw new ImageUploadException("Image Upload Failed", e);
        }
    }

    private Path getRootResourcePath() throws IOException {
        return Paths.get(resourceLoader.getResource("classpath:").getURI());
    }

    public Path getResourcePath(String directory, String fileName) throws IOException {
        return Paths.get(
                getRootResourcePath() + File.separator + directory + File.separator + fileName);
    }

}
