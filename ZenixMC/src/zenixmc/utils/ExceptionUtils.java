/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils;

import java.security.InvalidParameterException;
import zenixmc.utils.exceptions.NotEvenException;

/**
 *
 * @author james
 */
public class ExceptionUtils {
    
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
}
