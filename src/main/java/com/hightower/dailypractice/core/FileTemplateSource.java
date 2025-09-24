package com.hightower.dailypractice.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTemplateSource {

    private Path root;

    public FileTemplateSource(final Path root) {
       this.root = root;
    }

    public String getTemplate(final String type, final String name) throws IOException {
        Path templatePath = this.root.resolve(type + "/" + name + ".template");
        if (templatePath.toFile().exists()) {
            return Files.readString(templatePath);
        }
        return null;
    }

}
