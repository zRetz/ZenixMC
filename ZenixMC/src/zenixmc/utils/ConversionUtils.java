/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils;

import java.util.List;
import java.util.TreeMap;
import zenixmc.utils.exceptions.NotEvenException;

/**
 *
 * @author james
 */
public class ConversionUtils {
    
    public static TreeMap<String, Object> arraysToTreeMap(String[] strings, Object[] objects) throws NotEvenException {
        
        if (strings.length != objects.length) {
            throw ExceptionUtils.notEvenException("strings and objects need to be even.");
        }
        
        TreeMap<String, Object> result = new TreeMap<>();
        
        for (int i=0; i < strings.length; i++) {
            result.put(strings[i], objects[i]);
        }
        
        return result;
    }
}
