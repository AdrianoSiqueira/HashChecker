package gui;

import core.Algorithm;
import core.Report;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import language.LanguageManager;

import java.util.Map;

public class ReportUI extends Application {
    private final Stage stage;

    private final ScrollPane scrollRoot;
    private final StackPane paneRoot;

    private final TableView<Report> tableReport;

    private final Map<Integer, String> officialHashes;
    private final Map<Integer, String> calculatedHashes;
    private final Map<Integer, Boolean> results;

    {
        tableReport = configureTableReport();
        paneRoot = configurePaneRoot();
        scrollRoot = configureScrollRoot();
        stage = configureStage();
    }

    public ReportUI(Map<Integer, String> officialHashes, Map<Integer, String> calculatedHashes, Map<Integer, Boolean> results) {
        this.officialHashes = officialHashes;
        this.calculatedHashes = calculatedHashes;
        this.results = results;
        fillTable(tableReport);
    }

    @Override
    public void start(Stage stage) {
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

    private StackPane configurePaneRoot() {
        final StackPane pane = new StackPane(tableReport);
        pane.getStyleClass().add("pane-root");
        return pane;
    }

    private Scene configureScene() {
        final Scene scene = new Scene(scrollRoot);
        scene.getRoot().getStylesheets().clear();
        scene.getRoot().getStylesheets().add("/gui/css/ReportUI.css");
        return scene;
    }

    private ScrollPane configureScrollRoot() {
        final ScrollPane scroll = new ScrollPane(paneRoot);
        scroll.getStyleClass().add("scroll-root");
        return scroll;
    }

    private Stage configureStage() {
        final Stage stage = new Stage();

        stage.setMinWidth(600);
        stage.setMinHeight(232);
        stage.setMaxHeight(232);

        stage.setTitle(LanguageManager.get("Hash.Checker.Report"));
        stage.setScene(configureScene());
        stage.show();
        return stage;
    }

    private TableView<Report> configureTableReport() {
        final TableView<Report> table = new TableView<>();
        table.getStyleClass().add("table-report");
        table.getColumns().add(configureColumnAlgorithm());
        table.getColumns().add(configureColumnOfficialHash());
        table.getColumns().add(configureColumnCalculatedHash());
        table.getColumns().add(configureColumnStatus());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSortOrder().add(table.getColumns().get(0));
        return table;
    }

    private void fillTable(final TableView<Report> table) {
        final ObservableList<Report> list = FXCollections.observableArrayList();

        for (Integer key : officialHashes.keySet()) {
            final String algorithm = Algorithm.getByLength(key).getAlgorithm();
            final String official = officialHashes.get(key);
            final String calculated = calculatedHashes.get(key);
            final String status = results.get(key)
                    ? LanguageManager.get("Secure")
                    : LanguageManager.get("Risk");

            list.add(new Report(algorithm, official, calculated, status));
        }

        table.setItems(list);
    }

    protected Stage getStage() {
        return stage;
    }
}