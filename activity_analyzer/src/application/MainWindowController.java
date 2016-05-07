package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
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
    private AreaChart<Number, Number> heightDistanceAreaChart;
    @FXML
    private NumberAxis elevationYAxis;
    @FXML
    private NumberAxis elevationXAxis;
    @FXML
    private LineChart<Number, Number> hrDistanceLineChart;
    @FXML
    private NumberAxis hrYAxis;
    @FXML
    private NumberAxis hrXAxis;
    @FXML
    private LineChart<Number, Number> cadDistanceLineChart;
    @FXML
    private NumberAxis cadYAxis;
    @FXML
    private NumberAxis cadXAxis;
    @FXML
    private LineChart<Number, Number> speedDistanceLineChart;
    @FXML
    private NumberAxis speedYAxis;
    @FXML
    private NumberAxis speedXAxis;
    @FXML
    private PieChart zonesPieChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        elevationXAxis.setLabel("Distance [Km]");
        hrXAxis.setLabel("Distance [Km]");
        cadXAxis.setLabel("Distance [Km]");
        speedXAxis.setLabel("Distance [Km]");
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
    }

    @FXML
    private void loadGPXFile(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
        if (file != null) {
            loadButton.setDisable(true);
            statusLabel.getScene().setCursor(Cursor.WAIT);
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
        }
    }
    
    private void updateCharts() {
        XYChart.Series<Number, Number> heightDistance = new XYChart.Series();
        XYChart.Series<Number, Number> hrDistance = new XYChart.Series();
        XYChart.Series<Number, Number> cadenceDistance = new XYChart.Series();
        XYChart.Series<Number, Number> speedDistance = new XYChart.Series();
        ObservableList<Chunk> chunks = trackData.getChunks();
        Boolean add = true;
        Double distance = 0.0;
        Double elevation = 0.0;
        for (Chunk c: chunks) {
            distance = distance + c.getDistance();
            elevation = elevation + c.getAscent() - c.getDescend();
            if (elevation < 0) {
                elevation = 0.0;
            }
            heightDistance.getData().add(new XYChart.Data<>(distance/1000, elevation));
            hrDistance.getData().add(new XYChart.Data<>(distance/1000, c.getAvgHeartRate()));
            cadenceDistance.getData().add(new XYChart.Data<>(distance/1000, c.getAvgCadence()));
            speedDistance.getData().add(new XYChart.Data<>(distance/1000, c.getSpeed() * 3.6));
        };
        heightDistanceAreaChart.getData().clear();
        hrDistanceLineChart.getData().clear();
        cadDistanceLineChart.getData().clear();
        speedDistanceLineChart.getData().clear();
        heightDistanceAreaChart.getData().add(heightDistance);
        hrDistanceLineChart.getData().add(hrDistance);
        cadDistanceLineChart.getData().add(cadenceDistance);
        speedDistanceLineChart.getData().add(speedDistance);
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
        textInfo.appendText(String.format("\nAverage Cadence: %d", trackData.getAverageCadence()));
        textInfo.appendText(String.format("\nMaximum Cadence: %d", trackData.getMaxCadence()));
        /*ObservableList<Chunk> chunks = trackData.getChunks();
        textInfo.appendText("\nTrack containing " + chunks.size() + " points");*/
    }

}
