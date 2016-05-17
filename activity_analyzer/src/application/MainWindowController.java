package application;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import jgpx.model.analysis.Chunk;
import jgpx.model.analysis.TrackData;
import jgpx.model.gpx.Track;
import jgpx.model.jaxb.GpxType;
import jgpx.model.jaxb.TrackPointExtensionT;
import jgpx.util.DateTimeUtils;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class MainWindowController implements Initializable {

    @FXML
    private Label statusLabel;
    private TrackData trackData;
    @FXML
    private Button loadButton;
    @FXML
    private TextArea textInfo;
    @FXML
    private AreaChart<String, Number> heightDistanceAreaChart;
    @FXML
    private NumberAxis elevationYAxis;
    @FXML
    private CategoryAxis elevationXAxis;
    @FXML
    private LineChart<String, Number> hrDistanceLineChart;
    @FXML
    private NumberAxis hrYAxis;
    @FXML
    private CategoryAxis hrXAxis;
    @FXML
    private LineChart<String, Number> cadDistanceLineChart;
    @FXML
    private NumberAxis cadYAxis;
    @FXML
    private CategoryAxis cadXAxis;
    @FXML
    private LineChart<String, Number> speedDistanceLineChart;
    @FXML
    private NumberAxis speedYAxis;
    @FXML
    private CategoryAxis speedXAxis;
    @FXML
    private PieChart zonesPieChart;
    @FXML
    private TabPane tabPane;
    @FXML
    private ChoiceBox<String> xAxisSelector;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        elevationYAxis.setLabel("Elevation [m.s.l.]");
        hrYAxis.setLabel("Heartrate [bpm]");
        cadYAxis.setLabel("Cadence [rpm]");
        speedYAxis.setLabel("Speed [Km/h]");
        heightDistanceAreaChart.setLegendVisible(false);
        heightDistanceAreaChart.setCreateSymbols(false);
        hrDistanceLineChart.setLegendVisible(false);
        hrDistanceLineChart.setCreateSymbols(false);
        cadDistanceLineChart.setLegendVisible(false);
        cadDistanceLineChart.setCreateSymbols(false);
        speedDistanceLineChart.setLegendVisible(false);
        speedDistanceLineChart.setCreateSymbols(false);
        zonesPieChart.setLegendVisible(false);
        xAxisSelector.setItems(FXCollections.observableArrayList("Distance [Km]", "Time [hh:mm:ss]"));
        xAxisSelector.getSelectionModel().selectFirst();
        xAxisSelector.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //System.out.println(newValue);
                updateCharts();
                /*elevationXAxis.setLabel(newValue);
                 hrXAxis.setLabel(newValue);
                 cadXAxis.setLabel(newValue);
                 speedXAxis.setLabel(newValue);*/

            }
        });
        xAxisSelector.setVisible(false);
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {

                        if (newTab.getId() != null) {
                            String id = newTab.getId();
                            if (id.equals("overviewTab") || id.equals("zonesTab")) {
                                xAxisSelector.setVisible(false);
                            }
                        } else {
                            xAxisSelector.setVisible(true);
                        }

                    }
                }
        );
    }

    @FXML
    private void loadGPXFile(ActionEvent event) throws JAXBException {
        // WITHOUT TASK
        /*FileChooser fileChooser = new FileChooser();
         File file = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
         if (file != null) {
         statusLabel.getScene().setCursor(Cursor.WAIT);
         tabPane.setDisable(true);
         loadButton.setDisable(true);
         statusLabel.setText("Loading " + file.getName());
         JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class, TrackPointExtensionT.class);
         Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
         JAXBElement<Object> root = (JAXBElement<Object>) unmarshaller.unmarshal(file);
         GpxType gpx = (GpxType) root.getValue();
         if (gpx != null) {
         trackData = new TrackData(new Track(gpx.getTrk().get(0)));
         showTrackInfo(trackData);
         } else {
         statusLabel.setText("Error loading GPX from " + file.getName());
         }
         statusLabel.setText("GPX successfully loaded");
         loadButton.setDisable(false);
         statusLabel.getScene().setCursor(Cursor.DEFAULT);
         updateCharts();
         tabPane.setDisable(false);
         }*/

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
        if (file != null) {
            statusLabel.getScene().setCursor(Cursor.WAIT);
            tabPane.setDisable(true);
            loadButton.setDisable(true);
            statusLabel.setText("Loading " + file.getName());
            Task<Integer> task = new Task<Integer>() {
                @Override
                protected Integer call() throws JAXBException {
                    JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class, TrackPointExtensionT.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                    JAXBElement<Object> root = (JAXBElement<Object>) unmarshaller.unmarshal(file);
                    GpxType gpx = (GpxType) root.getValue();
                    if (gpx != null) {
                        trackData = new TrackData(new Track(gpx.getTrk().get(0)));
                        return 0;
                    }
                    return -1;
                }
            };
            task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                    new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            if (task.getValue() == -1) {
                                statusLabel.setText("Error loading GPX from " + file.getName());
                            }
                            statusLabel.setText("GPX successfully loaded");
                            showTrackInfo(trackData);
                            updateCharts();
                            loadButton.setDisable(false);
                            statusLabel.getScene().setCursor(Cursor.DEFAULT);
                            tabPane.setDisable(false);
                        }
                    });
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
        }

    }

    private void updateCharts() {
        XYChart.Series<String, Number> heightDistance = new XYChart.Series();
        XYChart.Series<String, Number> hrDistance = new XYChart.Series();
        XYChart.Series<String, Number> cadenceDistance = new XYChart.Series();
        XYChart.Series<String, Number> speedDistance = new XYChart.Series();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        int maxHeartrate = trackData.getMaxHeartrate();
        System.out.println(String.format("%f %f %f %f %f", maxHeartrate * 0.6, maxHeartrate * 0.6, maxHeartrate * 0.7, maxHeartrate * 0.8, maxHeartrate * 0.9));
        Double z1peak = maxHeartrate * 0.6;
        Double z2peak = maxHeartrate * 0.7;
        Double z3peak = maxHeartrate * 0.8;
        Double z4peak = maxHeartrate * 0.9;
        Double z5peak = maxHeartrate * 1.0;
        Duration z1 = Duration.ZERO;
        Duration z2 = Duration.ZERO;
        Duration z3 = Duration.ZERO;
        Duration z4 = Duration.ZERO;
        Duration z5 = Duration.ZERO;
        ObservableList<Chunk> chunks = trackData.getChunks();
        Boolean add = true;
        Double distance = 0.0;
        Double elevation = 0.0;
        int i = 0;
        int step = chunks.size() / 100;
        for (Chunk c : chunks) {
            elevation = elevation + c.getAscent() - c.getDescend();
            if (elevation < 0) {
                elevation = 0.0;
            }
            double avgHeartRate = c.getAvgHeartRate();
            if (avgHeartRate < z1peak) {
                z1 = z1.plus(c.getMovingTime());
            } else if (avgHeartRate < z2peak && avgHeartRate >= z1peak) {
                z2 = z2.plus(c.getMovingTime());
            } else if (avgHeartRate < z3peak && avgHeartRate >= z2peak) {
                z3 = z3.plus(c.getMovingTime());
            } else if (avgHeartRate < z4peak && avgHeartRate >= z3peak) {
                z4 = z4.plus(c.getMovingTime());
            } else if (avgHeartRate >= z4peak) {
                z5 = z5.plus(c.getMovingTime());
            }
            if (xAxisSelector.getValue().equals("Distance [Km]")) {
                distance = distance + c.getDistance();
                System.out.println();
                if (i % step == 0) { // affects accuracy
                    String category = String.format("%.0f", distance / 1000);
                    heightDistance.getData().add(new XYChart.Data<>(category, elevation));
                    hrDistance.getData().add(new XYChart.Data<>(category, c.getAvgHeartRate()));
                    cadenceDistance.getData().add(new XYChart.Data<>(category, c.getAvgCadence()));
                    speedDistance.getData().add(new XYChart.Data<>(category, c.getSpeed() * 3.6));
                }

            } else if (xAxisSelector.getValue().equals("Time [hh:mm:ss]")) {
                LocalDateTime time = c.getFirstPoint().getTime();
                if (i % step == 0) { // affects accuracy
                    String category = String.format(time.format(DateTimeFormatter.ofPattern("HH:mm")));
                    heightDistance.getData().add(new XYChart.Data<>(category, elevation));
                    hrDistance.getData().add(new XYChart.Data<>(category, c.getAvgHeartRate()));
                    cadenceDistance.getData().add(new XYChart.Data<>(category, c.getAvgCadence()));
                    speedDistance.getData().add(new XYChart.Data<>(category, c.getSpeed() * 3.6));
                }
            }
            i++;
        };
        pieChartData.add(new PieChart.Data("Z1 Recovery", z1.getSeconds()));
        pieChartData.add(new PieChart.Data("Z2 Endurance", z2.getSeconds()));
        pieChartData.add(new PieChart.Data("Z3 Tempo", z3.getSeconds()));
        pieChartData.add(new PieChart.Data("Z4 Threshold", z4.getSeconds()));
        pieChartData.add(new PieChart.Data("Z5 Anaerobic", z5.getSeconds()));
        heightDistanceAreaChart.getData().clear();
        hrDistanceLineChart.getData().clear();
        cadDistanceLineChart.getData().clear();
        speedDistanceLineChart.getData().clear();
        heightDistanceAreaChart.getData().add(heightDistance);
        hrDistanceLineChart.getData().add(hrDistance);
        cadDistanceLineChart.getData().add(cadenceDistance);
        speedDistanceLineChart.getData().add(speedDistance);
        zonesPieChart.setData(pieChartData);
    }

    private void showTrackInfo(TrackData trackData) {
        textInfo.setText("Start time: " + DateTimeUtils.format(trackData.getStartTime()));
        //textInfo.appendText("\nEnd time: " + DateTimeUtils.format(trackData.getEndTime()));
        textInfo.appendText("\nDuration: " + DateTimeUtils.format(trackData.getTotalDuration()));
        textInfo.appendText("\nMoving time: " + DateTimeUtils.format(trackData.getMovingTime()));
        textInfo.appendText(String.format("\nTotal Distance: %.2f Km", trackData.getTotalDistance() / 1000));
        textInfo.appendText(String.format("\nTotal ascent: %.2f m", trackData.getTotalAscent()));
        textInfo.appendText(String.format("\nTotal descend: %.2f m", trackData.getTotalDescend()));
        textInfo.appendText(String.format("\nAverage Speed: %.2f Km/h", trackData.getAverageSpeed() * 3.6));
        textInfo.appendText(String.format("\nMaximum Speed: %.2f Km/h", trackData.getMaxSpeed() * 3.6));
        textInfo.appendText(String.format("\nMinimum Heartrate: %d bpm", trackData.getMinHeartRate()));
        textInfo.appendText(String.format("\nAverage Heartrate: %d bpm", trackData.getAverageHeartrate()));
        textInfo.appendText(String.format("\nMaximum Heartrate: %d bpm", trackData.getMaxHeartrate()));
        textInfo.appendText(String.format("\nAverage Cadence: %d rpm", trackData.getAverageCadence()));
        textInfo.appendText(String.format("\nMaximum Cadence: %d rpm", trackData.getMaxCadence()));
        /*ObservableList<Chunk> chunks = trackData.getChunks();
         textInfo.appendText("\nTrack containing " + chunks.size() + " points");*/
    }

}
