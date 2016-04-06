package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class MainWindowController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private Label statusBarText;
    @FXML
    private ToggleGroup buyOn;
    @FXML
    private RadioMenuItem buyOnAmazonCheck;
    @FXML
    private RadioMenuItem buyOnEbayCheck;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statusBarText.setText("Application started");

    }

    @FXML
    private void onClose(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You are about to leave the program");
        alert.setContentText("Are you sure you want to leave?\n\n");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void onAmazonClick(ActionEvent event) {
        if (buyOnAmazonCheck.isSelected()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("You have completed your purchase");
            alert.setContentText("You have bought on Amazon");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("You cannot buy on Amazon");
            alert.setContentText("Please change the current selection in the options menu");
            alert.showAndWait();
        }
    }

    @FXML
    private void onBloggerClick(ActionEvent event) {
        List<String> choices = new ArrayList<>();
        choices.add("Athos' blog");
        choices.add("Porthos' blog");
        choices.add("Aramis' blog");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Athos' blog", choices);
        dialog.setTitle("Select a blog");
        dialog.setHeaderText("Which blog do you want to visit?");
        dialog.setContentText("Choose:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            statusBarText.setText("Visiting " + result.get());
        }
        //result.ifPresent(number -> System.out.println("Your choice: " + number));
    }

    @FXML
    private void onEbayClick(ActionEvent event) {
        if (buyOnEbayCheck.isSelected()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("You have completed your purchase");
            alert.setContentText("You have bought on Ebay");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("You cannot buy on Ebay");
            alert.setContentText("Please change the current selection in the options menu");
            alert.showAndWait();
        }
    }

    @FXML
    private void onFacebookClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("John"); // Default value
        dialog.setTitle("Introduce your name");
        dialog.setHeaderText("Which user do you want to use to write on Facebook?");
        dialog.setContentText("Enter your name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            statusBarText.setText("Message sent as " + result.get());
        }
    }

    @FXML
    private void onGooglePlusClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("John"); // Default value
        dialog.setTitle("Introduce your name");
        dialog.setHeaderText("Which user do you want to use to write on Google+?");
        dialog.setContentText("Enter your name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            statusBarText.setText("Message sent as " + result.get());
        }
    }

}
