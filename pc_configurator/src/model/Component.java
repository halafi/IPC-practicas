package model;

import es.upv.inf.Product;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.product);
        hash = 23 * hash + Objects.hashCode(this.quantity);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Component other = (Component) obj;
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Component{" + "product=" + product + ", quantity=" + quantity + '}';
    }

}
