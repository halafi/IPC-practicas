package practica7;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private NumberAxis lineYAxis;
    @FXML
    private CategoryAxis lineXAxis;
    @FXML
    private NumberAxis barYAxis;
    @FXML
    private CategoryAxis barXAxis;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int hist[] = new int[10];
        for (int i = 0; i < hist.length; i++) {
            hist[i] = 0;
        }
        for (int j = 0; j < 1000; j++) {
            double value = Math.random() * 10;
            for (int i = 0; i < hist.length; i++) {
                if (i <= value && value < i + 1) {
                    hist[i]++;
                    break;
                }
            }
        }
        // LINE CHART

        lineXAxis.setLabel("Ranges");
        lineYAxis.setLabel("Frequencies");

        XYChart.Series<String, Number> series = new XYChart.Series();
        for (int i = 0; i < hist.length; i++) {
            series.getData().add(new XYChart.Data<>(i + "-" + (i + 1), hist[i]));
        }
        series.setName("Histogram");
        lineChart.getData().add(series);
        // BAR CHART
        XYChart.Series series1 = new XYChart.Series();
        for (int i = 0; i < hist.length; i++) {
            series1.getData().add(new XYChart.Data<>(i + "-" + (i + 1), hist[i]));
        }
        barXAxis.setLabel("Ranges");
        barYAxis.setLabel("Frequencies");

        barChart.getData().addAll(series1);
        //barChart.setBarGap(3);
        //barChart.setCategoryGap(20);

        // PIE CHART
        //pieChart.setTitle("Imported fruits");
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < hist.length; i++) {
            pieChartData.add(new PieChart.Data(i + "-" + (i + 1), hist[i]));
        }
        pieChart.setData(pieChartData);
        pieChart.setLabelsVisible(true);
        pieChart.setLegendSide(Side.BOTTOM);

    }

}
