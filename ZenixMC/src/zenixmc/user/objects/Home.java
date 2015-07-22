/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user.objects;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import zenixmc.utils.io.SerialisableObjectInterface;
import zenixmc.user.ZenixUserInterface;

/**
 * Personal set location.
 * @author james
 */
public class Home implements SerialisableObjectInterface {
    
    /**
     * The owner of the home.
     */
    private ZenixUserInterface zui;
    
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

    @Override
    public Map<String, Object> serialise() {
        
        HashMap<String, Object> result = new HashMap<>();
        
        result.put("owner-uuid", zui.getPlayer().getUniqueId().toString());
        result.put("home-name", name);
        result.put("loc-x", location.getX());
        result.put("loc-y", location.getY());
        result.put("loc-z", location.getZ());
        
        return result;
    }
}
