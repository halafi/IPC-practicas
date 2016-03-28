/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stageunico;

import controlador.Ventana1Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Paco
 */
public class PrincipalController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button buttonIr;
    @FXML
    private Button buttonSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    private Stage primaryStage;

    public void initStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    private void irAVentana1(ActionEvent event) {
        try {
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/Ventana1.fxml"));
            Parent root = (Parent) miCargador.load();
            // acceso al controlador de ventana 1
            Ventana1Controller ventana1 = miCargador.<Ventana1Controller>getController();
            ventana1.initStage(primaryStage);
            Scene scene = new Scene(root); 
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        primaryStage.hide();
    }

}
