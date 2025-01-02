package com.maze.stulog.image.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileName {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS");

    private final String fileName;

    private FileName(String fileName) {
        this.fileName = fileName;
    }

    public static FileName from(String originalFileName) {
        String fileName = FORMATTER.format(LocalDateTime.now());
        String extension = extractExtension(originalFileName);

        return new FileName(fileName + extension);
    }

    private static String extractExtension(String originalFileName) {
        return FileExtension.from(originalFileName)
                .getExtension();
    }

    public String getFileName() {
        return fileName;
    }
}
