package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import language.LanguageManager;

import java.util.ArrayList;
import java.util.List;

public class AboutUI extends Application {
    private final List<Label> labels;
    private final GridPane paneRoot;

    {
        labels = configureLabels();
        paneRoot = configurePaneRoot();
        configureStage();
    }

    @Override
    public void start(Stage stage) {
    }

    private Hyperlink configureLinkContentAccount() {
        final Hyperlink hyperlink = new Hyperlink("github.com/AdrianoSiqueira");
        hyperlink.getStyleClass().add("link-content");
        hyperlink.visitedProperty().addListener((observable, oldValue, newValue) -> hyperlink.setVisited(false));
        hyperlink.setOnMouseClicked(event -> getHostServices().showDocument(hyperlink.getText()));
        return hyperlink;
    }

    private Label configureLabelContentDeveloper() {
        final Label label = new Label("Adriano Siqueira");
        label.getStyleClass().add("label-content");
        return label;
    }

    private Hyperlink configureLinkContentSource() {
        final Hyperlink hyperlink = new Hyperlink("github.com/AdrianoSiqueira/HashChecker");
        hyperlink.getStyleClass().add("link-content");
        hyperlink.visitedProperty().addListener((observable, oldValue, newValue) -> hyperlink.setVisited(false));
        hyperlink.setOnMouseClicked(event -> getHostServices().showDocument(hyperlink.getText()));
        return hyperlink;
    }

    private Label configureLabelContentTitle() {
        final Label label = new Label(LanguageManager.get("Hash.Checker"));
        label.getStyleClass().add("label-content");
        return label;
    }

    private Label configureLabelContentVersion() {
        final Label label = new Label("3.1");
        label.getStyleClass().add("label-content");
        return label;
    }

    private List<Label> configureLabels() {
        final List<Label> list = new ArrayList<>();
        list.add(configureLabelSubjectDeveloper());
        list.add(configureLabelContentDeveloper());
        list.add(configureLabelSubjectAccount());
        list.add(configureLabelSubjectTitle());
        list.add(configureLabelContentTitle());
        list.add(configureLabelSubjectVersion());
        list.add(configureLabelContentVersion());
        list.add(configureLabelSubjectSource());
        return list;
    }

    private Label configureLabelSubjectAccount() {
        final Label label = new Label(LanguageManager.get("Github.Account") + ":");
        label.getStyleClass().add("label-subject");
        return label;
    }

    private Label configureLabelSubjectDeveloper() {
        final Label label = new Label(LanguageManager.get("Developer") + ":");
        label.getStyleClass().add("label-subject");
        return label;
    }

    private Label configureLabelSubjectTitle() {
        final Label label = new Label(LanguageManager.get("Title") + ":");
        label.getStyleClass().add("label-subject");
        return label;
    }

    private Label configureLabelSubjectVersion() {
        final Label label = new Label(LanguageManager.get("Version") + ":");
        label.getStyleClass().add("label-subject");
        return label;
    }

    private Label configureLabelSubjectSource() {
        final Label label = new Label(LanguageManager.get("Source.code") + ":");
        label.getStyleClass().add("label-subject");
        return label;
    }

    private GridPane configurePaneRoot() {
        final GridPane pane = new GridPane();
        pane.getStyleClass().add("pane-root");
        pane.add(labels.get(3), 0, 0);
        pane.add(labels.get(4), 1, 0);
        pane.add(labels.get(5), 0, 1);
        pane.add(labels.get(6), 1, 1);
        pane.add(labels.get(7), 0, 2);
        pane.add(configureLinkContentSource(), 1, 2);
        pane.add(new Separator(), 0, 3, 2, 1);
        pane.add(labels.get(0), 0, 4);
        pane.add(labels.get(1), 1, 4);
        pane.add(labels.get(2), 0, 5);
        pane.add(configureLinkContentAccount(), 1, 5);
        return pane;
    }

    private Scene configureScene() {
        final Scene scene = new Scene(paneRoot);
        scene.getRoot().getStylesheets().clear();
        scene.getRoot().getStylesheets().add("/gui/css/AboutUI.css");
        return scene;
    }

    private void configureStage() {
        final Stage stage = new Stage();
        stage.setTitle(LanguageManager.get("About.Hash.Checker"));
        stage.setResizable(false);
        stage.setScene(configureScene());
        stage.show();
    }
}