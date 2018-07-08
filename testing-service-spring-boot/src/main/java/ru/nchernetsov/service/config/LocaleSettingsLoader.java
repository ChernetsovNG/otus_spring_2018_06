package ru.nchernetsov.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nchernetsov.config.LocaleSettings;

@Service
public class LocaleSettingsLoader {
    private final String locale;

    @Autowired
    public LocaleSettingsLoader(LocaleSettings settings) {
        locale = settings.getLocale();
    }

    public String getLocale() {
        return locale;
    }
}
