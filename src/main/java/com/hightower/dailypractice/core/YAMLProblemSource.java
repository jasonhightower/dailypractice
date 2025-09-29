package com.hightower.dailypractice.core;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class YAMLProblemSource implements ProblemSource {

    private final Path root;

    public YAMLProblemSource(final Path root) {
       this.root = root;
    }

    @Override
    public List<Problem> getProblems() {
        List<Problem> problems = new ArrayList<>();
        try {
            Files.walkFileTree(this.root, new YamlVisitor(path -> {
               try {
                   Problem problem = this.loadProblem(path);
                   problems.add(problem);
               } catch (IOException e) {
                   // TODO JH do better than this
                   e.printStackTrace();
               }
            }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return problems;
    }

    private Problem loadProblem(final Path path) throws IOException {
        Yaml yaml = new Yaml();
        try (Reader reader = Files.newBufferedReader(path)) {
            return asProblem(yaml.load(reader));
        }
    }

    private Problem asProblem(final Map<String, Object> values) {
        String name = (String)values.get("name");
        return new Problem(
                name,
                (String)values.get("description"),
                Problem.Difficulty.of((String)values.get("difficulty")),
                (List<String>)values.getOrDefault("tags", Collections.emptyList()),
                asTemplate(name, (Map<String, Object>)values.get("template")));
    }

    private TemplateDef asTemplate(final String name, Map<String, Object> values) {
        if (values == null) {
            return new TemplateDef(name, null);
        }
        String tempName = (String)values.get("name");
        Map<String, Object> args = (Map<String, Object>)values.get("args");
        return new TemplateDef(tempName, args);
    }

    private static final class YamlVisitor extends SimpleFileVisitor<Path> {
        private Consumer<Path> consumer;

        public YamlVisitor(final Consumer<Path> consumer) {
            this.consumer = consumer;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
            if (!attributes.isDirectory() &&
                    file.toString().toLowerCase().endsWith("yaml")) {
                consumer.accept(file);
            }
            return FileVisitResult.CONTINUE;
        }
    }

}
