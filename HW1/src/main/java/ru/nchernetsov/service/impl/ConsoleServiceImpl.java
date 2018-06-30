package ru.nchernetsov.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.nchernetsov.service.ConsoleService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class ConsoleServiceImpl implements ConsoleService {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String readFromConsole() {
        String string = null;
        try {
            string = bufferedReader.readLine();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return string;
    }

    @Override
    public void writeInConsole(String textToWrite) {
        System.out.println(textToWrite);
    }
}
