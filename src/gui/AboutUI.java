package gui;

import core.controller.AppBIO;
import extras.language.LanguageManager;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class AboutUI extends Application {
    private Map<String, Label> labels;

    private Stage parent;

    public AboutUI(Stage parent) {
        this.parent = parent;
        labels = configureLabels();
        configureStage();
    }

    @Override
    public void start(Stage stage) {
    }

    private Label configureLabel(final String text, final String styleClass) {
        final Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    private Map<String, Label> configureLabels() {
        final Map<String, Label> list = new HashMap<>();
        list.put("s-developer", configureLabel(LanguageManager.get("Developer") + ":", "label-subject"));
        list.put("s-github-account", configureLabel(LanguageManager.get("Github.Account") + ":", "label-subject"));
        list.put("s-title", configureLabel(LanguageManager.get("Title") + ":", "label-subject"));
        list.put("s-version", configureLabel(LanguageManager.get("Version") + ":", "label-subject"));
        list.put("s-source", configureLabel(LanguageManager.get("Source.code") + ":", "label-subject"));

        list.put("c-developer", configureLabel("Adriano Siqueira", "label-content"));
        list.put("c-title", configureLabel(LanguageManager.get("Hash.Checker"), "label-content"));
        list.put("c-version", configureLabel(AppBIO.VERSION, "label-content"));
        return list;
    }

    private Hyperlink configureLink(final String text) {
        final Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.getStyleClass().add("link-content");
        hyperlink.visitedProperty().bind(new SimpleBooleanProperty(false));
        hyperlink.setOnMouseClicked(event -> getHostServices().showDocument(hyperlink.getText()));
        return hyperlink;
    }

    private GridPane configurePaneRoot() {
        final GridPane pane = new GridPane();
        pane.getStyleClass().add("pane-root");
        pane.add(labels.get("s-title"), 0, 0);
        pane.add(labels.get("c-title"), 1, 0);
        pane.add(labels.get("s-version"), 0, 1);
        pane.add(labels.get("c-version"), 1, 1);
        pane.add(labels.get("s-source"), 0, 2);
        pane.add(configureLink("github.com/AdrianoSiqueira/HashChecker"), 1, 2);
        pane.add(new Separator(), 0, 3, 2, 1);
        pane.add(labels.get("s-developer"), 0, 4);
        pane.add(labels.get("c-developer"), 1, 4);
        pane.add(labels.get("s-github-account"), 0, 5);
        pane.add(configureLink("github.com/AdrianoSiqueira"), 1, 5);
        return pane;
    }

    private Scene configureScene() {
        final Scene scene = new Scene(configurePaneRoot());
        scene.getStylesheets().addAll("/gui/css/aboutui/Label.css", "/gui/css/aboutui/Link.css", "/gui/css/aboutui/Pane.css");
        return scene;
    }

    private void configureStage() {
        final Stage stage = new Stage();
        stage.setTitle(LanguageManager.get("About.Hash.Checker"));
        stage.setResizable(false);
        stage.setScene(configureScene());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parent);
        stage.show();

        labels.get("s-developer").setOnMouseClicked(event -> System.out.println(stage.getWidth() + " X " + stage.getHeight()));
    }
}