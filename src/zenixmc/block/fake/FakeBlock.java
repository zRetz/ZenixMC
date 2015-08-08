package zenixmc.block.fake;

import org.bukkit.Material;

import zenixmc.block.BlockLocation;

/**
 * Fake block, a block only known to the clients.
 * 
 * @author james
 */
public class FakeBlock {

	/**
	 * Location of the block.
	 */
	private BlockLocation location;
	
	/**
     * The material of the block.
     */
	private Material material;
	
	/**
     * The timestamp of when the block should be removed.
     */
	private long timestamp;
	
	/**
     * Points to the original source, null if no other fake block source.
     */
	private FakeBlock parent;
	
	/**
     * Create a new fake block from a location.
     *
     * @param location
     *            The location of the new fake block.
     */
    FakeBlock(final BlockLocation location) {
        this(location, null, 0);
    }
	
	/**
     * Create a new fake block from a location, material and destination time.
     *
     * @param location
     *    	The location of the new fake block
     * @param material
     *    	The material of the new fake block.
     * @param timeout
     *    	The timestamp of removal of the new fake block.
     */
	public FakeBlock(BlockLocation location, Material material, long timestamp) {
		this.location = location;
		this.material = material;
		this.timestamp = timestamp;
	}
	
	/**
     * @return The material of the block.
     */
	public Material getMaterial() {
		return material;
	}
	
	/**
     * Sets the material of the block.
     *
     * @param material
     *     	The new material of the block.
     */
	public void setMaterial(Material mat) {
		this.material = mat;
	}
	
	/**
     * Sets the parent of the block.
     *
     * @param parent
     *    	The parent of the block.
     */
	public void setParent(FakeBlock parent) {
		this.parent = parent;
	}
	
	/**
	 * @return The fakeblocks parent.
	 */
	public FakeBlock getParent() {
		return parent;
	}
	
	/**
     * @return The location of the block
     */
	public BlockLocation getLocation() {
		return location;
	}
	
	/**
     * @return The timestamp of the removal of the block.
     */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
     * Delay the timestamp of removal by the specified amount.
     *
     * @param timeout
     *    	The amount of time to delay the removal.
     */
	public void addTime(long duration) {
		this.timestamp += duration;
	}

}
