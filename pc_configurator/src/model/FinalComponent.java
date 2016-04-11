package model;

import es.upv.inf.Product;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author filip
 */
public class FinalComponent {
    
    private ObjectProperty<Product> product = new SimpleObjectProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();
    private DoubleProperty totalPriceWithoutVAT = new SimpleDoubleProperty();
    private DoubleProperty totalPriceWithVAT = new SimpleDoubleProperty();
    private DoubleProperty vat = new SimpleDoubleProperty();
    
    public FinalComponent(Component component) {
        this.product = component.getProductProperty();
        this.quantity = component.getQuantityProperty();
        this.totalPriceWithVAT.set(component.getQuantity() * component.getProductProperty().get().getPrice() * 1.21);
        this.totalPriceWithoutVAT.set(component.getQuantity() * component.getProductProperty().get().getPrice());
        this.vat.set(component.getQuantity() * component.getProductProperty().get().getPrice() * 0.21);
    }

    public ObjectProperty<Product> getProduct() {
        return product;
    }
    
    public void setProduct(ObjectProperty<Product> product) {
        this.product = product;
    }
    
    public IntegerProperty getQuantity() {
        return quantity;
    }
    
    public void setQuantity(IntegerProperty quantity) {
        this.quantity = quantity;
    }
    
    public DoubleProperty getTotalPriceWithoutVAT() {
        return totalPriceWithoutVAT;
    }
    
    public void setTotalPriceWithoutVAT(DoubleProperty totalPriceWithoutVAT) {
        this.totalPriceWithoutVAT = totalPriceWithoutVAT;
    }
    
    public DoubleProperty getTotalPriceWithVAT() {
        return totalPriceWithVAT;
    }
    
    public void setTotalPriceWithVAT(DoubleProperty totalPriceWithVAT) {
        this.totalPriceWithVAT = totalPriceWithVAT;
    }
    
    public DoubleProperty getVat() {
        return vat;
    }
    
    public void setVat(DoubleProperty vat) {
        this.vat = vat;
    }
    
}
