package model;

import java.util.List;
import java.util.Objects;

/**
 * Class representing a built computer.
 * 
 * @author filip
 */
public class Pc {

    private String name;
    private List<Component> components;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.components);
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
        final Pc other = (Pc) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.components, other.components)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pc{" + "name=" + name + ", components=" + components + '}';
    }

}
