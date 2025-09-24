package com.hightower.dailypractice.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectByNameStrategy implements SelectionStrategy {

    private Set<String> problems;

    public SelectByNameStrategy(final String ... problems) {
        this.problems = new HashSet<>(Arrays.asList(problems));
    }

    @Override
    public List<Problem> selectProblems(List<Problem> availableProblems) {
        return availableProblems.stream().filter(p -> this.problems.contains(p.name())).toList();
    }
}
