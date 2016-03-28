/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paco
 */
public class Ventana2Controller implements Initializable {

    @FXML
    private Button buttonCerrar;

    private Stage primaryStage;
    private Scene escenaAnterior;
    private String tituloAnterior;

    public void initStage(Stage stage) {
        primaryStage = stage;
        escenaAnterior = stage.getScene();
        tituloAnterior = stage.getTitle();
        primaryStage.setTitle("Ventana 2");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cerrarAccion(ActionEvent event) {
        System.out.println("Cerrando ventana 2");
        primaryStage.setTitle(tituloAnterior);
        primaryStage.setScene(escenaAnterior);
    }

}
