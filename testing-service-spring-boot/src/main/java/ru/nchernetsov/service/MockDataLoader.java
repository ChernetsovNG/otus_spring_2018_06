package ru.nchernetsov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nchernetsov.config.ApplicationSettings;

@Service
public class MockDataLoader {
    private final boolean loadMockData;
    private final double version;

    @Autowired
    public MockDataLoader(ApplicationSettings settings) {
        loadMockData = settings.isLoadMockData();
        version = settings.getVersion();
    }

    public boolean isLoadMockData() {
        return loadMockData;
    }

    public double getVersion() {
        return version;
    }
}
