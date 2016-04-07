package application;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author filip
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Get the default locale
        Locale locale = Locale.getDefault();
        // Load the bundle (e.g., strings_es_ES.properties)
        ResourceBundle bundle = ResourceBundle.getBundle("resources.strings", locale);

        // Provide the bundle to the FXMLLoader
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"), bundle);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
