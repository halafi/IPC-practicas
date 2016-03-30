package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.exit(0);
    }

}
