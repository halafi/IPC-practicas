package controller;

import es.upv.inf.Product;
import es.upv.inf.Product.Category;
import java.net.URL;
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
import javafx.scene.control.CheckBox;
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
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField minPriceField;
    @FXML
    private TextField maxPriceField;
    @FXML
    private CheckBox inStockCheckbox;

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

        categoryComboBox.setValue(Category.SPEAKER);
        products = FXCollections.observableArrayList(DatabaseService.getProductByCategory(categoryComboBox.getValue()));
        productTable.setItems(products);

        categoryComboBox.setItems(FXCollections.observableArrayList(Category.values()));
        categoryComboBox.valueProperty().addListener(new ChangeListener<Category>() {
            @Override
            public void changed(ObservableValue<? extends Category> observable, Category oldValue, Category newValue) {
                boolean searchByDesc = !descriptionField.textProperty().get().equals("");
                boolean searchByMinPrice = !minPriceField.textProperty().get().equals("");
                boolean searchByMaxPrice = !maxPriceField.textProperty().get().equals("");
                if (!searchByDesc && (!searchByMinPrice || !searchByMaxPrice)) {
                    if (!searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategory(categoryComboBox.getValue()));
                    } else if (!searchByMinPrice && searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), 0, Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                    } else if (searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), Double.parseDouble(minPriceField.textProperty().get()), 9999, inStockCheckbox.isSelected()));
                    }
                }
                if (searchByDesc && (!searchByMinPrice || !searchByMaxPrice)) {
                    if (!searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndDescription(categoryComboBox.getValue(), descriptionField.getText(), inStockCheckbox.isSelected()));
                    } else if (!searchByMinPrice && searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), 0, Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                    } else if (searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), Double.parseDouble(minPriceField.textProperty().get()), 9999, inStockCheckbox.isSelected()));
                    }
                }
                if (!searchByDesc && searchByMinPrice && searchByMaxPrice) {
                    products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), Double.parseDouble(minPriceField.textProperty().get()), Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                }
                if (searchByDesc && searchByMinPrice && searchByMaxPrice) {
                    products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), Double.parseDouble(minPriceField.textProperty().get()), Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                }
            }
        });
        descriptionField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                boolean searchByDesc = !descriptionField.textProperty().get().equals("");
                boolean searchByMinPrice = !minPriceField.textProperty().get().equals("");
                boolean searchByMaxPrice = !maxPriceField.textProperty().get().equals("");
                if (!searchByDesc && (!searchByMinPrice || !searchByMaxPrice)) {
                    if (!searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategory(categoryComboBox.getValue()));
                    } else if (!searchByMinPrice && searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), 0, Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                    } else if (searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), Double.parseDouble(minPriceField.textProperty().get()), 9999, inStockCheckbox.isSelected()));
                    }
                }
                if (searchByDesc && (!searchByMinPrice || !searchByMaxPrice)) {
                    if (!searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndDescription(categoryComboBox.getValue(), descriptionField.getText(), inStockCheckbox.isSelected()));
                    } else if (!searchByMinPrice && searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), 0, Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                    } else if (searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), Double.parseDouble(minPriceField.textProperty().get()), 9999, inStockCheckbox.isSelected()));
                    }
                }
                if (!searchByDesc && searchByMinPrice && searchByMaxPrice) {
                    products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), Double.parseDouble(minPriceField.textProperty().get()), Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                }
                if (searchByDesc && searchByMinPrice && searchByMaxPrice) {
                    products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), Double.parseDouble(minPriceField.textProperty().get()), Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                }
            }
        });
        inStockCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                boolean searchByDesc = !descriptionField.textProperty().get().equals("");
                boolean searchByMinPrice = !minPriceField.textProperty().get().equals("");
                boolean searchByMaxPrice = !maxPriceField.textProperty().get().equals("");
                if (!searchByDesc && (!searchByMinPrice || !searchByMaxPrice)) {
                    if (!searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategory(categoryComboBox.getValue()));
                    } else if (!searchByMinPrice && searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), 0, Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                    } else if (searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), Double.parseDouble(minPriceField.textProperty().get()), 9999, inStockCheckbox.isSelected()));
                    }
                }
                if (searchByDesc && (!searchByMinPrice || !searchByMaxPrice)) {
                    if (!searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndDescription(categoryComboBox.getValue(), descriptionField.getText(), inStockCheckbox.isSelected()));
                    } else if (!searchByMinPrice && searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), 0, Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                    } else if (searchByMinPrice && !searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), Double.parseDouble(minPriceField.textProperty().get()), 9999, inStockCheckbox.isSelected()));
                    }
                }
                if (!searchByDesc && searchByMinPrice && searchByMaxPrice) {
                    products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), Double.parseDouble(minPriceField.textProperty().get()), Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                }
                if (searchByDesc && searchByMinPrice && searchByMaxPrice) {
                    products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), Double.parseDouble(minPriceField.textProperty().get()), Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                }
            }
        });
        minPriceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    minPriceField.setText(oldValue);
                    minPriceField.positionCaret(minPriceField.getLength());
                } else {
                    boolean searchByDesc = !descriptionField.textProperty().get().equals("");
                    boolean searchByMinPrice = !minPriceField.textProperty().get().equals("");
                    boolean searchByMaxPrice = !maxPriceField.textProperty().get().equals("");
                    if (!searchByDesc && (!searchByMinPrice || !searchByMaxPrice)) {
                        if (!searchByMinPrice && !searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategory(categoryComboBox.getValue()));
                        } else if (!searchByMinPrice && searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), 0, Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                        } else if (searchByMinPrice && !searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), Double.parseDouble(minPriceField.textProperty().get()), 9999, inStockCheckbox.isSelected()));
                        }
                    }
                    if (searchByDesc && (!searchByMinPrice || !searchByMaxPrice)) {
                        if (!searchByMinPrice && !searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategoryAndDescription(categoryComboBox.getValue(), descriptionField.getText(), inStockCheckbox.isSelected()));
                        } else if (!searchByMinPrice && searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), 0, Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                        } else if (searchByMinPrice && !searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), Double.parseDouble(minPriceField.textProperty().get()), 9999, inStockCheckbox.isSelected()));
                        }
                    }
                    if (!searchByDesc && searchByMinPrice && searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), Double.parseDouble(minPriceField.textProperty().get()), Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                    }
                    if (searchByDesc && searchByMinPrice && searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), Double.parseDouble(minPriceField.textProperty().get()), Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                    }
                }
            }
        });
        maxPriceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    maxPriceField.setText(oldValue);
                    maxPriceField.positionCaret(maxPriceField.getLength());
                } else {
                    boolean searchByDesc = !descriptionField.textProperty().get().equals("");
                    boolean searchByMinPrice = !minPriceField.textProperty().get().equals("");
                    boolean searchByMaxPrice = !maxPriceField.textProperty().get().equals("");
                    if (!searchByDesc && (!searchByMinPrice || !searchByMaxPrice)) {
                        if (!searchByMinPrice && !searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategory(categoryComboBox.getValue()));
                        } else if (!searchByMinPrice && searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), 0, Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                        } else if (searchByMinPrice && !searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), Double.parseDouble(minPriceField.textProperty().get()), 9999, inStockCheckbox.isSelected()));
                        }
                    }
                    if (searchByDesc && (!searchByMinPrice || !searchByMaxPrice)) {
                        if (!searchByMinPrice && !searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategoryAndDescription(categoryComboBox.getValue(), descriptionField.getText(), inStockCheckbox.isSelected()));
                        } else if (!searchByMinPrice && searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), 0, Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                        } else if (searchByMinPrice && !searchByMaxPrice) {
                            products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), Double.parseDouble(minPriceField.textProperty().get()), 9999, inStockCheckbox.isSelected()));
                        }
                    }
                    if (!searchByDesc && searchByMinPrice && searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryAndPrice(categoryComboBox.getValue(), Double.parseDouble(minPriceField.textProperty().get()), Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                    }
                    if (searchByDesc && searchByMinPrice && searchByMaxPrice) {
                        products.setAll(DatabaseService.getProductByCategoryDescriptionAndPrice(categoryComboBox.getValue(), descriptionField.getText(), Double.parseDouble(minPriceField.textProperty().get()), Double.parseDouble(maxPriceField.textProperty().get()), inStockCheckbox.isSelected()));
                    }
                }
            }
        });
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
