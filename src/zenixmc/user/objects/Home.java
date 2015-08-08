/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.bukkit.Location;

import zenixmc.io.SerializableLocation;
import zenixmc.user.ZenixUserInterface;

/**
 * Personal set location.
 * @author james
 */
public class Home implements Serializable {
    
    /**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -1109205647337243688L;

	/**
     * The owner of the home.
     */
    private transient ZenixUserInterface zui;
    
    /**
     * The name of the home.
     */
    private String name;
    
    /**
     * The location of the home.
     */
    private Location location;
    
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
        this.zui.getTeleport().teleportToLocation(zui, location, false, true, 0);
    }
    
    /**
     * Sets the homes parent.
     * @param zui
     * 		The zenix user.
     */
    public void setZenixUser(ZenixUserInterface zui) {
    	this.zui = zui;
    }
    
    /**
     * @return The owner of the home.
     */
    public ZenixUserInterface getZenixUser() {
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
    
    /**
	 * Serialize this instance.
	 * 
	 * @param out
	 *            Target to which this instance is written.
	 * @throws IOException
	 *             Thrown if exception occurs during serialization.
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.writeUTF(name);
		out.writeObject(new SerializableLocation(location));
	}

	/**
	 * Deserialize this instance from input stream.
	 * 
	 * @param in
	 *            Input Stream from which this instance is to be deserialized.
	 * @throws IOException
	 *             Thrown if error occurs in deserialization.
	 * @throws ClassNotFoundException
	 *             Thrown if expected class is not found.
	 */
	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
		name = in.readUTF();
		location = ((SerializableLocation) in.readObject()).toLocation();
	}
    
}
