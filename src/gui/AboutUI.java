package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
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

    private Label configureLabelContentAccount() {
        final Label label = new Label("github.com/AdrianoSiqueira");
        label.getStyleClass().add("label-content");
        label.setTooltip(new Tooltip(LanguageManager.get("Click.to.copy.to.clipboard")));
        label.setOnMouseClicked(event -> {
            final ClipboardContent content = new ClipboardContent();
            content.putString(label.getText());

            final Clipboard clipboard = Clipboard.getSystemClipboard();
            clipboard.setContent(content);
        });
        return label;
    }

    private Label configureLabelContentDeveloper() {
        final Label label = new Label("Adriano Siqueira");
        label.getStyleClass().add("label-content");
        return label;
    }

    private Label configureLabelContentSource() {
        final Label label = new Label("github.com/AdrianoSiqueira/HashChecker");
        label.getStyleClass().add("label-content");
        label.setTooltip(new Tooltip(LanguageManager.get("Click.to.copy.to.clipboard")));
        label.setOnMouseClicked(event -> {
            final ClipboardContent content = new ClipboardContent();
            content.putString(label.getText());

            final Clipboard clipboard = Clipboard.getSystemClipboard();
            clipboard.setContent(content);
        });
        return label;
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
        list.add(configureLabelContentAccount());
        list.add(configureLabelSubjectTitle());
        list.add(configureLabelContentTitle());
        list.add(configureLabelSubjectVersion());
        list.add(configureLabelContentVersion());
        list.add(configureLabelSubjectSource());
        list.add(configureLabelContentSource());
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
        pane.add(labels.get(4), 0, 0);
        pane.add(labels.get(5), 1, 0);
        pane.add(labels.get(6), 0, 1);
        pane.add(labels.get(7), 1, 1);
        pane.add(labels.get(8), 0, 2);
        pane.add(labels.get(9), 1, 2);
        pane.add(new Separator(), 0, 3, 2, 1);
        pane.add(labels.get(0), 0, 4);
        pane.add(labels.get(1), 1, 4);
        pane.add(labels.get(2), 0, 5);
        pane.add(labels.get(3), 1, 5);
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