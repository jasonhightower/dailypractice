package com.hightower.dailypractice.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Context {

    private final String pkg;

    private Path sourcePath;
    private Path testPath;

    private ProblemSource problemSource;

    public Context(final String pkg, final ProblemSource problemSource) {
       this.pkg = pkg;
    }

    public String getPackage() {
        return this.pkg;
    }

    public Path sourcePath() {
        if (this.sourcePath == null) {
            this.sourcePath = Paths.get("src/main/java/" + this.pkg.replace('.', '/'));
        }
        return this.sourcePath;
    }

    public Path testPath() {
        if (this.sourcePath == null) {
            this.sourcePath = Paths.get("src/main/java/" + this.pkg.replace('.', '/'));
        }
        return this.sourcePath;
    }

    public boolean alreadySelected(final Problem problem) {
        return solutionPath(problem).toFile().exists();
    }

    public Path solutionPath(Problem problem) {
        return sourcePath().resolve(problem.name() + ".java").toAbsolutePath();
    }

    public Path testCasePath(Problem problem) {
        return testPath().resolve(problem.name() + "Test.java").toAbsolutePath();
    }

}
