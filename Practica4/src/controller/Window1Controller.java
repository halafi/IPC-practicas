package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Person;
import model.Residence;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class Window1Controller implements Initializable {

    private Stage primaryStage;

    private Integer indexOfModified;

    private ObservableList<Person> data;
    @FXML
    private TextField dniText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField ciudadText;
    @FXML
    private TextField imagenText;
    @FXML
    private TextField provinceText;
    @FXML
    private Button saveButton;

    public void initStage(Stage stage, Person person, ObservableList<Person> data, Integer indexOfModified, boolean viewOnly) {
        primaryStage = stage;
        primaryStage.setTitle("Add Person");
        this.data = data;
        this.indexOfModified = indexOfModified;
        if (person != null) {
            primaryStage.setTitle("Edit "+person.getFullName());
            dniText.setText(person.getId().toString());
            nameText.setText(person.getFullName());
            imagenText.setText(person.getPathImagen().get());
            ciudadText.setText(person.getResidence().getCity());
            provinceText.setText(person.getResidence().getProvince());
        }
        if (viewOnly) {
            primaryStage.setTitle("View Person");
            dniText.setDisable(true);
            nameText.setDisable(true);
            ciudadText.setDisable(true);
            imagenText.setDisable(true);
            provinceText.setDisable(true);
            saveButton.setDisable(true);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    @FXML
    private void onSave(MouseEvent event) {
        Person person = new Person(nameText.getText(), Integer.parseInt(dniText.getText()), new Residence(ciudadText.getText(), provinceText.getText()), imagenText.getText());
        // add or update
        if (this.indexOfModified != null) {
            data.set(indexOfModified, person);
        } else {
            data.add(person);
        }
        onCancel(event);

    }

    @FXML
    private void onCancel(MouseEvent event) {
        Node myNode = (Node) event.getSource();
        myNode.getScene().getWindow().hide();
    }
}
