/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user.objects;

import java.io.Serializable;
import java.util.ArrayList;

import zenixjava.collections.Triplet;
import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.essential.ReachedMaxWarningEvent;
import zenixmc.event.essential.ReachedZeroWarningEvent;
import zenixmc.user.ZenixUserInterface;

/**
 * Map of amount of warnings and punishment.
 * @author james
 */
public class Warning implements Serializable {
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 784783646180386720L;

	/**
	 * The event dispatcher to fire events.
	 */
	private transient EventDispatcher eventDispatcher;
	
	/**
	 * The parent of the warning object.
	 */
	private transient ZenixUserInterface parent;
	
	/**
	 * The values.
	 */
    private final Triplet<Integer, Long, ArrayList<String>> values;
    
    /**
     * Instantiates a brand new warning.
     * @param parent
     * 		The parent of warning object.
     * @param eventDispatcher
     * 		The eventDispatcher to fire events.
     */
    public Warning(ZenixUserInterface parent, EventDispatcher eventDispatcher) {
    	this.eventDispatcher = eventDispatcher;
    	this.parent = parent;
        this.values = new Triplet<>(new Integer(0), 0L, new ArrayList<String>(3));
    }
    
    /**
     * Instantiates a warning with set values.
     * @param parent
     * 		The parent of warning object.
     * @param eventDispatcher
     *      The eventDispatcher to fire events.
     * @param values
     * 		The values.
     */
    public Warning(ZenixUserInterface parent, EventDispatcher eventDispatcher, Triplet<Integer, Long, ArrayList<String>> values) {
    	this.eventDispatcher = eventDispatcher;
    	this.parent = parent;
        this.values = values;
    }
    
    /**
     * Adds a warning and the punishment timestamp.
     * @param timestamp
     *      The punishment timestamp.
     */
    public void increment(long duration, String reason) {
        
    	System.out.println("Amount: " + values.getA());
        values.setA(values.getA() + 1);
        values.setB(values.getB() + duration);
        values.getC().add(reason);
    	
        //temp const
        if (values.getA() >= 3) {
        	eventDispatcher.callEvent(new ReachedMaxWarningEvent(parent));
            return;
        }
    }
    
    /**
     * Subtracts a warning but keeps the punishment timestamp as it is.
     */
    public void decrement() {
        
    	if (isZero()) {
    		eventDispatcher.callEvent(new ReachedZeroWarningEvent(parent));
            return;
    	}
        
        values.setA(values.getA() - 1);
    }
    
    /**
     * @return If amount has reached maximum warnings.
     */
    public boolean isMaximum() {
        //temp const
        return values.getA() >= 3;
    }
    
    /**
     * @return If amount is at zero.
     */
    public boolean isZero() {
    	
    	if (values.getA() < 0) {
    		values.setA(0);
    	}
    	
        return values.getA() == 0;
    }
    
    /**
     * @return The amount of warnings.
     */
    public int getAmount() {
    	return values.getA();
    }
    
    /**
     * @return The built-up sentence.
     */
    public long getSentence() {
    	return values.getB();
    }
    
    public String getReasonOne() {
    	if (values.getC().size() >= 1) {
    		return values.getC().get(0);
    	}
    	return " ";
    }
    
    public String getReasonTwo() {
    	if (values.getC().size() >= 2) {
    		return values.getC().get(1);
    	}
    	return " ";
    }
    
    public String getReasonThree() {
    	if (values.getC().size() >= 3) {
    		return values.getC().get(2);
    	}
    	return " ";
    }
    
    public void setEventDispatcher(EventDispatcher eventDispatcher) {
    	this.eventDispatcher = eventDispatcher;
    }
    
    public void setParent(ZenixUserInterface zui) {
    	this.parent = zui;
    }
}

