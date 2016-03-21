package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

    private final StringProperty fullName = new SimpleStringProperty();
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final ObjectProperty<Residence> residence = new SimpleObjectProperty();
    private final StringProperty pathImagen = new SimpleStringProperty();

    public Person(String fullName, Integer id, Residence residence, String pathImagen) {
        this.fullName.setValue(fullName);
        this.id.setValue(id);
        this.residence.setValue(residence);
        this.pathImagen.setValue(pathImagen);
    }

    public String getFullName() {
        return fullName.get();
    }

    public Integer getId() {
        return id.get();
    }

    public Residence getResidence() {
        return residence.get();
    }

    public ObjectProperty<Residence> getResidenceProperty() {
        return residence;
    }

    public StringProperty getPathImagen() {
        return pathImagen;
    }
    
    
}
