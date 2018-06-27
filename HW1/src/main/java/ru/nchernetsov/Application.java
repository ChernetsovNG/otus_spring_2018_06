package ru.nchernetsov;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class Application {

    public static void main(String[] args) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:tests/test-tasks-1.csv");

    }
}
