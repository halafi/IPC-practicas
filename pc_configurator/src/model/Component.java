package model;

import es.upv.inf.Product;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Class representing PC component.
 *
 * @author filip
 */
public class Component {

    private ObjectProperty<Product> product = new SimpleObjectProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();

    public Component() {

    }

    public Component(Product product, Integer quantity) {
        this.product.setValue(product);
        this.quantity.setValue(quantity);
    }

    @XmlTransient
    public ObjectProperty<Product> getProductProperty() {
        return product;
    }

    @XmlTransient
    public IntegerProperty getQuantityProperty() {
        return quantity;
    }

    @XmlElement(name = "product")
    public ProductWrapper getProduct() {
        return new ProductWrapper(product.get());
    }

    @XmlElement(name = "quantity")
    public Integer getQuantity() {
        return quantity.get();
    }

    public void setQuantity(Integer quantity) {
        this.quantity.set(quantity);
    }

    public void setProduct(ProductWrapper product) {
        Product p = new Product(product.getDescription(), product.getPrice(), product.getStock(), product.getCategory());
        this.product.set(p);
    }

    public void setProductProperty(ObjectProperty<Product> product) {
        this.product = product;
    }

    public void setQuantityProperty(IntegerProperty quantity) {
        this.quantity = quantity;
    }
}
