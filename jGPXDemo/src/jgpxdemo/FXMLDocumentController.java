package jgpxdemo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import jgpx.util.FileIO;
import jgpx.util.JAXBFactory;

/**
 *
 * @author mario
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextArea text;
    final FileChooser fileChooser = new FileChooser();
    @FXML
    private Label label;
    @FXML
    private Button loadButton;
    private TrackData trackData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void load(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
        if (file == null) {
            return;
        }
        label.setText("Loading " + file.getName());
        JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class, TrackPointExtensionT.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Object> root = (JAXBElement<Object>) unmarshaller.unmarshal(file);
        GpxType gpx = (GpxType) root.getValue();

        if (gpx != null) {
            trackData = new TrackData(new Track(gpx.getTrk().get(0)));
            showTrackInfo(trackData);
            label.setText("GPX successfully loaded");
        } else {
            label.setText("Error loading GPX from " + file.getName());
        }
    }

    private void showTrackInfo(TrackData trackData) {
        text.setText("Start time: " + DateTimeUtils.format(trackData.getStartTime()));
        text.appendText("\nEnd time: " + DateTimeUtils.format(trackData.getEndTime()));
        text.appendText(String.format("\nTotal Distance: %.0f m",trackData.getTotalDistance()));
        text.appendText("\nDuration: " + DateTimeUtils.format(trackData.getTotalDuration()));
        text.appendText("\nMoving time: " + DateTimeUtils.format(trackData.getMovingTime()));
        text.appendText(String.format("\nAverage Speed: %.2f m/s", trackData.getAverageSpeed()));
        text.appendText(String.format("\nAverage Cadence: %d", trackData.getAverageCadence()));
        text.appendText(String.format("\nAverage Heartrate: %d bpm", trackData.getAverageHeartrate()));
        text.appendText(String.format("\nTotal ascent: %.2f m", trackData.getTotalAscent()));
        text.appendText(String.format("\nTotal descend: %.2f m", trackData.getTotalDescend()));
        ObservableList<Chunk> chunks = trackData.getChunks();
        text.appendText("\nTrack containing " + chunks.size() + " points");
    }

}
