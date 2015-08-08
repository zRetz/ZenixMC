package zenixmc.block.chunk;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import zenixmc.utils.MinecraftUtils;

public class ChunkWrap {

	public Chunk chunk;
	
	public ChunkWrap(Chunk chunk) {
		this.chunk = chunk;
	}
	
	public Block[] getCornerBlocks() {
		
		Block[] result = new Block[4];
		
		result[0] = getCornerBlockOne();
		result[1] = getCornerBlockTwo();
		result[2] = getCornerBlockThree();
		result[3] = getCornerBlockFour();
		
		return result;
	}
	
	public Block getCornerBlockOne() {
		return MinecraftUtils.getHighestBlockAt(chunk, 0, 0);
	}
	
	public Block getCornerBlockTwo() {
		return MinecraftUtils.getHighestBlockAt(chunk, 0, 15);
	}
	
	public Block getCornerBlockThree() {
		return MinecraftUtils.getHighestBlockAt(chunk, 15, 0);
	}
	
	public Block getCornerBlockFour() {
		return MinecraftUtils.getHighestBlockAt(chunk, 15, 15);
	}
	
	public boolean isEqualTo(ChunkWrap wrap) {
		return wrap.chunk.getX() == chunk.getX() && wrap.chunk.getZ() == chunk.getZ();
	}
}
