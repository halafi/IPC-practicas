package model;

import es.upv.inf.Product;

/**
 *
 * @author filip
 */
public class ProductWrapper {

    private String description;
    private double price;
    private int stock;
    private Product.Category category;

    public ProductWrapper() {
        
    }
    
    public ProductWrapper(Product product) {
        description = product.getDescription();
        price = product.getPrice();
        stock = product.getStock();
        category = product.getCategory();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Product.Category getCategory() {
        return category;
    }

    public void setCategory(Product.Category category) {
        this.category = category;
    }

}
