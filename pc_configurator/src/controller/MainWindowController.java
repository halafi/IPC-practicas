package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import model.Pc;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class MainWindowController implements Initializable {

    private Stage primaryStage;

    public void initStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onBuildPCFromScratch(ActionEvent event) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/configurationWindow.fxml"));
            Parent root = (Parent) myLoader.load();
            ConfigurationWindowController configurationWindowController = myLoader.<ConfigurationWindowController>getController();
            configurationWindowController.initStage(primaryStage, new Pc());

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBuildPCFromTemplate(ActionEvent event) {
    }

    @FXML
    private void onLoadPCFromFile(ActionEvent event) {
        Pc loadedPc = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load file");

            File loadedFile = fileChooser.showOpenDialog(null);
            if (loadedFile != null) {
                JAXBContext jaxbContext = JAXBContext.newInstance(Pc.class);
                Unmarshaller um = jaxbContext.createUnmarshaller();
                loadedPc = (Pc) um.unmarshal(loadedFile);
            }
        } catch (JAXBException e) {
            Alert alert = new Alert(AlertType.ERROR, "Error reading file");
            alert.showAndWait();
            e.printStackTrace();
        }
        if (loadedPc != null) {
            try {
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/configurationWindow.fxml"));
                Parent root = (Parent) myLoader.load();

                ConfigurationWindowController configurationWindowController = myLoader.<ConfigurationWindowController>getController();
                configurationWindowController.initStage(primaryStage, loadedPc);

                Scene scene = new Scene(root);

                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.exit(0);
    }

}
