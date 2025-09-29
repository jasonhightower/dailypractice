package com.hightower.dailypractice.core;

import com.hightower.dailypractice.core.freemarker.FreeMarkerProblemWriter;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;


public class DailyPractice {

    enum Strategy {
        ALL(SelectionStrategy.ALL),
        RANDOM_5(new RandomSelectionStrategy(5)),
        WEIGHTED_5(new WeightedSelectionStrategy(5));

        SelectionStrategy value;
        Strategy(SelectionStrategy value) {
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException {
        // check if SQLLite db exists
        // if not initialize the SQLLite db

        // check if new problems exist to be added?

        Strategy strategy;
        strategy = Strategy.ALL;
//        strategy = Strategy.RANDOM_5;
//        strategy = Strategy.WEIGHTED_5;

        SelectionStrategy selectionStrategy = strategy.value;
//        SelectionStrategy selectionStrategy = new SelectByNameStrategy("ReverseNodesInKGroup");

        ProblemSource source = new YAMLProblemSource(resourceAsPath("/problems"));
        List<Problem> problems = source.getProblems();

        // TODO JH consider having the selection strategy return a stream
        // then limit can be handled as part of stream processing
        List<Problem> selectedProblems = selectionStrategy.selectProblems(problems);
        if (selectedProblems.isEmpty()) {
            throw new RuntimeException("No problems selected");
        }

        // generate random problems based on configured preferences and past performance.
        // TODO JH load problems that have already been added to today's set

        // it would be better to remove selected items from the list of available problems
        for (Problem problem: selectedProblems) {
            // TODO JH this random selection logic needs to be improved greatly
            FileTemplateSource templateSource = new FileTemplateSource(resourceAsPath("/templates"));

            TemplateDef problemTemplate = problem.template();
            String template = templateSource.getTemplate("java", problemTemplate.name());
            // TODO JH add a test template
            String testTemplate = templateSource.getTemplate("java", problem.name() + "Test");
            if (testTemplate == null) {
                throw new RuntimeException("Test template does not exist for " + problem.name());
            }

            final String pkg = "com.hightower.dailypractice.fmtest." + today();

            Configuration config = new Configuration(Configuration.VERSION_2_3_34);
            config.setClassForTemplateLoading(DailyPractice.class, "/templates/java/");
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            config.setLogTemplateExceptions(false);
            config.setWrapUncheckedExceptions(true);
            config.setFallbackOnNullLoopVariable(false);
            config.setSQLDateAndTimeTimeZone(TimeZone.getDefault());

            ProblemWriter writer = new FreeMarkerProblemWriter(pkg, config);
            writer.writeSolution(problem);

            /*
            File parentFolder = srcPath.toFile();
            if (!parentFolder.exists()) {
                parentFolder.mkdirs();
            }
            Path solutionPath = srcPath.resolve(Character.toUpperCase(problem.name().charAt(0)) + problem.name().substring(1) + ".java").toAbsolutePath();
            try {
                Files.writeString(
                        solutionPath,
                        resolveTemplate(template, pkg, problem),
                        StandardOpenOption.CREATE_NEW);
            } catch (FileAlreadyExistsException e) {
                System.err.println("Solution for " + problem.name() + " already exists.");
            }
             */

            Path testPath = Paths.get("src/test/java/" + pkg.replace('.', '/'));
            File testParentFolder = testPath.toFile();
            if (!testParentFolder.exists()) {
                testParentFolder.mkdirs();
            }
            Path testCasePath = testPath.resolve(Character.toUpperCase(problem.name().charAt(0)) + problem.name().substring(1) + "Test.java").toAbsolutePath();
            Files.writeString(
                    testCasePath,
                    resolveTemplate(testTemplate, pkg, problem),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        }


        // have a script to support running the current days generated tests
    }

    private static String getProblemName(final Path problemSrcFile) {
        String fileName = problemSrcFile.toString();
        int lastSlash = fileName.lastIndexOf('/');
        if (lastSlash >= 0) {
            fileName = fileName.substring(lastSlash);
        }
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot >= 0) {
            fileName = fileName.substring(0, lastDot);
        }
        return fileName;
    }

    private static String resolveTemplate(String template, final String pkg, final Problem problem) {
        TemplateDef templateDef = problem.template();
        Map<String, Object> args = templateDef.args();
        Map<String, String> values = new HashMap<>();
        values.put("name", problem.name());
        values.put("pkg", pkg);
        values.put("description", problem.description());
        if (args != null) {
            if (args.containsKey("input")) {
                // this is a bit of a hack until we have a proper template library/mechanism
                List<Object> input = (List<Object>)args.get("input");
                StringBuilder params = new StringBuilder();
                for (Object inputParam: input) {
                    Map<String, Object> ip = (Map<String, Object>) inputParam;
                    if (!params.isEmpty()) {
                        params.append(", ");
                    }
                    params.append(ip.get("type"));
                    params.append(" ");
                    params.append(ip.get("name"));
                }
                values.put("params", params.toString());
            }
            if (args.containsKey("output")) {
                String output = (String)args.get("output");
                values.put("output", output);
                values.put("default", switch (output.toLowerCase()) {
                    case "string" -> "\"\"";
                    case "boolean" -> "false";
                    case "int", "long" -> "-1";
                    default -> "null";
                });
            }
        }
        for (Map.Entry<String, String> entry: values.entrySet()) {
            template = template.replace("{"+entry.getKey()+"}", entry.getValue());
        }
        return template;
    }

    private static String today() {
        final int START_DATE = 262;
        return "Day" + (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - START_DATE);
    }

    private static Path resourceAsPath(String resource) {
        URL url = DailyPractice.class.getResource(resource);
        if (url == null) {
            throw new RuntimeException("COULDN'T LOAD RESOURCE DIR");
        }
        try {
            return Path.of(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
