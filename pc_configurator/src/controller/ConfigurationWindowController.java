package controller;

import es.upv.inf.Product;
import es.upv.inf.Product.Category;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private TableColumn<Component, Double> totalPriceColumn;

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
        totalPriceColumn.textProperty().set("Total Price");

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
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("getTotalPriceWithoutVAT"));
        /*totalPriceColumn.setCellFactory(v -> {
            return new TableCell<Component, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        System.out.println(item);
                        setText(String.valueOf(item.getTotalPriceWithoutVAT()));
                    }
                }
            };
        });*/
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
            searchController.initStage(modalStage);

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
    }

    @FXML
    private void onContinue(ActionEvent event) {
    }

    @FXML
    private void onCancel(ActionEvent event) {
        this.primaryStage.setTitle(this.previousSceneTitle);
        this.primaryStage.setScene(this.previousScene);
    }

    @FXML
    private void onRemoveComponent(ActionEvent event) {
        pc.getComponents().remove(componentTable.getSelectionModel().getSelectedIndex());
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
            /*componentTable.getColumns().get(0).setVisible(false);
            componentTable.getColumns().get(0).setVisible(true);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
