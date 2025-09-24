package com.hightower.dailypractice.core;

import java.util.List;

public interface ProblemRepository {

    void addProblem(final Problem problem);

    List<Problem> listProblems();

    List<String> listCategories();

}
