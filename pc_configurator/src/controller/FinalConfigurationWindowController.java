package controller;

import es.upv.inf.Product;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Component;
import model.FinalComponent;
import model.Pc;
import util.NumberUtils;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class FinalConfigurationWindowController implements Initializable {

    private Stage primaryStage;
    private Pc pc;

    @FXML
    private Text pcNameText;
    @FXML
    private Label noticeLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button printButton;
    @FXML
    private TableView<FinalComponent> componentTable;
    @FXML
    private TableColumn<FinalComponent, Product> descriptionColumn;
    @FXML
    private TableColumn<FinalComponent, Product> categoryColumn;
    @FXML
    private TableColumn<FinalComponent, Product> stockColumn;
    @FXML
    private TableColumn<FinalComponent, Product> priceColumn;
    @FXML
    private TableColumn<FinalComponent, Integer> amountColumn;
    @FXML
    private TableColumn<FinalComponent, Double> totalPriceColumn;
    @FXML
    private TableColumn<FinalComponent, Double> vatColumn;
    @FXML
    private TextField priceWithoutVatInput;
    @FXML
    private TextField priceWithVatInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        descriptionColumn.textProperty().set("Description");
        categoryColumn.textProperty().set("Category");
        stockColumn.textProperty().set("Stock");
        amountColumn.textProperty().set("Quantity");
        priceColumn.textProperty().set("Unit Price");
        vatColumn.textProperty().set("VAT (21%)");
        totalPriceColumn.textProperty().set("Total Price");

        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct());
        descriptionColumn.setCellFactory(v -> {
            return new TableCell<FinalComponent, Product>() {
                @Override
                protected void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getDescription());
                    }
                }
            };
        });
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct());
        categoryColumn.setCellFactory(v -> {
            return new TableCell<FinalComponent, Product>() {
                @Override
                protected void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getCategory().name());
                    }
                }
            };
        });
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct());
        priceColumn.setCellFactory(v -> {
            return new TableCell<FinalComponent, Product>() {
                @Override
                protected void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item.getPrice()));
                    }
                }
            };
        });
        stockColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct());
        stockColumn.setCellFactory(v -> {
            return new TableCell<FinalComponent, Product>() {
                @Override
                protected void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item.getStock()));
                    }
                }
            };
        });
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalPriceWithoutVAT().asObject());
        totalPriceColumn.setCellFactory(v -> {
            return new TableCell<FinalComponent, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(NumberUtils.roundDouble(item, 10)));
                    }
                }
            };
        });
        vatColumn.setCellValueFactory(cellData -> cellData.getValue().getVat().asObject());
        vatColumn.setCellFactory(v -> {
            return new TableCell<FinalComponent, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(NumberUtils.roundDouble(item, 2)));
                    }
                }
            };
        });

    }

    public void initStage(Stage stage, Pc pc) {
        this.pc = pc;
        ObservableList<FinalComponent> cpts = FXCollections.observableArrayList(new ArrayList<>());
        Double priceWithVAT = 0.0;
        Double priceWithoutVAT = 0.0;
        for (Component c : pc.getComponents()) {
            FinalComponent fc = new FinalComponent(c);
            cpts.add(fc);
            priceWithVAT += fc.getTotalPriceWithVAT().doubleValue();
            priceWithoutVAT += fc.getTotalPriceWithoutVAT().doubleValue();
        }
        priceWithoutVatInput.setText(String.valueOf(NumberUtils.roundDouble(priceWithoutVAT, 2)));
        priceWithVatInput.setText(String.valueOf(NumberUtils.roundDouble(priceWithVAT, 2)));
        priceWithoutVatInput.setEditable(false);
        priceWithVatInput.setEditable(false);
        componentTable.setDisable(true);
        componentTable.setItems(cpts);
        primaryStage = stage;
        primaryStage.setTitle("TechDog PC Final Overview");
        pcNameText.setText(pc.getName() + " PC Summary");
        LocalDateTime timePoint = LocalDateTime.now();
        noticeLabel.setText("Made on: " + timePoint.toLocalDate().toString() + ", valid for 7 days.");
    }

    @FXML
    private void onCancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        node.getScene().getWindow().hide();
    }

    @FXML
    private void onPrint(ActionEvent event) {
        Node node = new Circle(100, 200, 200);
        print(node);
    }

    public void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }

}
