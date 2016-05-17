/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test011;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Paco
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField textfield;
    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        label.setText(textfield.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clearButtonAction(ActionEvent event) {
        textfield.clear();
        label.setText("");
    }

}
