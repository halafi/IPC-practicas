package upv.etsinf.ipc;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField texto_usuario;
    @FXML
    private Text mensaje_usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pulsadoIniciar(ActionEvent event) {
        mensaje_usuario.setText("Bienvenido "+ texto_usuario.getText());
    }
    
}
