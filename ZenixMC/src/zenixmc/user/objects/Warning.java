/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user.objects;

import zenixmc.event.EventDispatcher;
import zenixmc.event.ReachedMaxWarningEvent;
import zenixmc.event.ReachedZeroWarningEvent;
import zenixmc.user.ZenixUserInterface;

/**
 * Map of amount of warnings and punishment.
 * @author james
 */
public class Warning {
	
	/**
	 * The event dispatcher to fire events.
	 */
	private transient final EventDispatcher eventDispatcher;
	
	/**
	 * The parent of the warning object.
	 */
	private transient final ZenixUserInterface parent;
	
    /**
     * The amount of warnings.
     */
    private int amount;
    
    /**
     * The duration of the sentence.
     */
    private long sentence;
    
    /**
     * Instantiates a brand new warning.
     * @param parent
     * 		The parent of warning object.
     */
    public Warning(ZenixUserInterface parent, EventDispatcher eventDispatcher) {
    	this.eventDispatcher = eventDispatcher;
    	this.parent = parent;
        this.amount = 0;
        this.sentence = 0;
    }
    
    /**
     * Instantiates a warning with set values.
     * @param
     * 		The parent of warning object.
     * @param amount
     *      The amount of warnings.
     * @param timestamp
     *      The punishment stamp.
     */
    public Warning(ZenixUserInterface parent, EventDispatcher eventDispatcher, int amount, long timestamp) {
    	this.eventDispatcher = eventDispatcher;
    	this.parent = parent;
        this.amount = amount;
        this.sentence = timestamp;
    }
    
    /**
     * Adds a warning and the punishment timestamp.
     * @param timestamp
     *      The punishment timestamp.
     */
    public void increment(long timestamp) {
        
        //temp const
        if (amount >= 3) {
        	eventDispatcher.callEvent(new ReachedMaxWarningEvent(parent));
            return;
        }
        
        amount = amount + 1;
        sentence = sentence + timestamp;
    }
    
    /**
     * Subtracts a warning but keeps the punishment timestamp as it is.
     */
    public void decrement() {
        
    	if (isZero()) {
    		eventDispatcher.callEvent(new ReachedZeroWarningEvent(parent));
            return;
    	}
        
        amount = amount - 1;
    }
    
    /**
     * @return If amount has reached maximum warnings.
     */
    public boolean isMaximum() {
        //temp const
        return amount >= 3;
    }
    
    /**
     * @return If amount is at zero.
     */
    public boolean isZero() {
    	
    	if (amount < 0) {
    		amount = 0;
    	}
    	
        return amount == 0;
    }

}
