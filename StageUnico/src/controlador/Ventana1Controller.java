/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paco
 */
public class Ventana1Controller implements Initializable {

    @FXML
    private Button buttonAbrir;
    @FXML
    private Button buttonCerrar;

    private Stage primaryStage;
    private Scene escenaAnterior;
    private String tituloAnterior;

    public void initStage(Stage stage) {
        primaryStage = stage;
        escenaAnterior = stage.getScene();
        tituloAnterior = stage.getTitle();
        primaryStage.setTitle("Ventana 1");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void irAVentana2(ActionEvent event) {
        try {
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/Ventana2.fxml"));
            Parent root = (Parent) miCargador.load();
            // acceso al controlador de ventana 2
            Ventana2Controller ventana2 = miCargador.<Ventana2Controller>getController();
            ventana2.initStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarAccion(ActionEvent event) {
        System.out.println("Cerrando ventana 1");
        primaryStage.setTitle(tituloAnterior);
        primaryStage.setScene(escenaAnterior);
    }

}
