/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Paco
 */
public class StageVarios extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
            Parent root = (Parent) miCargador.load();
            Scene scene = new Scene(root); 
            primaryStage.setTitle("Multi ventanas");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
