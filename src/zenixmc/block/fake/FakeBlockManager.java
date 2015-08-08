package zenixmc.block.fake;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import zenixmc.bending.BendingPlayerInterface;
import zenixmc.block.BlockLocation;
import zenixmc.block.BlockMap;
import zenixmc.block.RayIterator;
import zenixmc.user.ZenixUserInterface;

/**
 * Manages sending fake block updates to the client.
 * 
 * @author james
 */
public class FakeBlockManager {
	
	/**
     * The amount of water source blocks required to be around for an fake empty
     * spot to turn into still water.
     */
    public static final int MIN_STATICWATER_TURN = 2;

    /**
     * The amount of water source blocks required to be around for an fake empty
     * spot to turn into moving water.
     */
    public static final int MIN_WATER_TURN = 1;

    /**
     * The amount of lava source blocks required to be around for an fake empty
     * spot to turn into lava.
     */
    public static final int MIN_LAVA_TURN = 3;

    /**
     * The radius in which players receive fake block updates.
     */
    public static final int SEND_BLOCK_RADIUS = 50;

    /**
     * The fake blocks that currently are alive.
     */
    private BlockMap<FakeBlock> fakeBlocks = new BlockMap<FakeBlock>();
	
    /**
     * Finds the material on a location.
     *
     * @param location
     *            The location to check for
     * @return The material at the specified location
     */
    public Material findMaterial(final BlockLocation location) {
        final FakeBlock fakeBlock = fakeBlocks.get(location);

        if (fakeBlock == null) {
            return location.getBlock().getType();
        }

        return fakeBlock.getMaterial();
    }

    /**
     * Return an existing fake block on a location or create a new one.
     *
     * @param location
     *            The location of the fake block.
     * @param create
     *            Whether to create a fake block if not found.
     * @return The fake block
     */
    protected FakeBlock getFakeBlock(final BlockLocation location,
            final boolean create) {
        FakeBlock fakeBlock = fakeBlocks.get(location);

        if (create && fakeBlock == null) {
            fakeBlock = new FakeBlock(location);
            fakeBlocks.put(fakeBlock.getLocation(), fakeBlock);
        }

        return fakeBlock;
    }

    /**
     * Find out what material should be used to fill in the gap after removing a
     * fake block.
     *
     * @param block
     *            The block that will be (fakely) removed, and has to be filled
     *            in.
     * @param hasParent
     *            Whether the hole was previously a fake block.
     * @return The material the hole should be filled in with
     */
    protected Material findFillMaterial(final Block block,
            final boolean hasParent) {
        final BlockFace[] faces = {
                BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH,
                BlockFace.SOUTH
        };

        int lavaSources = 0, waterSources = 0;

        for (final BlockFace face : faces) {
            final Material material = findMaterial(new BlockLocation(
                    block.getRelative(face)));

            switch (material) {
            case LAVA:
            case STATIONARY_LAVA:
                lavaSources++;
                break;
            case WATER:
            case STATIONARY_WATER:
            case ICE:
                waterSources++;
                break;
            default:
                break;
            }
        }
        if (waterSources > 0 && lavaSources > 0) {
            return Material.COBBLESTONE;
        } else if (waterSources >= MIN_STATICWATER_TURN) {
            return Material.STATIONARY_WATER;
        } else if (waterSources >= MIN_WATER_TURN) {
            return Material.WATER;
        } else if (lavaSources >= MIN_LAVA_TURN) {
            return Material.LAVA;
        }

        return Material.AIR;
    }

    /**
     * Find out what material a bent block turns into after being bent.
     *
     * @param source
     *            The source material, before being bent
     * @return The material it turns into
     */
    protected Material findBentMaterial(final Material source) {
        switch (source) {
        case STONE:
            return Material.COBBLESTONE;
        case WATER:
            return Material.STATIONARY_WATER;
        case SAND:
            return Material.SANDSTONE;
        default:
            return source;
        }
    }

    /**
     * Moves a block of earth to the specified location.
     *
     * @param source
     *   	The source block to move from
     * @param target
     *     	The target block to move to
     * @param timeout
     *     	The amount of time this should stay this way
     */
    public void moveEarthBlock(final Block source, final Block target,
            final int timeout) {
        final BlockLocation sourceLocation = new BlockLocation(source);
        final BlockLocation targetLocation = new BlockLocation(target);

        final Block sourceBlock = sourceLocation.getBlock();
        final FakeBlock sourceFake = getFakeBlock(sourceLocation, true);

        sourceFake.addTime(timeout);

        sourceFake.setMaterial(findFillMaterial(sourceBlock,
                sourceFake.getParent() != null));

        // TODO: Allow recursive parents?
        if (sourceFake.getParent() != null) {
            sourceFake.getParent().addTime(timeout);
        }

        final FakeBlock targetFake = getFakeBlock(targetLocation, true);

        targetFake.addTime(timeout);
        targetFake.setParent(sourceFake);

        targetFake.setMaterial(findBentMaterial(sourceBlock.getType()));

        updateFake(sourceFake);
        updateFake(targetFake);
    }

    /**
     * Update a fake block, schedules removal and sends itself to nearby
     * players.
     *
     * @param fake
     *    	The fake block to update
     */
    void updateFake(final FakeBlock fake) {
        final BlockLocation location = fake.getLocation();
        final World world = location.getWorld();

        for (final Player player : world.getPlayers()) {
            if (location.distanceSquared(player.getLocation()) < SEND_BLOCK_RADIUS
                    * SEND_BLOCK_RADIUS) {
                player.sendBlockChange(location.getBlock().getLocation(),
                        fake.getMaterial(), (byte) 0);
            }
        }
    }

    /**
     * Returns the block the player is looking at.
     *
     * @param player
     *            The player to check for.
     * @return The block that the player is looking at.
     */
    public FakeBlock getBlockLookingAt(BendingPlayerInterface player) {
        final ZenixUserInterface p = player.getZenixUser();
        final Location eyeLocation = p.getEyeLocation();
        final Vector position = eyeLocation.toVector();
        final Vector direction = eyeLocation.getDirection();

        final RayIterator it = new RayIterator(10, position, direction);
        final Location blockLocation = eyeLocation.clone();

        while (it.hasNext()) {
            final Vector blockVector = it.next();
            blockLocation.setX(blockVector.getX());
            blockLocation.setY(blockVector.getY());
            blockLocation.setZ(blockVector.getZ());
            final Block block = blockLocation.getBlock();
            block.setType(Material.STONE);
        }

        return null;
    }
}
