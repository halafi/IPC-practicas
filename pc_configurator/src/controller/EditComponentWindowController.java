package controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Component;
import util.NumberUtils;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class EditComponentWindowController implements Initializable {

    private Stage stage;
    private Component component;
    private Integer newQuantity;
    private TableColumn<Component, ?> column;

    @FXML
    private Slider productQuantity;
    @FXML
    private Text productDescription;
    @FXML
    private Text summaryText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initStage(Stage modalStage, Component edittedComponent, TableColumn<Component, ?> column) {
        stage = modalStage;
        stage.setTitle("Edit");
        this.column = column; // for table refresh bug
        component = edittedComponent;
        newQuantity = component.getQuantityProperty().intValue();
        productDescription.setText(component.getProductProperty().get().getDescription());
        productQuantity.setMax(component.getProductProperty().get().getStock());
        productQuantity.setMin(1);
        productQuantity.setValue(component.getQuantityProperty().doubleValue());
        if (component.getProductProperty().get().getStock() == 1) {
            productQuantity.setDisable(true);
        }
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
    private void onUpdate(ActionEvent event) {
        if (newQuantity > 0) {
            component.setQuantityProperty(new SimpleIntegerProperty(newQuantity));
            column.setVisible(false);
            column.setVisible(true);
            onCancel(event);
        }
    }

    /**
     * Update summary text with total price.
     */
    public void updateSummary() {
        summaryText.setText("Price: " + newQuantity + " x "
                + component.getProductProperty().get().getPrice() + " = "
                + NumberUtils.roundDouble(newQuantity * component.getProductProperty().get().getPrice(), 2));
    }
}
