/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3convertor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Slider conversionSlider;
    @FXML
    private Label conversionRate;
    @FXML
    private CheckBox automaticConversionCheckbox;
    @FXML
    private TextArea input;
    @FXML
    private TextArea output;
    @FXML
    private Button convertBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conversionSlider.valueProperty().addListener(new ChangeListener<Number>() {
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
            
        });
    }

    @FXML
    private void convertInput(MouseEvent event) {
        try {
            Float inputFl = Float.parseFloat(input.getText());
            Float conversionRateFl = Float.parseFloat(conversionRate.getText());
            output.setText(String.format("%.2f", inputFl * conversionRateFl));
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            output.setText(String.format("0.0"));
        }
    }

    @FXML
    private void clear(MouseEvent event) {
        output.clear();
        input.clear();
    }

    @FXML
    private void inputChange(KeyEvent event) {
        if (automaticConversionCheckbox.isSelected()) {
            convertInput(null);
        }
    }

}
