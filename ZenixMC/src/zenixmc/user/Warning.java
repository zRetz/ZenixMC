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
    private TreeMap<Integer, Long> values;
    
    public Warning() {
        this.values = new TreeMap<>();
        put(0, 0L);
    }
    
    public Warning(int amount, long timestamp) {
        this.values = new TreeMap<>();
        put(amount, timestamp);
    }
    
    public void increment(long timestamp) {
        
        int amount = values.firstKey();
        
        //temp const
        if (amount > 3) {
            return;
        }
        
        long currentTimestamp = values.get(amount);
        put(amount + 1, currentTimestamp + timestamp);
    }
    
    public void decrement() {
        
    }
    
    private void put(int amount, long timestamp) {
        values.clear();
        values.put(amount, timestamp);
    }
}
