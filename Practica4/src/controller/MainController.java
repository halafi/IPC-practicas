package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;
import model.Residence;

// Local class in the controller
/*class PersonListCell extends ListCell<Person> {

 @Override
 protected void updateItem(Person item, boolean empty) {
 super.updateItem(item, empty); // This is mandatory
 if (item == null || empty) {
 setText(null);
 } else {
 setText(item.getFirstName() + " " + item.getLastName());
 }
 }
 }*/
/**
 *
 * @author fjabad
 */
public class MainController implements Initializable {

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    private TextField firstNameText;
    private TextField lastNameText;

    private ObservableList<Person> data = null;

    @FXML
    private Button buttonModify;

    @FXML
    private TableView<Person> personTable;

    @FXML
    private TableColumn<Person, Integer> dni;
    @FXML
    private TableColumn<Person, String> name;
    @FXML
    private TableColumn<Person, Residence> ciudad;
    @FXML
    private TableColumn<Person, String> imagen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initialize");
        name.textProperty().set("Name");
        dni.textProperty().set("DNI");
        ciudad.textProperty().set("Ciudad");
        imagen.textProperty().set("Imagen");
        name.setCellValueFactory(
                new PropertyValueFactory<>("fullName"));
        dni.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        imagen.setCellValueFactory(
                new PropertyValueFactory<>("pathImagen"));

        ciudad.setCellValueFactory(cellData3 -> cellData3.getValue().getResidenceProperty());
        ciudad.setCellFactory(v -> {
            return new TableCell<Person, Residence>() {
                @Override
                protected void updateItem(Residence item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText("-->" + item.getCity());
                    }
                }
            };
        });

        imagen.setCellValueFactory(celda4 -> celda4.getValue().getPathImagen());
        imagen.setCellFactory(columna -> {
            return new TableCell<Person, String>() {
                private ImageView view = new ImageView();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        Image image = new Image(MainController.class.getResourceAsStream(item), 40, 40, true, true);
                        view.setImage(image);
                        setGraphic(view);
                    }
                }
            };
        }); //setCellFactory

        

        buttonRemove.disableProperty().bind(
                Bindings.equal(-1,
                        personTable.getSelectionModel().selectedIndexProperty()));

        buttonModify.disableProperty().bind(
                Bindings.equal(-1,
                        personTable.getSelectionModel().selectedIndexProperty()));
    }

    public void initStage(ObservableList<Person> data) {
        this.data = data;
        personTable.setItems(data);
        System.out.println("init stage");
    }

    @FXML
    private void onAddItem(ActionEvent event) {
        try {
            Stage actualStage = new Stage();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/Window1.fxml"));
            Parent root = (Parent) myLoader.load();
            myLoader.<Window1Controller>getController().initStage(actualStage, null, data, null, false);
            Scene scene = new Scene(root);
            actualStage.setScene(scene);
            actualStage.initModality(Modality.APPLICATION_MODAL);
            actualStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRemoveItem(ActionEvent event) {
        data.remove(personTable.getSelectionModel().getSelectedIndex());
    }

    private void onLastNameEntered(ActionEvent event) {
        onAddItem(null);
    }

    @FXML
    private void onModifyItem(ActionEvent event) {
        try {
            Stage actualStage = new Stage();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/Window1.fxml"));
            Parent root = (Parent) myLoader.load();
            Person get = data.get(personTable.getSelectionModel().getSelectedIndex());
            myLoader.<Window1Controller>getController()
                    .initStage(actualStage, get, data, personTable.getSelectionModel().getSelectedIndex(), false);
            Scene scene = new Scene(root);
            actualStage.setScene(scene);
            actualStage.initModality(Modality.APPLICATION_MODAL);
            actualStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onViewItem(ActionEvent event) {
        try {
            Stage actualStage = new Stage();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/Window1.fxml"));
            Parent root = (Parent) myLoader.load();
            Person get = data.get(personTable.getSelectionModel().getSelectedIndex());
            myLoader.<Window1Controller>getController()
                    .initStage(actualStage, get, data, personTable.getSelectionModel().getSelectedIndex(), true);
            Scene scene = new Scene(root);
            actualStage.setScene(scene);
            actualStage.initModality(Modality.APPLICATION_MODAL);
            actualStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
