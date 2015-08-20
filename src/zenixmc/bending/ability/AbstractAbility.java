package zenixmc.bending.ability;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.block.fake.FakeBlockManager;

public abstract class AbstractAbility implements AbilityInterface {
	
	/**
	 * The players that are currently using the ability and if they are using the secondary feature or not.
	 */
	protected final Map<BendingPlayerInterface, Boolean> using = new HashMap<>();
	
	/**
	 * Manager of fake block.
	 */
	protected final FakeBlockManager blockManager;
	
	/**
	 * The plugin.
	 */
	protected final ZenixMCInterface zenix;
	
	protected AbstractAbility(FakeBlockManager blockManager, ZenixMCInterface zenix) {
		this.blockManager = blockManager;
		this.zenix = zenix;
	}
	
	abstract protected void playDefaultSound(Location loc);
	
	abstract protected void playDefaultParticles(float ofsx, float ofsy, float ofsz, float speed, int amount, Location loc);
	
	abstract protected void updateData(AbilityData d, BendingPlayerInterface player);
	
	abstract protected void advanceLocation(AbilityData d);
	
	abstract protected boolean performChecks(AbilityData d);
}
