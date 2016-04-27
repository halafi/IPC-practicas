package es.upv.inf;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author fjabad
 */
public class FXMLDocumentController implements Initializable {

    private static final int NUM_BUTTONS = 5;

    @FXML
    private Label label;
    @FXML
    private HBox buttonContainer;

    private long t0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < NUM_BUTTONS; i++) {
            Button b = new Button("Click me");
            b.setOnAction(this::onStopTimer);
            b.setPrefSize(80, 80);
            b.setDisable(true);
            buttonContainer.getChildren().add(b);
        }
        label.setText("");
    }

    @FXML
    private void onStart(ActionEvent event) {
        label.setText("Get ready...");
        for (Node b : buttonContainer.getChildren()) {
            b.setDisable(true);
        }
        double waitTime = Math.random() * 5 + 1.0;
        label.getScene().setCursor(Cursor.WAIT);
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() {
                try {
                    Thread.sleep((long) (waitTime * 1000));
                } catch (InterruptedException e) {
                    if (isCancelled()) {
                        return -1;
                    }
                }
                return 0;
            }
        };
        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        if (task.getValue() == -1) {
                            
                        }
                        label.getScene().setCursor(Cursor.DEFAULT);
                        t0 = System.currentTimeMillis();
                        int winnerButton = (int) (Math.floor(Math.random() * NUM_BUTTONS));
                        buttonContainer.getChildren().get(winnerButton).setDisable(false);
                        label.setText("Now!");
                    }
                });
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    private void onStopTimer(ActionEvent event) {
        long t1 = System.currentTimeMillis();
        long elapsed = t1 - t0;

        String msg;
        if (elapsed < 150) {
            msg = "You are fast as an arrow! ";
        } else if (elapsed < 500) {
            msg = "Pretty good. ";
        } else if (elapsed < 1000) {
            msg = "Good. ";
        } else {
            msg = "You should try harder. ";
        }
        label.setText(msg + "You needed " + elapsed + " ms");

        ((Node) event.getSource()).setDisable(true);
    }
}
