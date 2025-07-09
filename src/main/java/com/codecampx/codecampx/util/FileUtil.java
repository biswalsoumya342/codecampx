package com.codecampx.codecampx.util;

import com.codecampx.codecampx.exception.ResorceNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtil {

    public static Path createTempDirectory() throws IOException {
        String projectRoot = System.getProperty("user.dir");
        String temp = projectRoot+ "/temp/code-"+System.currentTimeMillis()+"_"+ UUID.randomUUID();
        Path path = Paths.get(temp);
        return Files.createDirectories(path);
    }

    public static void deleteTempDirectory(Path path) {
        if (Files.exists(path)){
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException("File Not Found To Delete");
            }
        }
    }
}
