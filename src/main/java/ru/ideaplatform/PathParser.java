package ru.ideaplatform;

import java.nio.file.Path;

public class PathParser {

    public static Path parse(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Не передано имя файла");
        }

        return Path.of(".", args[0]);
    }
}
