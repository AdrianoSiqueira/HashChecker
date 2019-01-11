package gui;

import core.controller.AppBIO;
import core.controller.AppController;
import core.controller.FileOpener;
import extras.language.Language;
import extras.language.LanguageManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.File;

public class AppUI extends Application {
    private AppController controller;

    private Stage stage;

    private Menu menuSettings;
    private Menu menuLanguage;
    private Menu menuHelp;
    private MenuItem itemManual;
    private MenuItem itemAbout;

    private Label labelTitle;
    private Label labelFileToCheck;
    private Label labelHashFile;

    private TextField fieldFileToCheck;
    private TextField fieldHashFile;

    private Button buttonOpenFileToCheck;
    private Button buttonOpenHashFile;

    public AppUI() {
        controller = new AppController();

        menuLanguage = configureMenuLanguage();
        menuSettings = configureMenuSettings();
        itemManual = configureMenuItemManual();
        itemAbout = configureMenuItemAbout();
        menuHelp = configureMenuHelp();

        labelTitle = configureLabel("label-title");
        labelFileToCheck = configureLabel("label-guide");
        labelHashFile = configureLabel("label-guide");

        fieldFileToCheck = configureFieldFileToCheck();
        fieldHashFile = configureFieldHashFile();

        buttonOpenFileToCheck = configureButtonOpenFileToCheck();
        buttonOpenHashFile = configureButtonOpenHashFile();

        stage = configureStage();
    }

    @Override
    public void start(Stage stage) {
        updateLanguage();
    }

    private Button configureButtonOpenFileToCheck() {
        final Button button = new Button();
        button.getStyleClass().add("button-open");
        button.setTooltip(new Tooltip());
        button.setOnAction(event -> {
            final File file = new FileOpener().openFile();
            controller.setFileToCheck(file);
            fieldFileToCheck.setText(file != null ? file.toString() : "");
            run();
        });
        return button;
    }

    private Button configureButtonOpenHashFile() {
        final Button button = new Button();
        button.getStyleClass().add("button-open");
        button.setTooltip(new Tooltip());
        button.setOnAction(event -> {
            final File file = new FileOpener().openHash();
            controller.setHashFile(file);
            fieldHashFile.setText(file != null ? file.toString() : "");
            run();
        });
        return button;
    }

    private TextField configureFieldFileToCheck() {
        final TextField field = new TextField();
        field.setTooltip(new Tooltip());
        field.setEditable(false);
        field.getStyleClass().add("field-file");
        field.setOnAction(event -> buttonOpenFileToCheck.fire());
        field.setOnMouseClicked(event -> buttonOpenFileToCheck.fire());
        return field;
    }

    private TextField configureFieldHashFile() {
        final TextField field = new TextField();
        field.setTooltip(new Tooltip());
        field.setEditable(false);
        field.getStyleClass().add("field-file");
        field.setOnAction(event -> buttonOpenHashFile.fire());
        field.setOnMouseClicked(event -> buttonOpenHashFile.fire());
        return field;
    }

    private Label configureLabel(final String styleClass) {
        final Label label = new Label();
        label.getStyleClass().add(styleClass);
        return label;
    }

    private MenuBar configureMenuBar() {
        return new MenuBar(menuSettings, menuHelp);
    }

    private Menu configureMenuHelp() {
        final Menu menu = new Menu();
        menu.getItems().add(itemManual);
        menu.getItems().add(itemAbout);
        return menu;
    }

    private MenuItem configureMenuItemAbout() {
        final MenuItem item = new MenuItem();
        item.setOnAction(event -> new AboutUI(stage));
        return item;
    }

    private MenuItem configureMenuItemLanguage() {
        return new CustomMenuItem(configureSpinnerLanguage(), false);
    }

    private MenuItem configureMenuItemManual() {
        final MenuItem item = new MenuItem();
        item.setOnAction(event -> new ManualUI());
        return item;
    }

    private Menu configureMenuLanguage() {
        final Menu menu = new Menu();
        menu.getItems().add(configureMenuItemLanguage());
        return menu;
    }

    private Menu configureMenuSettings() {
        final Menu menu = new Menu();
        menu.getItems().add(menuLanguage);
        return menu;
    }

    private GridPane configurePaneBackground() {
        /* Enable column #1 to grow when window resize */
        GridPane.setHgrow(fieldFileToCheck, Priority.ALWAYS);

        final GridPane pane = new GridPane();
        pane.getStyleClass().add("pane-background");
        pane.add(labelTitle, 0, 0, 3, 1);
        pane.add(labelFileToCheck, 0, 1);
        pane.add(fieldFileToCheck, 1, 1);
        pane.add(buttonOpenFileToCheck, 2, 1);
        pane.add(new Separator(Orientation.HORIZONTAL), 0, 2, 3, 1);
        pane.add(labelHashFile, 0, 3);
        pane.add(fieldHashFile, 1, 3);
        pane.add(buttonOpenHashFile, 2, 3);
        return pane;
    }

    private BorderPane configurePaneRoot() {
        final BorderPane pane = new BorderPane();
        pane.setTop(configureMenuBar());
        pane.setCenter(configureScrollBackground());
        return pane;
    }

    private Scene configureScene() {
        final Scene scene = new Scene(configurePaneRoot());
        scene.getStylesheets().addAll("/gui/css/appui/Button.css", "/gui/css/appui/Label.css", "/gui/css/appui/Pane.css", "/gui/css/appui/Scroll.css", "/gui/css/appui/TextField.css", "/gui/css/share/Tooltip.css");
        return scene;
    }

    private ScrollPane configureScrollBackground() {
        final ScrollPane scroll = new ScrollPane(configurePaneBackground());
        scroll.getStyleClass().add("scroll-background");
        return scroll;
    }

    private Spinner<String> configureSpinnerLanguage() {
        final Spinner<String> spinner = new Spinner<>(FXCollections.observableList(Language.allNames()));
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            LanguageManager.set(Language.getByName(newValue));
            updateLanguage();
        });
        return spinner;
    }

    private Stage configureStage() {
        final Stage stage = new Stage();
        stage.setMinWidth(531);

        /* Lock vertical resize */
        stage.setMinHeight(240);
        stage.setMaxHeight(240);

        stage.setScene(configureScene());
        stage.show();
        return stage;
    }

    private void run() {
        new Thread(() -> Platform.runLater(() -> {
            if (controller.run()) {
                final ReportUI reportUI = new ReportUI(controller.getOfficialHashes(), controller.getCalculatedHashes(), controller.getResults(), stage);
                reportUI.getStage().showingProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        fieldFileToCheck.clear();
                        fieldHashFile.clear();
                        controller.reset();
                    }
                });
            }
        })).start();
    }

    private void updateLanguage() {
        stage.setTitle(LanguageManager.get("Hash.Checker") + " v" + AppBIO.VERSION);

        menuSettings.setText(LanguageManager.get("Settings"));
        menuLanguage.setText(LanguageManager.get("Language"));
        menuHelp.setText(LanguageManager.get("Help"));
        itemManual.setText(LanguageManager.get("Manual"));
        itemAbout.setText(LanguageManager.get("About"));

        labelTitle.setText(LanguageManager.get("Hash.Checker"));

        labelFileToCheck.setText(LanguageManager.get("File") + ":");
        labelFileToCheck.setTooltip(new Tooltip(LanguageManager.get("File.to.be.checked")));

        labelHashFile.setText(LanguageManager.get("Hash") + ":");
        labelHashFile.setTooltip(new Tooltip(LanguageManager.get("File.with.official.hashes")));

        fieldFileToCheck.setTooltip(new Tooltip(LanguageManager.get("Click.to.open.file.to.check")));
        fieldHashFile.setTooltip(new Tooltip(LanguageManager.get("Click.to.open.hash.file")));

        buttonOpenFileToCheck.setText(LanguageManager.get("Open"));
        buttonOpenFileToCheck.getTooltip().setText(LanguageManager.get("Open.File.to.Check"));

        buttonOpenHashFile.setText(LanguageManager.get("Open"));
        buttonOpenHashFile.getTooltip().setText(LanguageManager.get("Open.Hash.File"));
    }
}

/*
 * MenuBar Scheme:
 *
 * MenuBar
 *  > MenuSettings
 *      > MenuLanguage
 *          > ItemLanguage
 *              > SpinnerLanguage
 *  > MenuHelp
 *      > ItemManual
 *      > ItemAbout
 */