package com.maze.stulog.image.util;

import com.maze.stulog.image.domain.FileExtension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileNameUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS");

    public static String createFileName(String fileName) {
        String name = FORMATTER.format(LocalDateTime.now());
        FileExtension extension = FileExtension.from(fileName);

        return name.concat(extension.getExtension());
    }
}
