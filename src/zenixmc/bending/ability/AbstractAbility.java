package zenixmc.bending.ability;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.event.EventDispatcher;

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
	
	/**
	 * EventDispatcher to register animations.
	 */
	protected final EventDispatcher eventDispatcher;
	
	protected AbstractAbility(FakeBlockManager blockManager, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
		this.blockManager = blockManager;
		this.zenix = zenix;
		this.eventDispatcher = eventDispatcher;
	}
	
	@Override
	public void activate(BendingPlayerInterface bendingPlayer, boolean secondary) {
		if (!(using.containsKey(bendingPlayer))) {
			using.put(bendingPlayer, secondary);
			System.out.println("Activated!");
		}
	}

	@Override
	public void deactivate(BendingPlayerInterface bendingPlayer) {
		if (using.containsKey(bendingPlayer)) {
			bendingPlayer.setAbilityData(this, null);
			using.remove(bendingPlayer);
			System.out.println("De-Activated!");
		}
	}
	
	abstract protected void playDefaultSound(Location loc);
	
	abstract protected void updateData(AbilityData d, BendingPlayerInterface player);
	
	abstract protected void advanceLocation(AbilityData d);
	
	abstract protected void operate(AbilityData d, BendingPlayerInterface player);
	
	abstract protected void performChecks(AbilityData d);
	
	abstract protected void animate(AbilityData d);
}
