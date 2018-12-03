package gui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import language.LanguageManager;

import java.util.ArrayList;
import java.util.List;

public class ManualUI extends Application {
    private final ScrollPane scrollRoot;
    private final GridPane paneBackground;

    private final VBox paneMenuIndex;
    private final List<Label> labelsMenu;

    private final Separator separator;

    private final Accordion accordion;
    private final List<TitledPane> panesManual;

    {
        labelsMenu = configureLabelsMenu();
        paneMenuIndex = configurePaneMenuIndex();

        separator = new Separator(Orientation.VERTICAL);

        panesManual = configurePanesManual();
        accordion = configureAccordion();

        paneBackground = configurePaneBackground();
        scrollRoot = configureScrollRoot();

        start(configureStage());
    }

    @Override
    public void start(Stage stage) {
    }

    private Accordion configureAccordion() {
        final Accordion accordion = new Accordion();
        accordion.getStyleClass().add("accordion-manual");
        accordion.getPanes().addAll(panesManual);
        accordion.expandedPaneProperty().addListener((observable, oldValue, newValue) -> {
            final int index = panesManual.indexOf(newValue);
            for (int i = 0; i < labelsMenu.size(); ++i)
                labelsMenu.get(i).setId(i == index ? "label-menu-index-selected" : "");
        });
        accordion.setExpandedPane(accordion.getPanes().get(0));
        return accordion;
    }

    private List<Label> configureLabelsMenu() {
        final List<Label> list = new ArrayList<>();
        list.add(new Label(LanguageManager.get("Information")));
        list.add(new Label(LanguageManager.get("How.To.Use")));
        list.add(new Label(LanguageManager.get("Operation")));
        list.add(new Label(LanguageManager.get("Supported.Encryptions")));
        list.add(new Label(LanguageManager.get("Language")));

        for (Label label : list) {
            label.getStyleClass().add("label-menu-index");
            label.setOnMouseClicked(event -> accordion.setExpandedPane(accordion.getPanes().get(list.indexOf(label))));
        }

        return list;
    }

    private GridPane configurePaneBackground() {
        GridPane.setHgrow(accordion, Priority.ALWAYS);
        GridPane.setVgrow(accordion, Priority.ALWAYS);

        final GridPane pane = new GridPane();
        pane.getStyleClass().add("pane-background");
        pane.add(paneMenuIndex, 0, 0);
        pane.add(separator, 1, 0);
        pane.add(accordion, 2, 0);
        return pane;
    }

    private List<TitledPane> configurePanesManual() {
        final List<TitledPane> list = new ArrayList<>();
        list.add(createPaneInformation());
        list.add(createPaneHowToUse());
        list.add(createPaneOperation());
        list.add(createPaneSupportedEncryptions());
        list.add(createPaneLanguage());
        return list;
    }

    private VBox configurePaneMenuIndex() {
        final VBox box = new VBox();
        box.getStyleClass().add("pane-menu-index");
        box.getChildren().addAll(labelsMenu);
        return box;
    }

    private Scene configureScene() {
        final Scene scene = new Scene(scrollRoot);
        scene.getRoot().getStylesheets().clear();
        scene.getRoot().getStylesheets().add("/gui/css/ManualUI.css");
        return scene;
    }

    private ScrollPane configureScrollRoot() {
        final ScrollPane pane = new ScrollPane(paneBackground);
        pane.getStyleClass().add("scroll-root");
        return pane;
    }

    private Stage configureStage() {
        final Stage stage = new Stage();
        stage.setTitle(LanguageManager.get("Hash.Checker.Manual"));
        stage.setScene(configureScene());
        stage.show();
        return stage;
    }

    private TitledPane createPaneHowToUse() {
        final TitledPane paneHowToUse = new TitledPane();
        paneHowToUse.setText(LanguageManager.get("How.To.Use"));

        final StackPane paneContent = new StackPane();
        paneContent.getStyleClass().addAll("pane-content-manual", "pane-content-manual-how-to-use");

        final ScrollPane scrollContent = new ScrollPane(paneContent);
        scrollContent.getStyleClass().add("scroll-content-manual");

        final Label label = new Label(LanguageManager.get("pane.how.to.use.content"));
        label.getStyleClass().add("label-content-manual");

        paneContent.getChildren().add(label);
        paneHowToUse.setContent(scrollContent);

        return paneHowToUse;
    }

    private TitledPane createPaneInformation() {
        final TitledPane paneInformation = new TitledPane();
        paneInformation.setText(LanguageManager.get("Information"));

        final StackPane paneContent = new StackPane();
        paneContent.getStyleClass().addAll("pane-content-manual", "pane-content-manual-information");

        final ScrollPane scrollContent = new ScrollPane(paneContent);
        scrollContent.getStyleClass().add("scroll-content-manual");
        scrollContent.setOnMouseClicked(event -> System.out.println(scrollContent.getWidth() + " x " + scrollContent.getHeight()));

        final Label label = new Label(LanguageManager.get("pane.information-content"));
        label.getStyleClass().add("label-content-manual");

        paneContent.getChildren().add(label);
        paneInformation.setContent(scrollContent);

        return paneInformation;
    }

    private TitledPane createPaneLanguage() {
        final TitledPane paneLanguage = new TitledPane();
        paneLanguage.setText(LanguageManager.get("Language"));

        final StackPane paneContent = new StackPane();
        paneContent.getStyleClass().addAll("pane-content-manual", "pane-content-manual-language");

        final ScrollPane scrollContent = new ScrollPane(paneContent);
        scrollContent.getStyleClass().add("scroll-content-manual");

        final Label label = new Label(LanguageManager.get("pane.language.content"));
        label.getStyleClass().add("label-content-manual");

        paneContent.getChildren().add(label);
        paneLanguage.setContent(scrollContent);

        return paneLanguage;
    }

    private TitledPane createPaneOperation() {
        final TitledPane paneOperation = new TitledPane();
        paneOperation.setText(LanguageManager.get("Operation"));

        final VBox paneContent = new VBox();
        paneContent.getStyleClass().addAll("pane-content-manual", "pane-content-manual-operation");

        final ScrollPane scrollContent = new ScrollPane(paneContent);
        scrollContent.getStyleClass().add("scroll-content-manual");

        final Label[] labels = new Label[4];

        labels[0] = new Label(LanguageManager.get("pane.operation.content.1"));
        labels[0].getStyleClass().add("label-content-manual");

        labels[1] = new Label(LanguageManager.get("pane.operation.content.2"));
        labels[1].getStyleClass().add("label-content-manual");

        labels[2] = new Label(LanguageManager.get("pane.operation.content.3"));
        labels[2].getStyleClass().add("label-content-manual");

        labels[3] = new Label(LanguageManager.get("pane.operation.content.4"));
        labels[3].getStyleClass().add("label-content-manual");

        paneContent.getChildren().addAll(labels);
        paneOperation.setContent(scrollContent);

        return paneOperation;
    }

    private TitledPane createPaneSupportedEncryptions() {
        final TitledPane paneSupportedEncryptions = new TitledPane();
        paneSupportedEncryptions.setText(LanguageManager.get("Supported.Encryptions"));

        final GridPane paneContent = new GridPane();
        paneContent.getStyleClass().addAll("pane-content-manual", "pane-content-manual-encryption");

        final ScrollPane scrollContent = new ScrollPane(paneContent);
        scrollContent.getStyleClass().add("scroll-content-manual");

        final Label[] labels = new Label[6];

        labels[0] = new Label("MD5");
        labels[0].getStyleClass().add("label-content-manual");

        labels[1] = new Label("SHA-1");
        labels[1].getStyleClass().add("label-content-manual");

        labels[2] = new Label("SHA-224");
        labels[2].getStyleClass().add("label-content-manual");

        labels[3] = new Label("SHA-256");
        labels[3].getStyleClass().add("label-content-manual");

        labels[4] = new Label("SHA-384");
        labels[4].getStyleClass().add("label-content-manual");

        labels[5] = new Label("SHA-512");
        labels[5].getStyleClass().add("label-content-manual");

        paneContent.add(labels[0], 0, 0);
        paneContent.add(labels[1], 1, 0);
        paneContent.add(labels[2], 2, 0);
        paneContent.add(labels[3], 0, 1);
        paneContent.add(labels[4], 1, 1);
        paneContent.add(labels[5], 2, 1);

        paneSupportedEncryptions.setContent(scrollContent);
        return paneSupportedEncryptions;
    }
}