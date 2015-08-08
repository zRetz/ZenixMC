package zenixmc.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * The location of a block in a world.
 * 
 * @author james
 */
public class BlockLocation {
    /**
     * The x, y and z coordinate.
     */
    public final int x, y, z;

    /**
     * The name of the world the block is in.
     */
    public final String world;

    /**
     * Creates a new block location from a block in the world.
     *
     * @param block
     *            The block in the world
     */
    public BlockLocation(final Block block) {
        this(block.getWorld(), block.getX(), block.getY(), block.getZ());
    }

    /**
     * Creates a new block location from a world and coordinates.
     *
     * @param world
     *            The world the block is in.
     * @param x
     *            The x coordinate of the block.
     * @param y
     *            The y coordinate of the block.
     * @param z
     *            The z coordinate of the block.
     */
    public BlockLocation(final World world, final int x, final int y,
            final int z) {
        this(world == null ? null : world.getName(), x, y, z);
    }

    /**
     * Creates a new block location from a world name and coordinates.
     *
     * @param world
     *            The name of the world the block is in.
     * @param x
     *            The x coordinate of the block.
     * @param y
     *            The y coordinate of the block.
     * @param z
     *            The z coordinate of the block.
     */
    public BlockLocation(final String world, final int x, final int y,
            final int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return The world the block is in or null
     */
    public World getWorld() {
        return Bukkit.getServer().getWorld(world);
    }

    /**
     * @return The block at this location, or null when the location is invalid
     */
    public Block getBlock() {
        World world = getWorld();
        return world == null ? null : world.getBlockAt(x, y, z);
    }

    /**
     * Return the squared distance between two locations.
     *
     * @param location
     *            The location to check against
     * @return The distance squared
     */
    public double distanceSquared(final Location location) {
        double dx = location.getX() - x;
        double dy = location.getY() - y;
        double dz = location.getZ() - z;

        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * @return A string representation of the class in the format
     *         "world(x;y;z)".
     */
    @Override
    public String toString() {
        return this.world + "(" + this.x + ";" + this.y + ";" + this.z + ")";
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof BlockLocation) {
            BlockLocation otherBlock = (BlockLocation) other;
            return this.x == otherBlock.x
                    && this.y == otherBlock.y
                    && this.z == otherBlock.z
                    && (otherBlock.world != null ? otherBlock.world
                            .equals(this.world) : this.world == null);
        } else {
            return false;
        }
    }

    /**
     * Optimised hash code, taken from BKCommonlib.
     *
     * @return A hash representation of this object
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.world.hashCode();
        hash = 53 * hash + (this.x ^ (this.x >> 16));
        hash = 53 * hash + (this.y ^ (this.y >> 16));
        hash = 53 * hash + (this.z ^ (this.z >> 16));
        return hash;

    }
}
