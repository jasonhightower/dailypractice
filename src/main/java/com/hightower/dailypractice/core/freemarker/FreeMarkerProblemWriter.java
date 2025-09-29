package com.hightower.dailypractice.core.freemarker;

import com.hightower.dailypractice.core.Problem;
import com.hightower.dailypractice.core.ProblemWriter;
import com.hightower.dailypractice.core.TemplateDef;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// TODO JH this should probably just handle constructing the class and test files?
public class FreeMarkerProblemWriter implements ProblemWriter {

    private final String pkg;
    private final Configuration conf;

    // TODO JH this should be externalized via some sort of config
    public FreeMarkerProblemWriter(final String pkg, final Configuration config) {
        this.pkg = pkg;
        this.conf = config;
    }


    @Override
    public void writeSolution(Problem problem) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("problem", problem);
            data.put("pkg", this.pkg);

            TemplateDef template = problem.template();
            data.put("args", template.args());
            data.put("util", new SolutionFunctions());

            Template temp = this.conf.getTemplate(String.format("%s.ftlh", template.name()));

            Path srcPath = Paths.get("src/main/java/" + this.pkg.replace('.', '/'));
            File pkgFolder = srcPath.toFile();
            if (!pkgFolder.exists()) {
                pkgFolder.mkdirs();
            }
            Path solutionPath = srcPath.resolve(String.format("%s.java", problem.name())).toAbsolutePath();
            File solutionFile = solutionPath.toFile();
            try (FileWriter writer = new FileWriter(solutionFile)) {
                temp.process(data, writer);
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            // TODO JH error handling
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeTestCases(Problem problem) {

    }

    public static class SolutionFunctions {
        public String defaultReturn(String type) {
            return switch (type.toLowerCase()) {
                case "string" -> "\"\"";
                case "boolean" -> "false";
                case "int", "long" -> "-1";
                default -> "null";
            };
        }
    }
}
