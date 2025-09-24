package com.hightower.dailypractice.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSelectionStrategy implements SelectionStrategy {

    private Random random;
    private int max;

    public RandomSelectionStrategy(int max) {
        this.random = new Random();
        this.max = max;
    }


    @Override
    public List<Problem> selectProblems(List<Problem> availableProblems) {
        if (this.max > availableProblems.size()) {
            return availableProblems;
        }
        List<Problem> selected = new ArrayList<>(availableProblems);
        while (selected.size() > this.max) {
            int toRemove = this.random.nextInt(selected.size());
            selected.remove(toRemove);
        }
        return selected;
    }
}
