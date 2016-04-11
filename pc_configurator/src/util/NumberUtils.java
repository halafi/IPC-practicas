package util;

import java.math.BigDecimal;

/**
 *
 * @author filip
 */
public class NumberUtils {
    
    /**
     * Round double number to a given precision.
     * 
     * @param number number to be rounded
     * @param precision number of decimal places
     * @return rounded number to a given precision
     */
    public static Double roundDouble(Double number, Integer precision) {
        return new BigDecimal(number).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
