package com.hightower.dailypractice.core;

import java.util.List;

public interface SelectionStrategy {

    SelectionStrategy ALL = availableProblems -> availableProblems;

    List<Problem> selectProblems(final List<Problem> availableProblems);

}
