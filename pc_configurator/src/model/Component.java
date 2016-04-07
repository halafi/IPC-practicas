package model;

import es.upv.inf.Product;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlElement;

/**
 * Class representing PC component.
 *
 * @author filip
 */
public class Component {

    private ObjectProperty<Product> product = new SimpleObjectProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();

    public Component(Product product, Integer quantity) {
        this.product.setValue(product);
        this.quantity.setValue(quantity);
    }

    public ObjectProperty<Product> getProductProperty() {
        return product;
    }

    public IntegerProperty getQuantityProperty() {
        return quantity;
    }

    public void setProduct(ObjectProperty<Product> product) {
        this.product = product;
    }

    public void setQuantity(IntegerProperty quantity) {
        this.quantity = quantity;
    }
}
