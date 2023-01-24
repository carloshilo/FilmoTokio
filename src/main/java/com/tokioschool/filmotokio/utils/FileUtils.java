package com.tokioschool.filmotokio.utils;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtils {

    private static ResourceLoader resourceLoader;

    private static Path getRootResourcePath() throws IOException {
        return Paths.get(resourceLoader.getResource("classpath:").getURI());
    }

    public static Path getResourcePath(String directory, String fileName) throws IOException {
        return Paths.get(getRootResourcePath() + File.separator + directory + File.separator + fileName);
    }
}
