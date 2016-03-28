package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class ConfigurationWindowController implements Initializable {

    private Stage primaryStage;
    private Scene previousScene;
    private String previousSceneTitle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initStage(Stage stage) {
        primaryStage = stage;
        previousScene = stage.getScene();
        previousSceneTitle = stage.getTitle();
        primaryStage.setTitle("TechDog PC Configurator");
    }

    /*@FXML
    private void closeAction(ActionEvent event) {
        primaryStage.setTitle(previousSceneTitle);
        primaryStage.setScene(previousScene);
    }*/

}
