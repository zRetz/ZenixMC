package zenixmc.block;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.World;

import zenixmc.ZenixMC;

/**
 * Serializable Wrapper around <code>org.bukkit.Location</code>
 * 
 * {see @link org.bukkit.Location}
 * 
 * @author james
 */
public class SerializableLocation implements Serializable {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 2860937094271158536L;
	
	private Location loc;
	
	public SerializableLocation(Location loc) {
		this.loc = loc;
	}
	
	public Location toLocation() {
		return loc;
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
		out.writeUTF(loc.getWorld().getName());
		out.writeDouble(loc.getX());
		out.writeDouble(loc.getY());
		out.writeDouble(loc.getZ());
		out.writeFloat(loc.getYaw());
		out.writeFloat(loc.getPitch());
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
		loc = new Location(ZenixMC.instance.getWorld(in.readUTF()), in.readDouble(), in.readDouble(), in.readDouble(), in.readFloat(), in.readFloat());
	}
}
