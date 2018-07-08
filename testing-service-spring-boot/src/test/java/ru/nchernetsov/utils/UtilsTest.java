package ru.nchernetsov.utils;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.nchernetsov.utils.Constants.FILE_SEPARATOR;
import static ru.nchernetsov.utils.Utils.getFileNamesFromResourceFolder;
import static ru.nchernetsov.utils.Utils.listEquals;

public class UtilsTest {

    @Test
    public void equalListEqualsTest() {
        assertTrue(listEquals(
            Arrays.asList(1, 2, 3, 4, 5),
            Arrays.asList(3, 2, 1, 5, 4)
        ));
    }

    @Test
    public void notEqualListEqualsTest() {
        assertFalse(listEquals(
            Arrays.asList(1, 2, 3, 4, 5),
            Arrays.asList(3, 2, 1, 4)
        ));
    }

    @Test
    public void getFileNamesFromResourceFolderTest1() {
        assertThat(getFileNamesFromResourceFolder("tests" + FILE_SEPARATOR + "ru"),
            IsIterableContainingInAnyOrder.containsInAnyOrder("file-without-questions.csv", "math-test.csv", "test-file.csv"));
    }

}
