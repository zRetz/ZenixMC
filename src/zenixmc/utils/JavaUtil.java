/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import zenixmc.utils.exceptions.NotEvenException;

/**
 * Utilities class for Java objects.
 * @author james
 */
public class JavaUtil {

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
            throw new NotEvenException("strings and objects need to be even.");
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
            throw new IndexOutOfBoundsException("index cant be bigger or smaller than input");
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
            throw new IndexOutOfBoundsException("index cant be bigger or smaller than input");
        }
        
        return output;
    }
    
    /**
     * Puts two single elements into a map.
     * @param key
     * 		The key of the map.
     * @param value
     * 		The value of the map.
     * @param map
     * 		The instance of the map.
     * @return The filled map.
     */
    public static <S, T> Map<S, T> singleElementsToMap(S key, T value, Map<S, T> map) {
        
        if (map == null) {
            Map<S, T> r = new HashMap<>();
            map = r;
        }
        
        map.put(key, value);
        return map;
    }
    
    /**
     * Replaces an element in a map with another element.
     * @param map
     * 		The map.
     * @param key
     * 		The key to remove.
     * @param entry
     * 		The entry to enter.
     */
    public static <K, V> void replaceElementInMap(Map<K, V> map, K key, Entry<K, V> entry) {
    	map.remove(key);
    	map.put(entry.getKey(), entry.getValue());
    }
    
    /**
     * Checks if the given string is an integer within the given radix.
     * @param s
     * 		The string to check.
     * @param radix
     * 		The radix.
     * @return <code>true</code> If the string can be parsed to an integer.
     */
    public static boolean canBeInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
    
    /**
     * Checks if the given string can be parsed as an integer. 
     * @param integer
     * 		The string to check.
     * @return <code>true</code> If the string can be parsed as an integer.
     */
    public static boolean canBeInteger(String integer) {
    	return canBeInteger(integer, 10);
    }
    
    /**
     * Checks if given strings can be parsed as integers.
     * @param integers
     * 		The strings to check.
     * @return <code>true</code> If they can be parsed as integers.
     */
    public static boolean canBeIntegers(String... integers) {
    	
    	for (String s : integers) {
    		if (!(canBeInteger(s))) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * Removes specified elements from an array.
     * @param array
     * 		The array to modify.
     * @param type
     * 		The type inside the array.
     * @param indices
     * 		The indices to remove the elements from.
     * @return The modified array.
     * 
     */
    @SuppressWarnings("unchecked")
	public static <T> T[] removeElementsFromArray(T[] array, Class<T> type, Integer... indices) {
    	
    	ArrayList<T> result = new ArrayList<>(Arrays.asList(array));
    	
    	for (Integer i : indices) {
    		if (i < result.size()) {
    			result.remove(i.intValue());
    		}
    	}
    	
    	return result.toArray((T[]) Array.newInstance(type, result.size()));
    }
    
    /**
     * Converts an array to string.
     * @param array
     * 		The array to convert.
     * @return	The string.
     */
    public static String arrayToString(String[] array) {
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for (int i=0; i < array.length; i++) {
    		if (i != 0) {
    			sb.append(" ");
    		}
    		if (array[i] != null) {
    			sb.append(array[i]);
    		}
    	}
    	
    	return sb.toString();
    }
    
    /**
     * Checks if an array contains specified elements.
     * @param array
     * 		The array.
     * @param elements
     * 		The elements to check.
     * @return <code>true</code> If the array contains the elements.
     */
    public static <T> boolean arrayContainsElements(T[] array, T... elements) {
    	return Arrays.asList(array).containsAll(Arrays.asList(elements));
    }
    
    /**
     * @param i
     * 		The number to check.
     * @return <code>true</code> If the number is positive.
     */
    public static boolean isPositive(Number i) {
    	return i.intValue() > 0;
    }
    
    /**
     * @param i
     * 		The number to check.
     * @return <code>true</code> If the number is negative.
     */
    public static boolean isNegative(Number i) {
    	return i.intValue() < 0;
    }
    
    /**
     * @param s
     * 		The string to check.
     * @return <code>true</code> If the string can be parsed to a boolean.
     */
    public static boolean canBeBoolean(String s) {
    	return s.equals("true") || s.equals("false");
    }
}
