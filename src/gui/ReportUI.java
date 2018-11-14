package gui;

import core.Algorithm;
import core.Report;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import language.LanguageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportUI extends Application {
    private final Map<Integer, String> officialHashes;
    private final Map<Integer, String> calculatedHashes;
    private final Map<Integer, Boolean> results;

    private Stage stage;

    private ScrollPane paneRoot;

    private StackPane paneBackground;

    private TableView<Report> tableReport;

    public ReportUI(Map<Integer, String> officialHashes, Map<Integer, String> calculatedHashes, Map<Integer, Boolean> results) {
        this.officialHashes = officialHashes;
        this.calculatedHashes = calculatedHashes;
        this.results = results;

        start(new Stage());
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        initComponents();
        configurePaneBackground();
        configurePaneRoot();
        configureTabelReport();

        configureStage();
    }

    private void initComponents() {
        paneRoot = new ScrollPane();
        paneBackground = new StackPane();

        tableReport = new TableView<>(fillTable());
    }

    private void configureStage() {
        stage.setMinWidth(600);

        /* Lock vertical resize */
        stage.setMinHeight(232);
        stage.setMaxHeight(232);

        stage.setTitle(LanguageManager.get().getString("Hash.Checker") + " " + LanguageManager.get().getString("Report"));
        stage.setScene(new Scene(paneRoot));
        stage.show();
    }

    private void configurePaneBackground() {
        paneBackground.setAlignment(Pos.CENTER);
        paneBackground.setPadding(new Insets(15));
        paneBackground.getChildren().add(tableReport);
    }

    private void configurePaneRoot() {
        paneRoot.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        paneRoot.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        paneRoot.setFitToWidth(true);
        paneRoot.setFitToHeight(true);
        paneRoot.setContent(paneBackground);
    }

    private void configureTabelReport() {
        final TableColumn<Report, String> columnAlgorithm = new TableColumn<>(LanguageManager.get().getString("Algorithm"));
        columnAlgorithm.setCellValueFactory(new PropertyValueFactory<>("algorithm"));
        columnAlgorithm.setMinWidth(100);
        columnAlgorithm.setMaxWidth(100);

        final TableColumn<Report, String> columnOfficialHash = new TableColumn<>(LanguageManager.get().getString("Official.Hash"));
        columnOfficialHash.setCellValueFactory(new PropertyValueFactory<>("officialHash"));
        columnOfficialHash.setMinWidth(150);
        columnOfficialHash.setSortable(false);

        final TableColumn<Report, String> columnCalculatedHash = new TableColumn<>(LanguageManager.get().getString("Calculated.Hash"));
        columnCalculatedHash.setCellValueFactory(new PropertyValueFactory<>("calculatedHash"));
        columnCalculatedHash.setMinWidth(150);
        columnCalculatedHash.setSortable(false);

        final TableColumn<Report, String> columnStatus = new TableColumn<>(LanguageManager.get().getString("Status"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnStatus.setMinWidth(75);
        columnStatus.setMaxWidth(75);
        columnStatus.setStyle("-fx-alignment: center;");

        tableReport.getColumns().clear();
        tableReport.getColumns().addAll(columnAlgorithm, columnOfficialHash, columnCalculatedHash, columnStatus);
        tableReport.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableReport.getSortOrder().add(columnAlgorithm);
    }

    private ObservableList<Report> fillTable() {
        final List<Report> list = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : officialHashes.entrySet()) {
            final String algorithm = Algorithm.getByLength(entry.getKey()).getAlgorithm();
            final String official = entry.getValue();
            final String calculated = calculatedHashes.get(entry.getKey());
            final String status = results.get(entry.getKey())
                    ? LanguageManager.get().getString("Secure")
                    : LanguageManager.get().getString("Risk");

            list.add(new Report(algorithm, official, calculated, status));
        }

        return FXCollections.observableList(list);
    }

    protected Stage getStage() {
        return stage;
    }
}