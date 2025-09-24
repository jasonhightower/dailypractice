package com.hightower.dailypractice.core;

import java.util.*;

public class WeightedSelectionStrategy implements SelectionStrategy {

    private int num;
    public WeightedSelectionStrategy(int num) {
        this.num = num;
    }

    @Override
    public List<Problem> selectProblems(final List<Problem> availableProblems) {
        if (this.num >= availableProblems.size()) {
            return availableProblems;
        }
        double[] sums = new double[availableProblems.size()];
        double sum = 0.0;
        for (int i = 0; i < sums.length; i++) {
            sum += toWeight(availableProblems.get(i));
            sums[i] = sum;
        }

        Set<Integer> selected = new HashSet<>();
        Random random = new Random();
        while (selected.size() < this.num) {
            double target = random.nextDouble(sum);
            int index = find(sums, target);
            selected.add(index);
        }
        return selected.stream().map(availableProblems::get).toList();
    }

    private int find(double[] sums, double target) {
        int l = 0;
        int r = sums.length - 1;
        while (l < r) {
            int mid = (l + r)/2;
            if (target < sums[mid] && (mid == 0 || (target > sums[mid -1]))) {
                return mid;
            } else if (target > sums[mid]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        throw new RuntimeException(String.format("Could not find %s in %s", target, sums[sums.length - 1]));
    }

    private double toWeight(Problem problem) {
        return (difficultyWeight(problem) + toWeight(problem.tags()))/2.0;
    }

    private double toWeight(final List<String> categories) {
        return categories.parallelStream().map(this::categoryWeight).reduce(Math::min).orElse(0.25);
    }
    private double difficultyWeight(final Problem p) {
        if (p.difficulty() == null) {
            throw new NullPointerException(String.format("Problem %s has null difficulty", p.name()));
        }
        return switch (p.difficulty()) {
            case EASY -> 0.1;
            case MEDIUM -> 0.5;
            case HARD -> 0.3;
        };
    }
    private double categoryWeight(final String category) {
        return switch (category) {
            case "arrays" -> 0.4;
            case "hashing" -> 0.4;
            case "binarysearch" -> 0.5;
            case "stack" -> 0.4;
            case "twopointer" -> 0.5;
            default -> 0.25;
        };
    }




}
