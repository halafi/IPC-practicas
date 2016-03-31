package controller;

import es.upv.inf.Product;
import es.upv.inf.Product.Category;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.DatabaseService;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class SearchComponentsController implements Initializable {

    private ObservableList<Product> products = null;
    private Stage stage;

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, Integer> stockColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Category> categoryColumn;

    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        descriptionColumn.textProperty().set("Description");
        categoryColumn.textProperty().set("Category");
        stockColumn.textProperty().set("Stock");
        priceColumn.textProperty().set("Price");

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        addButton.disableProperty().bind(
                Bindings.equal(-1,
                        productTable.getSelectionModel().selectedIndexProperty()));

        products = FXCollections.observableArrayList(DatabaseService.getAllProducts());
        productTable.setItems(products);

        categoryComboBox.setItems(FXCollections.observableArrayList(Category.values()));
        categoryComboBox.valueProperty().addListener(new ChangeListener<Category>() {
            @Override
            public void changed(ObservableValue<? extends Category> observable, Category oldValue, Category newValue) {
                products.setAll(DatabaseService.getProductByCategory(newValue));
            }
        });
        /*conversionSlider.valueProperty().addListener(new ChangeListener<Number>() {
         @Override
         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
         conversionRate.textProperty().setValue(
         String.format("%.2f", conversionSlider.getValue())
         );
         if (automaticConversionCheckbox.isSelected() && input.getText() != null && !input.getText().equals("")) {
         convertInput(null);
         }
         }
         });
         automaticConversionCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
         @Override
         public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
         if (newValue) {
         convertInput(null);
         convertBtn.setDisable(true);
         } else {
         convertBtn.setDisable(false);
         }
         }
            
         });*/
    }

    void initStage(Stage modalStage) {
        this.stage = modalStage;
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        node.getScene().getWindow().hide();
    }

    @FXML
    private void onAdd(ActionEvent event) {
    }

}
