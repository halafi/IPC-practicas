package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class representing a built computer.
 *
 * @author filip
 */
@XmlRootElement(name = "pc")
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

    @Override
    public String toString() {
        return name;
    }

}
