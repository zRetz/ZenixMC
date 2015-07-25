/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user.objects;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import zenixmc.user.ZenixUserInterface;

/**
 * Personal set location.
 * @author james
 */
public class Home {
    
    /**
     * The owner of the home.
     */
    private transient ZenixUserInterface zui;
    
    /**
     * The name of the home.
     */
    private final String name;
    
    /**
     * The location of the home.
     */
    private final Location location;
    
    /**
     * Instantiates a home with set values.
     * @param name
     *      The name of the home.
     * @param location
     *      The location of the home.
     */
    public Home(String name, Location location) {
        this.name = name;
        this.location = location;
    }
    
    /**
     * Teleports the user to this home.
     */
    public void goTo() {
        
    }
    
    /**
     * @return The owner of the home.
     */
    public ZenixUserInterface getZui() {
        return zui;
    }
    
    /**
     * @return The name of the home.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The location of the home.
     */
    public Location getLocation() {
        return location;
    }

}
