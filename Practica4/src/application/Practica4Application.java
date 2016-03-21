package application;

import controller.MainController;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Person;
import model.Residence;

/**
 *
 * @author fjabad
 */
public class Practica4Application extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        Parent root = (Parent) loader.load();
        
        ArrayList<Person> backupList = new ArrayList<>();
        backupList.add(new Person("John Doe", 456, new Residence("Valencia", "Valencia"), "/resources/Sonriente.png"));
        backupList.add(new Person("Jane Doe", 123, new Residence("Valencia", "Valencia"), "/resources/LLoroso.png"));
        backupList.add(new Person("Juan Gómez", 45678912, new Residence("Valencia", "Valencia"), "/resources/Pregunta.png"));
        ObservableList<Person> data = FXCollections.observableArrayList(backupList);
        
        Scene scene = new Scene(root);
        
        loader.<MainController>getController().initStage(data);
        
        stage.setTitle("Practica4");
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
