package services;

import es.upv.inf.Database;
import es.upv.inf.Product;
import es.upv.inf.Product.Category;
import java.util.ArrayList;
import java.util.List;

/**
 * Layer to provide access to the database.
 *
 * @author filip
 */
public class DatabaseService extends Database {
    
    /**
     * Returns all products from database.
     * 
     * @return List of all products in the database 
     */
    public static List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        for (Category cat : Category.values()) {
            allProducts.addAll(getProductByCategory(cat));
        }
        return allProducts;
    }
}
