package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        }
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
