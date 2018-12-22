package extras.language;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static final String BASE_NAME = "extras.language.language";
    private static ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, Locale.getDefault());

    public static void set(Language language) {
        bundle = ResourceBundle.getBundle(BASE_NAME, new Locale(language.getLanguage()));
    }

    public static String get(final String key) {
        return bundle.getString(key);
    }
}
