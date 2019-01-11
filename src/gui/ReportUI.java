package gui;

import core.model.Algorithm;
import core.model.Report;
import extras.language.LanguageManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ReportUI extends Application {
    private Map<Integer, String> officialHashes;
    private Map<Integer, String> calculatedHashes;
    private Map<Integer, Boolean> results;

    private Stage stage;
    private Stage parent;

    public ReportUI(Map<Integer, String> officialHashes, Map<Integer, String> calculatedHashes, Map<Integer, Boolean> results, Stage parent) {
        this.officialHashes = officialHashes;
        this.calculatedHashes = calculatedHashes;
        this.results = results;
        this.parent = parent;
        stage = configureStage();
    }

    @Override
    public void start(Stage stage) {
    }

    private Map<String, Object> calculateIntegrity() {
        float good = 0;
        for (boolean value : results.values())
            if (value) ++good;

        final String id;
        final double percentil = good / results.size();
        if (percentil == 1) id = "100";
        else if (percentil >= .75) id = "75";
        else if (percentil >= .5) id = "50";
        else if (percentil >= .25) id = "25";
        else id = "0";

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("percentil", percentil);
        return map;
    }

    private TableColumn<Report, String> configureColumnAlgorithm() {
        final TableColumn<Report, String> column = new TableColumn<>(LanguageManager.get("Algorithm"));
        column.getStyleClass().add("column-algorithm");
        column.setMinWidth(100);
        column.setMaxWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<>("algorithm"));
        return column;
    }

    private TableColumn<Report, String> configureColumnCalculatedHash() {
        final TableColumn<Report, String> column = new TableColumn<>(LanguageManager.get("Calculated.Hash"));
        column.getStyleClass().add("column-calculated-hash");
        column.setSortable(false);
        column.setMinWidth(150);
        column.setCellValueFactory(new PropertyValueFactory<>("calculatedHash"));
        return column;
    }

    private TableColumn<Report, String> configureColumnOfficialHash() {
        final TableColumn<Report, String> column = new TableColumn<>(LanguageManager.get("Official.Hash"));
        column.getStyleClass().add("column-official-hash");
        column.setSortable(false);
        column.setMinWidth(150);
        column.setCellValueFactory(new PropertyValueFactory<>("officialHash"));
        return column;
    }

    private TableColumn<Report, String> configureColumnStatus() {
        final TableColumn<Report, String> column = new TableColumn<>(LanguageManager.get("Status"));
        column.getStyleClass().add("column-status");
        column.setMinWidth(75);
        column.setMaxWidth(75);
        column.setCellValueFactory(new PropertyValueFactory<>("status"));
        return column;
    }

    private HBox configurePaneIntegrity() {
        Map<String, Object> map = calculateIntegrity();

        Label label = new Label(new DecimalFormat("#.##").format((double) (map.get("percentil")) * 100) + " %");
        label.getStyleClass().add("label-integrity");

        ProgressBar bar = new ProgressBar((Double) map.get("percentil"));
        bar.getStyleClass().add("bar-integrity");
        bar.setId((String) map.get("id"));
        bar.prefWidthProperty().bind(stage.widthProperty());

        HBox pane = new HBox(label, bar);
        pane.getStyleClass().add("pane-integrity");
        return pane;
    }

    private VBox configurePaneRoot() {
        VBox pane = new VBox(configureTableReport(), configurePaneIntegrity());
        pane.getStyleClass().add("pane-root");
        return pane;
    }

    private Scene configureScene() {
        final Scene scene = new Scene(configurePaneRoot());
        scene.getStylesheets().addAll("/gui/css/reportui/Column.css",
                "/gui/css/reportui/Label.css",
                "/gui/css/reportui/Pane.css",
                "/gui/css/reportui/ProgressBar.css",
                "/gui/css/reportui/Scroll.css",
                "/gui/css/reportui/Table.css");
        return scene;
    }

    private Stage configureStage() {
        stage = new Stage();

        stage.setMinWidth(600);
        stage.setMinHeight(282);
        stage.setMaxHeight(282);

        stage.setTitle(LanguageManager.get("Hash.Checker.Report"));
        stage.setScene(configureScene());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parent);
        stage.show();
        return stage;
    }

    private TableView<Report> configureTableReport() {
        final TableView<Report> table = new TableView<>(fillTable());
        table.getStyleClass().add("table-report");
        table.setRowFactory(param -> new TableRow<>() {
            protected void updateItem(Report item, boolean empty) {
                if (!empty && item != null) {
                    if (item.getStatus().equals(LanguageManager.get("Risk"))) setId("risk");
                    else setId("secure");
                }
            }
        });

        table.getColumns().add(configureColumnAlgorithm());
        table.getColumns().add(configureColumnOfficialHash());
        table.getColumns().add(configureColumnCalculatedHash());
        table.getColumns().add(configureColumnStatus());

        table.getSortOrder().add(table.getColumns().get(0));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    private ObservableList<Report> fillTable() {
        final ObservableList<Report> list = FXCollections.observableArrayList();

        for (int key : officialHashes.keySet()) {
            final String algorithm = Algorithm.getByLength(key).getAlgorithm();
            final String official = officialHashes.get(key);
            final String calculated = calculatedHashes.get(key);
            final String status = results.get(key)
                    ? LanguageManager.get("Secure")
                    : LanguageManager.get("Risk");

            list.add(new Report(algorithm, official, calculated, status));
        }

        return list;
    }

    protected Stage getStage() {
        return stage;
    }
}