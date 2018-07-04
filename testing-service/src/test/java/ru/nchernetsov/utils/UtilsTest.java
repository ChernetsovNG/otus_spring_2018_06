package ru.nchernetsov.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nchernetsov.utils.Utils.listEquals;

class UtilsTest {

    @Test
    void equalListEqualsTest() {
        assertTrue(listEquals(
                Arrays.asList(1, 2, 3, 4, 5),
                Arrays.asList(3, 2, 1, 5, 4)
        ));
    }

    @Test
    void notEqualListEqualsTest() {
        assertFalse(listEquals(
                Arrays.asList(1, 2, 3, 4, 5),
                Arrays.asList(3, 2, 1, 4)
        ));
    }
}
