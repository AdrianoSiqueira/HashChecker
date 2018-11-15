package gui;

import core.AppController;
import core.FileOpener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import language.Language;
import language.LanguageManager;

import java.io.File;

public class AppUI extends Application {
    private final AppController controller;

    private Stage stage;
    private Scene scene;

    private MenuBar menuBar;
    private Menu menuSettings;
    private Menu menuSettingsLanguage;
    private Menu menuHelp;
    private CustomMenuItem itemSettingsLanguage;
    private MenuItem itemManual;

    private Spinner<String> spinnerSettingsLanguage;

    private BorderPane paneRoot;
    private ScrollPane scrollBackground;
    private GridPane paneBackground;

    private Label labelTitle;
    private Label labelFileToCheck;
    private Label labelHashFile;

    private TextField fieldFileToCheck;
    private TextField fieldHashFile;

    private Button buttonOpenFileToCheck;
    private Button buttonOpenHashFile;

    private Separator separator;

    public AppUI() {
        this.controller = new AppController();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        initComponents();
        updateLanguage();
        configurePaneBackground();
        configureScrollBackground();
        configureMenuBar();
        configurePaneRoot();
        configureSpinnerSettingsLanguage();
        configureCSS();

        fieldFileToCheck.setEditable(false);
        fieldFileToCheck.setOnAction(event -> buttonOpenFileToCheck.fire());
        fieldFileToCheck.setOnMouseClicked(event -> buttonOpenFileToCheck.fire());

        fieldHashFile.setEditable(false);
        fieldHashFile.setOnAction(event -> buttonOpenHashFile.fire());
        fieldHashFile.setOnMouseClicked(event -> buttonOpenHashFile.fire());

        buttonOpenFileToCheck.setOnAction(event -> {
            final File file = new FileOpener().openFile();
            controller.setFileToCheck(file);
            fieldFileToCheck.setText(file != null ? file.toString() : "");
            run();
        });

        buttonOpenHashFile.setOnAction(event -> {
            final File file = new FileOpener().openHash();
            controller.setHashFile(file);
            fieldHashFile.setText(file != null ? file.toString() : "");
            run();
        });

        configureStage();
    }

    private void initComponents() {
        menuBar = new MenuBar();
        menuSettings = new Menu();
        menuSettingsLanguage = new Menu();
        menuHelp = new Menu();
        itemSettingsLanguage = new CustomMenuItem();
        itemManual = new MenuItem();

        spinnerSettingsLanguage = new Spinner<>();

        paneRoot = new BorderPane();
        scrollBackground = new ScrollPane();
        paneBackground = new GridPane();

        labelTitle = new Label();
        labelFileToCheck = new Label();
        labelHashFile = new Label();

        fieldFileToCheck = new TextField();
        fieldHashFile = new TextField();

        buttonOpenFileToCheck = new Button();
        buttonOpenHashFile = new Button();

        separator = new Separator(Orientation.HORIZONTAL);
    }

    private void configureStage() {
        stage.setMinWidth(531);

        /* Lock vertical resize */
        stage.setMinHeight(240);
        stage.setMaxHeight(240);

        stage.setScene(scene);
        stage.show();
    }

    private void configureMenuBar() {
        itemSettingsLanguage.setContent(spinnerSettingsLanguage);
        itemSettingsLanguage.setHideOnClick(false);

        menuSettingsLanguage.getItems().add(itemSettingsLanguage);

        menuSettings.getItems().add(menuSettingsLanguage);

        menuHelp.setVisible(false);
        menuHelp.getItems().add(itemManual);

        menuBar.getMenus().addAll(menuSettings, menuHelp);
    }

    private void configurePaneRoot() {
        paneRoot.setTop(menuBar);
        paneRoot.setCenter(scrollBackground);
    }

    private void configureScrollBackground() {
        scrollBackground.setContent(paneBackground);
    }

    private void configurePaneBackground() {
        GridPane.setConstraints(labelTitle, 0, 0, 3, 1, HPos.CENTER, VPos.CENTER);

        GridPane.setConstraints(labelFileToCheck, 0, 1, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(fieldFileToCheck, 1, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(buttonOpenFileToCheck, 2, 1, 1, 1, HPos.RIGHT, VPos.CENTER);

        GridPane.setConstraints(separator, 0, 2, 3, 1, HPos.CENTER, VPos.CENTER);

        GridPane.setConstraints(labelHashFile, 0, 3, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(fieldHashFile, 1, 3, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(buttonOpenHashFile, 2, 3, 1, 1, HPos.RIGHT, VPos.CENTER);

        paneBackground.getStyleClass().add("pane-background");
        paneBackground.getChildren().addAll(labelTitle,
                labelFileToCheck, fieldFileToCheck, buttonOpenFileToCheck,
                separator,
                labelHashFile, fieldHashFile, buttonOpenHashFile);
    }

    private void configureSpinnerSettingsLanguage() {
        spinnerSettingsLanguage.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinnerSettingsLanguage.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableList(Language.allNames())));
        spinnerSettingsLanguage.valueProperty().addListener((observable, oldValue, newValue) -> {
            LanguageManager.set(Language.getByName(newValue));
            updateLanguage();
        });
    }

    private void configureCSS() {
        scene = new Scene(paneRoot);

        scene.getRoot().getStylesheets().clear();
        scene.getRoot().getStylesheets().add("/gui/css/AppUI.css");

        labelTitle.getStyleClass().add("label-title");
    }

    private void run() {
        new Thread(() -> Platform.runLater(() -> {
            if (controller.run()) {
                final ReportUI reportUI = new ReportUI(controller.getOfficialHashes(), controller.getCalculatedHashes(), controller.getResults());
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
        stage.setTitle(LanguageManager.get().getString("Hash.Checker") + " v3.0");

        menuSettings.setText(LanguageManager.get().getString("Settings"));
        menuSettingsLanguage.setText(LanguageManager.get().getString("Language"));
        menuHelp.setText(LanguageManager.get().getString("Help"));
        itemManual.setText(LanguageManager.get().getString("Manual"));

        labelTitle.setText(LanguageManager.get().getString("Hash.Checker"));
        labelFileToCheck.setText(LanguageManager.get().getString("File") + ":");
        labelHashFile.setText(LanguageManager.get().getString("Hash") + ":");

        buttonOpenFileToCheck.setText(LanguageManager.get().getString("Open"));
        buttonOpenHashFile.setText(LanguageManager.get().getString("Open"));
    }
}