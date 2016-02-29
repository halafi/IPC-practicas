package practica3;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Text counter;
    @FXML
    private CheckBox subtract;
    @FXML
    private TextField customValue;
    @FXML
    private Label subtractingLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        counter.setText("0.0");
    }

    @FXML
    private void changeOne(MouseEvent event) {
        Float value = Float.parseFloat(counter.getText());
        modifyCounter(1F);
    }

    @FXML
    private void changeFive(MouseEvent event) {
        Float value = Float.parseFloat(counter.getText());
        modifyCounter(5F);
    }

    @FXML
    private void changeTen(MouseEvent event) {
        Float value = Float.parseFloat(counter.getText());
        modifyCounter(10F);
    }

    @FXML
    private void changeCustom(MouseEvent event) {
        Float toChange = Float.parseFloat(customValue.getText());
        modifyCounter(toChange);
    }

    @FXML
    private void changeSubtracting(MouseEvent event) {
        if (subtract.isSelected()) {
            subtractingLabel.setVisible(true);
        } else {
            subtractingLabel.setVisible(false);
        }
    }

    private void modifyCounter(Float modifyBy) {
        Float value = Float.parseFloat(counter.getText());
        if (!subtract.isSelected()) {
            value = value + modifyBy;
        } else {
            value = value - modifyBy;
        }
        counter.setText(value.toString());
    }
}
