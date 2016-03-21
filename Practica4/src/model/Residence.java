
package model;

/**
 *
 * @author filip
 */
public class Residence {
    private final String city;
    private final String province;

    public Residence(String city, String province) {
        this.city = city;
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }
    
    
}
