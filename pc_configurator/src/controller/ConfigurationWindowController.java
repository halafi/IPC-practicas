package controller;

import es.upv.inf.Product;
import es.upv.inf.Product.Category;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import model.Component;
import model.Pc;
import services.DatabaseService;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class ConfigurationWindowController implements Initializable {

    private Stage primaryStage;
    private Scene previousScene;
    private String previousSceneTitle;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        descriptionColumn.textProperty().set("Description");
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
    }

    public void initStage(Stage stage, Pc pc) {
        this.pc = pc;
        this.componentTable.setItems(pc.getComponents());
        this.primaryStage = stage;
        this.previousScene = stage.getScene();
        this.previousSceneTitle = stage.getTitle();
        this.primaryStage.setTitle("TechDog PC Configurator");
        ObservableList<Component> components = this.pc.getComponents();
        List<Product> cases = DatabaseService.getProductByCategory(Category.CASE);
        List<Product> speakers = DatabaseService.getProductByCategory(Category.SPEAKER);
        List<Product> cpus = DatabaseService.getProductByCategory(Category.CPU);
        components.add(new Component(cases.get(0), 1));
        components.add(new Component(speakers.get(0), 1));
        components.add(new Component(cpus.get(0), 1));
        pc.setComponents(components);
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
        // Save to disk
        try {
            File file = new File("test.xml"); // file name
            JAXBContext jaxbContext = JAXBContext.newInstance(Pc.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(pc, file); // save to a file
            jaxbMarshaller.marshal(pc, System.out); // echo to the console
        } catch (JAXBException e) {
            e.printStackTrace();
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

        }
    }

    @FXML
    private void onCancel(ActionEvent event) {
        this.primaryStage.setTitle(this.previousSceneTitle);
        this.primaryStage.setScene(this.previousScene);
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
