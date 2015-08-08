package zenixmc.organization.clans;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Chunk;

import zenixmc.ZenixMC;
import zenixmc.ZenixMCInterface;
import zenixmc.block.chunk.ChunkWrap;
import zenixmc.organization.Influence;
import zenixmc.organization.Organization;
import zenixmc.persistance.CachedOrganizationRepository;
import zenixmc.utils.zenixjava.IntPair;

public class Territory implements Serializable {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 4272539668713381919L;
	
	/**
	 * The plugin.
	 */
	private ZenixMCInterface zenix;
	
	/**
	 * Territory Id.
	 */
	private String id;
	
	/**
	 * World the territory is in.
	 */
	private String world;
	
	/**
	 * The coordinates the territory is at.
	 */
	private IntPair coords;
	
	/**
	 * The owner of the territory.
	 */
	private transient Organization parent;
	
	public Territory(String id, Chunk c, Organization parent, ZenixMCInterface zenix) {
		this.id = id;
		this.world = c.getWorld().getName();
		this.coords = new IntPair(c.getX(), c.getZ());
		this.zenix = zenix;
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public String getWorld() {
		return world;
	}

	public IntPair getCoords() {
		return coords;
	}

	public void setZenix(ZenixMCInterface zenix) {
		this.zenix = zenix;
	}
	
	public ChunkWrap getChunk() {
		return new ChunkWrap(toChunk());
	}
	
	public Chunk toChunk() {
		return zenix.getWorld(world).getChunkAt(coords.getA(), coords.getB());
	}
	
	@Override
	public String toString() {
		return "X: " + coords.getA() + "; Z: " + coords.getB() + "; ";
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
		out.writeUTF(id);
		out.writeUTF(world);
		out.writeObject(coords);
		out.writeUTF(parent.getName());
		out.writeObject(parent.getClass());
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
		id = in.readUTF();
		world = in.readUTF();
		coords = (IntPair) in.readObject();
		try {
			Field f = ZenixMC.instance.getClass().getDeclaredField("cachedOrganizationRepository");
			f.setAccessible(true);
			CachedOrganizationRepository cor = (CachedOrganizationRepository) f.get(ZenixMC.instance);
			parent = cor.getOrganization(in.readUTF(), (Class<?>) in.readObject());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
