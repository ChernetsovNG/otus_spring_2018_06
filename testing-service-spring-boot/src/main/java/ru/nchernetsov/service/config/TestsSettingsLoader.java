package ru.nchernetsov.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nchernetsov.config.TestsSettings;

@Service
public class TestsSettingsLoader {
    private final String folder;
    private final int threshold;

    @Autowired
    public TestsSettingsLoader(TestsSettings settings) {
        folder = settings.getFolder();
        threshold = settings.getThreshold();
    }

    public String getFolder() {
        return folder;
    }

    public int getThreshold() {
        return threshold;
    }
}
