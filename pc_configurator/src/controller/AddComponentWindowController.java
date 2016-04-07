package controller;

import es.upv.inf.Product;
import es.upv.inf.Product.Category;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Component;
import model.Pc;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class AddComponentWindowController implements Initializable {

    @FXML
    private Text productDescription;
    @FXML
    private Slider productQuantity;
    @FXML
    private Text summaryText;

    private Stage stage;
    private Product product;
    private Integer newQuantity = 1;
    private Pc pc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initStage(Stage modalStage, Product product, Pc pc) {
        this.stage = modalStage;
        this.product = product;
        this.pc = pc;
        stage.setTitle("Add");
        productDescription.setText(product.getDescription());
        productQuantity.setMax(product.getStock());
        productQuantity.setMin(1);
        productQuantity.setValue(1);

        updateSummary();

        productQuantity.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                newQuantity = newValue.intValue();
                updateSummary();
            }
        });
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        node.getScene().getWindow().hide();
    }

    @FXML
    private void onAdd(ActionEvent event) {
        Component component = new Component(product, newQuantity);
        boolean valid = true;
        for (Component c : pc.getComponents()) {
            if (c.getProductProperty().get().getCategory() == product.getCategory()) {
                valid = false;
                Alert alert = new Alert(AlertType.ERROR, "Component with category " + product.getCategory() + " is already in the budget");
                alert.showAndWait();
                break;
            }
        }
        if (valid) {
            pc.getComponents().add(component);
        }
        onCancel(event);
    }

    /**
     * Update summary text with total price.
     */
    public void updateSummary() {
        Double totalPriceTruncated = new BigDecimal(newQuantity * product.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        summaryText.setText("Price: " + newQuantity + " x "
                + product.getPrice() + " = "
                + totalPriceTruncated);
    }

}
