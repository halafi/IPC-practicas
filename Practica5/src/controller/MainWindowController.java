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

    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        
        statusBarText.setText(rb.getString("text.applicationStarted"));

    }

    @FXML
    private void onClose(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("alert.confirmation"));
        alert.setHeaderText(rb.getString("alert.aboutToLeave"));
        alert.setContentText(rb.getString("alert.sureToLeave") + "\n\n");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void onAmazonClick(ActionEvent event) {
        if (buyOnAmazonCheck.isSelected()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(rb.getString("alert.confirmation"));
            alert.setHeaderText(rb.getString("alert.purchaseCompleted"));
            alert.setContentText(rb.getString("alert.purchaseCompletedAmazon"));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(rb.getString("alert.selectionError"));
            alert.setHeaderText(rb.getString("alert.purchaseFailedAmazon"));
            alert.setContentText(rb.getString("alert.changeSelection"));
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
        dialog.setTitle(rb.getString("alert.selectBlog"));
        dialog.setHeaderText(rb.getString("alert.chooseBlog"));
        dialog.setContentText(rb.getString("text.choose"));
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            statusBarText.setText(rb.getString("label.visiting") + result.get());
        }
        //result.ifPresent(number -> System.out.println("Your choice: " + number));
    }

    @FXML
    private void onEbayClick(ActionEvent event) {
        if (buyOnEbayCheck.isSelected()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(rb.getString("alert.confirmation"));
            alert.setHeaderText(rb.getString("alert.purchaseCompleted"));
            alert.setContentText(rb.getString("alert.purchaseCompletedEbay"));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(rb.getString("alert.selectionError"));
            alert.setHeaderText(rb.getString("alert.purchaseFailedEbay"));
            alert.setContentText(rb.getString("alert.changeSelection"));
            alert.showAndWait();
        }
    }

    @FXML
    private void onFacebookClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("John"); // Default value
        dialog.setTitle(rb.getString("alert.introduceName"));
        dialog.setHeaderText(rb.getString("alert.whichUser") + "Facebook?");
        dialog.setContentText(rb.getString("alert.enterName"));
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            statusBarText.setText(rb.getString("label.messageSent") + result.get());
        }
    }

    @FXML
    private void onGooglePlusClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("John"); // Default value
        dialog.setTitle(rb.getString("alert.introduceName"));
        dialog.setHeaderText(rb.getString("alert.whichUser") + "Google+?");
        dialog.setContentText(rb.getString("alert.enterName"));
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            statusBarText.setText(rb.getString("label.messageSent") + result.get());
        }
    }

}
