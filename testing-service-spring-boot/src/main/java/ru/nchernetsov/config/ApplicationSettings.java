package ru.nchernetsov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class ApplicationSettings {

    private boolean loadMockData;

    private double version;

    public boolean isLoadMockData() {
        return loadMockData;
    }

    public void setLoadMockData(boolean loadMockData) {
        this.loadMockData = loadMockData;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }
}
