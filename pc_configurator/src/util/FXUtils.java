/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;

/**
 *
 * @author Filip
 */
public class FXUtils {

    /**
     * Prints a node.
     *
     * @param node The scene node to be printed.
     */
    public static void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();

        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if (job != null && job.showPrintDialog(null)) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }
}
