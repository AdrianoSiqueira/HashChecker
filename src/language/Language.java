package language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Language {
    ENGLISH("English", "en"),
    FRENCH("Français", "fr"),
    PORTUGUESE("Português", "pt"),
    SPANISH("Español", "es");

    private final String name;
    private final String language;

    Language(String name, String language) {
        this.name = name;
        this.language = language;
    }

    public static List<String> allNames() {
        final List<String> names = new ArrayList<>();

        for (Language value : values())
            names.add(value.name);

        Collections.sort(names);
        return names;
    }

    public static Language getByName(final String name) {
        for (Language value : values())
            if (value.name.equals(name))
                return value;
        return ENGLISH;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }
}
