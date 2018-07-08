package ru.nchernetsov.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nchernetsov.config.ApplicationSettings;

@Service
public class ApplicationSettingsLoader {
    private final boolean loadMockData;
    private final double version;

    @Autowired
    public ApplicationSettingsLoader(ApplicationSettings settings) {
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
