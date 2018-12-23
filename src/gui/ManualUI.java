package gui;

import extras.language.LanguageManager;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ManualUI extends Application {
    private List<Label> sectionsIndex;

    private Accordion accordion;
    private List<TitledPane> sectionsManual;

    public ManualUI() {
        sectionsIndex = configureSectionsIndex();
        sectionsManual = configureSectionsManual();
        accordion = configureAccordion();

        configureStage();
    }

    @Override
    public void start(Stage stage) {
    }

    private Accordion configureAccordion() {
        final Accordion accordion = new Accordion();
        accordion.getStyleClass().add("accordion-manual");
        accordion.getPanes().addAll(sectionsManual);
        accordion.expandedPaneProperty().addListener((observable, oldValue, newValue) -> {
            final int index = sectionsManual.indexOf(newValue);
            for (int i = 0; i < sectionsIndex.size(); ++i)
                sectionsIndex.get(i).setId(i == index ? "label-menu-index-selected" : "");
        });
        accordion.setExpandedPane(accordion.getPanes().get(0));
        return accordion;
    }

    private GridPane configurePaneBackground() {
        GridPane.setHgrow(accordion, Priority.ALWAYS);
        GridPane.setVgrow(accordion, Priority.ALWAYS);

        final GridPane pane = new GridPane();
        pane.getStyleClass().add("pane-background");
        pane.add(configurePaneSectionsIndex(), 0, 0);
        pane.add(new Separator(Orientation.VERTICAL), 1, 0);
        pane.add(accordion, 2, 0);
        return pane;
    }

    private VBox configurePaneSectionsIndex() {
        final VBox box = new VBox();
        box.getStyleClass().add("pane-menu-index");
        box.getChildren().addAll(sectionsIndex);
        return box;
    }

    private Scene configureScene() {
        final Scene scene = new Scene(configurePaneBackground());
        scene.getStylesheets().addAll("/gui/css/manualui/Accordion.css",
                "/gui/css/manualui/Label.css",
                "/gui/css/manualui/Pane.css",
                "/gui/css/manualui/Scroll.css",
                "/gui/css/share/Tooltip.css");
        return scene;
    }

    private TitledPane configureSectionHowToUse() {
        final Label label = new Label(LanguageManager.get("pane.how.to.use.content"));
        label.getStyleClass().add("label-content-manual");

        final StackPane pane = new StackPane(label);
        pane.getStyleClass().addAll("pane-content-manual", "pane-content-manual-how-to-use");

        final ScrollPane scroll = new ScrollPane(pane);
        scroll.getStyleClass().add("scroll-content-manual");

        final TitledPane titledPane = new TitledPane(LanguageManager.get("How.To.Use"), scroll);
        titledPane.getStyleClass().add("pane-section-manual");
        return titledPane;
    }

    private TitledPane configureSectionInformation() {
        final Label label = new Label(LanguageManager.get("pane.information-content"));
        label.getStyleClass().add("label-content-manual");

        final StackPane pane = new StackPane(label);
        pane.getStyleClass().addAll("pane-content-manual", "pane-content-manual-information");

        final ScrollPane scroll = new ScrollPane(pane);
        scroll.getStyleClass().add("scroll-content-manual");

        final TitledPane titledPane = new TitledPane(LanguageManager.get("Information"), scroll);
        titledPane.getStyleClass().add("pane-section-manual");
        return titledPane;
    }

    private TitledPane configureSectionLanguage() {
        final Label label = new Label(LanguageManager.get("pane.language.content"));
        label.getStyleClass().add("label-content-manual");

        final StackPane pane = new StackPane(label);
        pane.getStyleClass().addAll("pane-content-manual", "pane-content-manual-extras.language");

        final ScrollPane scroll = new ScrollPane(pane);
        scroll.getStyleClass().add("scroll-content-manual");

        final TitledPane titledPane = new TitledPane(LanguageManager.get("Language"), scroll);
        titledPane.getStyleClass().add("pane-section-manual");
        return titledPane;
    }

    private TitledPane configureSectionOperation() {
        final Label label1 = new Label(LanguageManager.get("pane.operation.content.1"));
        label1.getStyleClass().add("label-content-manual");

        final Label label2 = new Label(LanguageManager.get("pane.operation.content.2"));
        label2.getStyleClass().add("label-content-manual");

        final Label label3 = new Label(LanguageManager.get("pane.operation.content.3"));
        label3.getStyleClass().add("label-content-manual");

        final Label label4 = new Label(LanguageManager.get("pane.operation.content.4"));
        label4.getStyleClass().add("label-content-manual");

        final VBox pane = new VBox(label1, label2, label3, label4);
        pane.getStyleClass().addAll("pane-content-manual", "pane-content-manual-operation");

        final ScrollPane scroll = new ScrollPane(pane);
        scroll.getStyleClass().add("scroll-content-manual");

        final TitledPane titledPane = new TitledPane(LanguageManager.get("Operation"), scroll);
        titledPane.getStyleClass().add("pane-section-manual");
        return titledPane;
    }

    private TitledPane configureSectionSupportedEncryptions() {
        final Label label1 = new Label("MD5");
        label1.getStyleClass().add("label-content-manual");

        final Label label2 = new Label("SHA-1");
        label2.getStyleClass().add("label-content-manual");

        final Label label3 = new Label("SHA-224");
        label3.getStyleClass().add("label-content-manual");

        final Label label4 = new Label("SHA-256");
        label4.getStyleClass().add("label-content-manual");

        final Label label5 = new Label("SHA-384");
        label5.getStyleClass().add("label-content-manual");

        final Label label6 = new Label("SHA-512");
        label6.getStyleClass().add("label-content-manual");

        final GridPane pane = new GridPane();
        pane.getStyleClass().addAll("pane-content-manual", "pane-content-manual-encryption");
        pane.add(label1, 0, 0);
        pane.add(label2, 1, 0);
        pane.add(label3, 2, 0);
        pane.add(label4, 0, 1);
        pane.add(label5, 1, 1);
        pane.add(label6, 2, 1);

        final ScrollPane scroll = new ScrollPane(pane);
        scroll.getStyleClass().add("scroll-content-manual");

        final TitledPane titledPane = new TitledPane(LanguageManager.get("Supported.Encryptions"), scroll);
        titledPane.getStyleClass().add("pane-section-manual");
        return titledPane;
    }

    private List<Label> configureSectionsIndex() {
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

    private List<TitledPane> configureSectionsManual() {
        final List<TitledPane> list = new ArrayList<>();
        list.add(configureSectionInformation());
        list.add(configureSectionHowToUse());
        list.add(configureSectionOperation());
        list.add(configureSectionSupportedEncryptions());
        list.add(configureSectionLanguage());
        return list;
    }

    private void configureStage() {
        final Stage stage = new Stage();
        stage.setMinWidth(781);
        stage.setMinHeight(368);

        stage.setTitle(LanguageManager.get("Hash.Checker.Manual"));
        stage.setScene(configureScene());
        stage.show();
    }
}