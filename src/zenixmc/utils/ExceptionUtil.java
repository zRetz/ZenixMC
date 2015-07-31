/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import zenixmc.utils.exceptions.NotEvenException;

/**
 *
 * @author james
 */
public class ExceptionUtil {
    
    /**
     * Static logger to debug/log.
     */
    private static final Logger log = Logger.getLogger("ZenixMC");
    
    public static IllegalArgumentException illegalArgumentException(String msg) {
        return new IllegalArgumentException(msg);
    }
    
    public static NullPointerException nullPointerException(String msg) {
        return new NullPointerException(msg);
    }
    
    public static IndexOutOfBoundsException indexOutOfBoundsException(String msg) {
        return new IndexOutOfBoundsException(msg);
    }
    
    public static NotEvenException notEvenException(String msg) {
        return new NotEvenException(msg);
    }
    
    public static boolean exists(String obName, Class<?> clazz, Object ob) {
        
        if (ob == null) {
            log.log(Level.WARNING, "{0} does not exist in {1}", new Object[]{obName, clazz.getName()});
        }
        
        return true;
    }
}
