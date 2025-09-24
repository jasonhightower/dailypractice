package com.hightower.dailypractice.core;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public interface Comparison {

    static <T> void assertEqualsUnordered(List<T> expected, List<T> actual) {
        if (expected == null && actual == null) {
            return;
        }
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));
    }
    static <T extends Comparable<T>> void assertEqualsUnordered(T[][] expected, T[][] actual) {
        assertEquals(expected.length, actual.length);
        sort(expected);
        sort(actual);
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i]);
        }
    }
    static <T extends Comparable<T>> void sort(T[][] values) {
        Arrays.sort(values, Comparator.comparingInt(a -> a.length));
        for (T[] value: values) {
            Arrays.sort(value);
        }
    }

}
