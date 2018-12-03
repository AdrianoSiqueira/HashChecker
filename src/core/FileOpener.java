package core;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import language.LanguageManager;

import java.io.File;

public class FileOpener extends Application {
    private final FileChooser fileChooser;
    private final Stage stage;

    public FileOpener() {
        this.fileChooser = new FileChooser();
        this.fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        /* Fix thread running after close. */
        this.stage = new Stage();
        this.stage.show();
        this.stage.close();
    }

    public File openFile() {
        fileChooser.setTitle(LanguageManager.get("Open.File.to.Check"));
        return fileChooser.showOpenDialog(stage);
    }

    public File openHash() {
        fileChooser.setTitle(LanguageManager.get("Open.Hash.File"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(LanguageManager.get("Hash.Files"), "*.txt", "*.md5", "*.sha1", "*.sha224", "*.sha256", "*.sha384", "*.sha512"),
                new FileChooser.ExtensionFilter(LanguageManager.get("All"), "*")
        );
        return fileChooser.showOpenDialog(stage);
    }

    @Override
    public void start(Stage stage) {
    }
}