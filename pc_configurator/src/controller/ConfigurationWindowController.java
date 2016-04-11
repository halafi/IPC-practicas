package controller;

import es.upv.inf.Product;
import es.upv.inf.Product.Category;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Component;
import model.Pc;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class ConfigurationWindowController implements Initializable {

    private Stage primaryStage;
    private Pc pc;

    @FXML
    private TableView<Component> componentTable;
    @FXML
    private TableColumn<Component, Product> descriptionColumn;
    @FXML
    private TableColumn<Component, Product> stockColumn;
    @FXML
    private TableColumn<Component, Product> categoryColumn;
    @FXML
    private TableColumn<Component, Integer> amountColumn;
    @FXML
    private TableColumn<Component, Product> priceColumn;

    @FXML
    private TextField pcNameField;

    @FXML
    private Button saveButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button removeComponentButton;
    @FXML
    private Button editComponentButton;
    @FXML
    private Button addComponentButton;
    @FXML
    private Button loadButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        descriptionColumn.textProperty().set("Description");
        stockColumn.textProperty().set("Stock");
        categoryColumn.textProperty().set("Category");
        amountColumn.textProperty().set("Quantity");
        priceColumn.textProperty().set("Unit Price");

        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getProductProperty());
        descriptionColumn.setCellFactory(v -> {
            return new TableCell<Component, Product>() {
                @Override
                protected void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getDescription());
                    }
                }
            };
        });
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getProductProperty());
        categoryColumn.setCellFactory(v -> {
            return new TableCell<Component, Product>() {
                @Override
                protected void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getCategory().name());
                    }
                }
            };
        });
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().getQuantityProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getProductProperty());
        priceColumn.setCellFactory(v -> {
            return new TableCell<Component, Product>() {
                @Override
                protected void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item.getPrice()));
                    }
                }
            };
        });
        stockColumn.setCellValueFactory(cellData -> cellData.getValue().getProductProperty());
        stockColumn.setCellFactory(v -> {
            return new TableCell<Component, Product>() {
                @Override
                protected void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item.getStock()));
                    }
                }
            };
        });

        removeComponentButton.disableProperty().bind(
                Bindings.equal(-1, componentTable.getSelectionModel().selectedIndexProperty())
        );

        editComponentButton.disableProperty().bind(
                Bindings.equal(-1, componentTable.getSelectionModel().selectedIndexProperty())
        );

        pcNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                pc.setName(newValue);
            }
        });
    }

    public void initStage(Stage stage, Pc pc) {
        this.pc = pc;

        this.componentTable.setItems(pc.getComponents());
        this.primaryStage = stage;
        this.primaryStage.setTitle("TechDog PC Configurator");
        if (pc.getName() == null) {
            this.pc.setName("");
        }
        this.pcNameField.setText(pc.getName());
    }

    @FXML
    private void odAddComponent(ActionEvent event) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/searchComponents.fxml"));
            Parent root = (Parent) myLoader.load();
            SearchComponentsController searchController = myLoader.<SearchComponentsController>getController();
            Stage modalStage = new Stage();
            searchController.initStage(modalStage, pc);

            Scene scene = new Scene(root);
            modalStage.setScene(scene);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSave(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            if (!pc.getName().equals("")) {
                fileChooser.setInitialFileName(pc.getName() + ".xml");
            } else {
                fileChooser.setInitialFileName("noname_pc.xml");
            }
            File savedFile = fileChooser.showSaveDialog(primaryStage);
            if (savedFile != null) {
                JAXBContext jaxbContext = JAXBContext.newInstance(Pc.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.marshal(pc, savedFile); // save to a file
                //jaxbMarshaller.marshal(pc, System.out); // echo to the console
            }
        } catch (JAXBException e) {
            Alert alert = new Alert(AlertType.ERROR, "Error saving file");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void onLoad(ActionEvent event) {
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
            pc = loadedPc;
            componentTable.setItems(pc.getComponents());
            pcNameField.setText(pc.getName());
        }
    }

    @FXML
    private void onContinue(ActionEvent event) {
        boolean hasMB = false, hasCPU = false, hasRAM = false, hasGPU = false, hasStorage = false, hasCase = false;
        for (Component c : pc.getComponents()) {
            Category cat = c.getProductProperty().get().getCategory();
            switch (cat) {
                case CASE:
                    hasCase = true;
                    break;
                case MOTHERBOARD:
                    hasMB = true;
                    break;
                case CPU:
                    hasCPU = true;
                    break;
                case GPU:
                    hasGPU = true;
                    break;
                case HDD:
                    hasStorage = true;
                    break;
                case HDD_SSD:
                    hasStorage = true;
                    break;
                case RAM:
                    hasRAM = true;
                    break;
            }
        }
        if (!hasMB || !hasCPU || !hasCase || !hasGPU || !hasRAM || !hasStorage) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Missing one or more crucial compoment(s)");
            alert.setContentText("PC needs at least one:\n* Motherboard\n* Case\n* CPU\n* RAM\n* GPU\n* HDD or SDD");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/finalConfigurationWindow.fxml"));
                Parent root = (Parent) myLoader.load();
                FinalConfigurationWindowController confController = myLoader.<FinalConfigurationWindowController>getController();
                confController.initStage(primaryStage, pc);

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        node.getScene().getWindow().hide();
    }

    @FXML
    private void onRemoveComponent(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to remove this component?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            pc.getComponents().remove(componentTable.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    private void onEditComponent(ActionEvent event) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/editComponentWindow.fxml"));
            Parent root = (Parent) myLoader.load();
            EditComponentWindowController editComponentController = myLoader.<EditComponentWindowController>getController();
            Stage modalStage = new Stage();
            editComponentController.initStage(modalStage, pc.getComponents().get(componentTable.getSelectionModel().getSelectedIndex()), componentTable.getColumns().get(0));

            Scene scene = new Scene(root);
            modalStage.setScene(scene);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
