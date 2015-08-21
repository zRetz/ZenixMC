package zenixmc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import zenixmc.user.ZenixUser;
import zenixmc.utils.particles.ParticleEffect;

/**
 * Utils Class for Minecraft related situations.
 * 
 * @author james
 */
public final class MinecraftUtil {

	public static final BlockFace[] NEIGHBOURS = new BlockFace[] { BlockFace.NORTH, BlockFace.WEST, BlockFace.EAST,
			BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN };

	public static final BlockFace[] Y = new BlockFace[] { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.SOUTH_EAST,
			BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.NORTH_WEST, BlockFace.EAST, BlockFace.WEST };

	public static final BlockFace[] XZ = new BlockFace[] { BlockFace.UP, BlockFace.DOWN };

	public static final Material[] DANGEROUS_BLOCKS = new Material[] { Material.LAVA, Material.STATIONARY_LAVA,
			Material.FIRE };

	public static boolean isSolid(Location loc) {
		return isSolid(loc.getBlock());
	}

	public static boolean isSolid(Block b) {
		return b.getType().isSolid();
	}

	public static boolean isLiquid(Location loc) {
		return isLiquid(loc.getBlock());
	}

	public static boolean isLiquid(Block b) {
		return b.getType() == Material.WATER || b.getType() == Material.LAVA || b.getType() == Material.STATIONARY_WATER
				|| b.getType() == Material.STATIONARY_LAVA;
	}

	public static boolean isSafeLocation(Location loc) {
		return isSafeBlock(loc.getBlock());
	}

	public static boolean isSafeBlock(Block block) {

		if (Arrays.asList(DANGEROUS_BLOCKS).contains(block.getType())) {
			return false;
		}

		Block blockBelow = block.getLocation().subtract(0, 1, 0).getBlock();

		if (Arrays.asList(DANGEROUS_BLOCKS).contains(blockBelow.getType()) || blockBelow.getType() == Material.AIR) {
			return false;
		}

		return true;
	}

	public static Location getSafeLocation(Location loc) {

		if (isSafeLocation(loc)) {
			return loc;
		}

		List<Block> cube = new ArrayList<>();

		int i = 0;
		boolean done = false;

		Location result = loc;

		while (!done) {

			i++;

			cube = getCube(loc.getBlock(), i, true);

			for (Block b : cube) {
				if (isSafeBlock(b)) {
					done = true;
					result = (Location) b.getLocation();
				}
			}
		}

		return result;
	}

	public static boolean isMaterial(String mat) {
		return Material.matchMaterial(mat) != null;
	}

	public static List<Material> stringToMaterialList(List<String> input) {

		List<Material> result = new ArrayList<>();

		for (String s : input) {
			if (isMaterial(s)) {
				result.add(Material.matchMaterial(s));
			}
		}

		return result;
	}

	public static List<Block> getCube(Block start, int range, boolean hollow) {

		World world = start.getWorld();

		List<Block> result = new ArrayList<>();

		int x = start.getX();
		int y = start.getY();
		int z = start.getZ();

		int minX = x - range;
		int minY = y - range;
		int minZ = z - range;

		int maxX = x + range;
		int maxY = y + range;
		int maxZ = z + range;

		if (hollow) {
			for (int y1 = minY; y1 < maxY; y1++) {
				for (int x1 = minX; x1 < maxX; x1++) {
					result.add(world.getBlockAt(x1, y1, minZ));
				}
				for (int x2 = maxX; x2 > minX; x2--) {
					result.add(world.getBlockAt(x2, y1, maxZ));
				}
				for (int z1 = minZ; z1 < maxZ; z1++) {
					result.add(world.getBlockAt(minX, y1, z1));
				}
				for (int z2 = maxZ; z2 > minZ; z2--) {
					result.add(world.getBlockAt(maxX, y1, z2));
				}
			}
		} else {
		}

		for (Block b : result) {
			ParticleEffect.FLAME.display(0, 0, 0, 0, 1, b.getLocation(), 30D);
		}

		return result;
	}

	public static Block getHighestBlockAt(Chunk chunk, int x, int z) {

		Block b = chunk.getBlock(x, 256, z);

		while (!(isSolid(b)) && !(isLiquid(b))) {
			b = chunk.getBlock(x, b.getY() - 1, z);
		}

		return b;
	}

	public static Location getTargetedLocation(ZenixUser zui, float range) {

		Location origin = zui.getEyeLocation();
		Vector direction = origin.getDirection().normalize();

		Location position = origin.clone();

		boolean toofar = false;

		do {
			toofar = origin.distance(position) > range;

			if (isSolid(position) || isLiquid(position)) {
				toofar = true;
			}

			position = position.add(direction.multiply(1));
		} while (!toofar);

		return position;
	}

	public static List<Entity> getEntities(float range, Location origin) {

		List<Entity> result = new ArrayList<>();

		for (Entity e : origin.getWorld().getEntities()) {
			if (e.getLocation().distance(origin) <= range) {
				result.add(e);
			}
		}

		return result;
	}

	public static List<Entity> getSpecificEntities(float range, Location origin, Class<?>... type) {

		List<Entity> result = new ArrayList<>();

		for (Entity e : origin.getWorld().getEntitiesByClasses(type)) {
			if (e.getLocation().distance(origin) <= range) {
				result.add(e);
			}
		}

		return result;
	}

	/**
	 * @author Orion304
	 */
	public static BlockFace getCardinalDirection(Vector vector) {
		BlockFace[] faces = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST,
				BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST, BlockFace.UP,
				BlockFace.DOWN };
		Vector n, ne, e, se, s, sw, w, nw, u, d;
		w = new Vector(-1, 0, 0);
		n = new Vector(0, 0, -1);
		u = new Vector(0, 1, 0);
		d = new Vector(0, -1, 0);
		s = n.clone().multiply(-1);
		e = w.clone().multiply(-1);
		ne = n.clone().add(e.clone()).normalize();
		se = s.clone().add(e.clone()).normalize();
		nw = n.clone().add(w.clone()).normalize();
		sw = s.clone().add(w.clone()).normalize();

		Vector[] vectors = { n, ne, e, se, s, sw, w, nw, u, d };

		double comp = 0;
		int besti = 0;
		for (int i = 0; i < vectors.length; i++) {
			double dot = vector.dot(vectors[i]);
			if (dot > comp) {
				comp = dot;
				besti = i;
			}
		}

		return faces[besti];
	}
	
	public static boolean isYFace(BlockFace f) {
		return Arrays.asList(Y).contains(f);
	}
	
	public static boolean isXZFace(BlockFace f) {
		return Arrays.asList(XZ).contains(f);
	}

	public static void playSound(Location loc, Sound sound, float volume, float pitch) {
		loc.getWorld().playSound(loc, sound, volume, pitch);
	}

	public static boolean isOnFire(Entity e) {
		return e.getFireTicks() > 0;
	}

	public static void extinguish(Entity e) {
		if (isOnFire(e)) {
			MinecraftUtil.playSound(e.getLocation(), Sound.FUSE, 50, 0);
			e.setFireTicks(0);
		}
	}
}
