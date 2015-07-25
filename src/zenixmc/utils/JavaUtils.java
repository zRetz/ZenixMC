/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import zenixmc.utils.exceptions.NotEvenException;

/**
 * Utilities class for Java objects.
 * @author james
 */
public class JavaUtils {

    /**
     * Converts two arrays of the same length to a tree map.
     *
     * @param as
     *      The first array.
     * @param bs
     *      The second array.
     * @return The tree map.
     * @throws NotEvenException
     */
    public static <A, B> TreeMap<A, B> arraysToTreeMap(A[] as, B[] bs) throws NotEvenException {

        if (as.length != bs.length) {
            throw ExceptionUtils.notEvenException("strings and objects need to be even.");
        }

        TreeMap<A, B> result = new TreeMap<>();

        for (int i = 0; i < as.length; i++) {
            result.put(as[i], bs[i]);
        }

        return result;
    }
    
    /**
     * Inserts a key and value into a map specified by index. The maps types are specified by generic types.
     * and the user of this method can decide whether he wants to replace at index.
     * @param <S>
     *      Type one.
     * @param <T>
     *      Type two.
     * @param in
     *      The input map.
     * @param index
     *      The position to insert.
     * @param key
     *      The key to insert.
     * @param value
     *      The value to insert.
     * @param replace
     *      Replaces at index if <code>true</code>
     * @return The output map.
     */
    public static <S, T> LinkedHashMap<S, T> orderedPut(LinkedHashMap<S, T> in, int index, S key, T value, boolean replace) {

        LinkedHashMap<S, T> input = new LinkedHashMap<>(in);
        LinkedHashMap<S, T> output = new LinkedHashMap<>();

        if (index >= 0 && index <= input.size()) {
            int i = 0;
            if (index == 0) {
                output.put(key, value);
                if (replace) {
                    orderedRemove(input, 0);
                }
                output.putAll(input);
            } else {
                if (replace) {
                    for (Map.Entry<S, T> entry : input.entrySet()) {
                        if (i == index) {
                            output.put(key, value);
                        }else {
                            output.put(entry.getKey(), entry.getValue());
                            i++;
                        }
                    }
                }else {
                    for (Map.Entry<S, T> entry : input.entrySet()) {
                        if (i == index) {
                            output.put(key, value);
                        }
                        output.put(entry.getKey(), entry.getValue());
                        i++;
                    }
                }
            }
            if (index == input.size()) {
                output.putAll(input);
                output.put(key, value);
            }
        }else {
            throw ExceptionUtils.indexOutOfBoundsException("index cant be bigger or smaller than input");
        }

        return output;
    }
    
    /**
     * Removes a map entry specified by index. The map types and specified by generic types.
     * @param <S>
     *      Type one.
     * @param <T>
     *      Type two.
     * @param input
     *      The input map.
     * @param index
     *      The position to remove.
     * @return The output map.
     */
    public static <S, T> LinkedHashMap<S, T> orderedRemove(LinkedHashMap<S, T> input, int index) {
        
        LinkedHashMap<S, T> output = new LinkedHashMap<>();
        
        List<S> keys = new ArrayList<>(input.keySet());
        
        if (index >= 0 && index <= keys.size()) {
            input.remove(keys.get(index));
            output.putAll(input);
        }else {
            throw ExceptionUtils.indexOutOfBoundsException("index cant be bigger or smaller than input");
        }
        
        return output;
    }
    
    public static <S, T> Map<S, T> singleElementsToMap(S key, T value, Map<S, T> map) {
        
        if (map == null) {
            Map<S, T> r = new HashMap<>();
            map = r;
        }
        
        map.put(key, value);
        return map;
    }
}
