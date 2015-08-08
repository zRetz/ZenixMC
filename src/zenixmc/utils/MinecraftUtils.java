package zenixmc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import zenixmc.utils.particles.ParticleEffect;

public final class MinecraftUtils {
	
	public static final BlockFace[] NEIGHBOURS = new BlockFace[]{BlockFace.NORTH, BlockFace.WEST, BlockFace.EAST, BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN};
	
	public static final Material[] DANGEROUS_BLOCKS = new Material[]{Material.LAVA, Material.STATIONARY_LAVA, Material.FIRE};
	
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
			for (int y1 = minY; y1 <  maxY; y1++) {
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
		}else {
		}
		
		for (Block b : result) {
			ParticleEffect.FLAME.display(0, 0, 0, 0, 1, b.getLocation(), 30D);
		}
		
		return result;
	}
	
	public static boolean isSolid(Block b) {
		return b.getType().isSolid();
	}
	
	public static Block getHighestBlockAt(Chunk chunk, int x, int z) {
		
		Block b = chunk.getBlock(x, 256, z);
		
		while (!(isSolid(b))) {
			b = chunk.getBlock(x, b.getY() - 1, z);
		}
		
		return b;
	}
	
}
