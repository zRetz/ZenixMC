/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user;

import java.util.TreeMap;

/**
 * Map of amount of warnings and punishment.
 * @author james
 */
public class Warning {
    
    /**
     * The map of amount of warnings and punishment timestamp.
     */
    private final TreeMap<Integer, Long> values;
    
    /**
     * Instantiates a brand new warning.
     */
    public Warning() {
        this.values = new TreeMap<>();
        put(0, 0L);
    }
    
    /**
     * Instantiates a warning with set values.
     * @param amount The amount of warnings.
     * @param timestamp The punishment stamp.
     */
    public Warning(int amount, long timestamp) {
        this.values = new TreeMap<>();
        put(amount, timestamp);
    }
    
    /**
     * Adds a warning and the punishment timestamp.
     * @param timestamp The punishment timestamp.
     * @return If it successfully incremented.
     */
    public boolean increment(long timestamp) {
        
        int amount = values.firstKey();
        
        //temp const
        if (amount >= 3) {
            return false;
        }
        
        put(amount + 1, values.get(amount) + timestamp);
        return true;
    }
    
    /**
     * Subtracts a warning but keeps the punishment timestamp as it is.
     * @return If it successfully decremented.
     */
    public boolean decrement() {
        
        int amount = values.firstKey();
        int nAmount = amount - 1;
        
        if (nAmount < 0) {
            return false;
        }
        
        put(nAmount, values.get(amount));
        return true;
    }
    
    /**
     * @return If amount has reached maximum warnings.
     */
    public boolean isMaximum() {
        //temp const
        return values.firstKey() >= 3;
    }
    
    /**
     * Puts values into the values map.
     * @param amount The amount of warnings (key).
     * @param timestamp The punishment timestamp (value).
     */
    private void put(int amount, long timestamp) {
        values.clear();
        values.put(amount, timestamp);
    }
}
