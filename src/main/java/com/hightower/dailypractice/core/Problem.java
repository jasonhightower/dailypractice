package com.hightower.dailypractice.core;

import java.util.List;

public record Problem(String name,
                      String description,
                      Difficulty difficulty,
                      List<String> tags,
                      Template template) {

    enum Difficulty {
        EASY,
        MEDIUM,
        HARD;

        public static Difficulty of(final String value) {
            for (Difficulty diff: Difficulty.values()) {
                if (diff.name().equalsIgnoreCase(value)) {
                    return diff;
                }
            }
            return null;
        }

    }

}
