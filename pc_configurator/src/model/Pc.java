package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class representing a built computer.
 *
 * @author filip
 */
public class Pc {

    private String name;
    private ObservableList<Component> components;
    
    public Pc() {
        this.name = null;
        this.components = FXCollections.observableArrayList(new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableList<Component> getComponents() {
        return components;
    }

    public void setComponents(ObservableList<Component> components) {
        this.components = components;
    }

}
