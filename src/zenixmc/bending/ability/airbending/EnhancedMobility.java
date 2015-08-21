package zenixmc.bending.ability.airbending;

import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.bending.ability.AbilityData;
import zenixmc.bending.ability.AnimationState;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.event.EventDispatcher;
import zenixmc.event.update.UpdateEvent;

public class EnhancedMobility extends AbstractAirbendingAbility{

	protected EnhancedMobility(FakeBlockManager blockManager, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
		super(blockManager, zenix, eventDispatcher);
	}
	
	/**
	 * State of Air Manipulation.
	 * @author james
	 */
    private enum EnhancedMobilityState {
        Boosting, Shield;
    }
    
    protected class EnhancedMobilityData extends AbilityData {

		/**
		 * SerialVersionUID.
		 */
		private static final long serialVersionUID = 6176568350969234203L;
		
		/**
		 * State of ability.
		 */
		EnhancedMobilityState state;
		
		/**
		 * State of animation.
		 */
		AnimationState anstate;
		
		/**
		 * Location of ability.
		 */
		Location location;
		
		/**
		 * Origin location of ability.
		 */
		Location origin;
		
		protected EnhancedMobilityData(boolean secondary) {
			super(secondary);
			state = this.secondary ? EnhancedMobilityState.Shield : EnhancedMobilityState.Boosting; 
		}
    	
    }

	@Override
	public String getName() {
		return "enhanced_mobility";
	}

	@Override
	public String getDisplayName() {
		return "EnhancedMobility";
	}

	@Override
	public boolean isPassive() {
		return false;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onUpdate(UpdateEvent e) {
		for (Entry<BendingPlayerInterface, Boolean> entry : using.entrySet()) {
			BendingPlayerInterface player = entry.getKey();
			EnhancedMobilityData data = (EnhancedMobilityData) player.getAbilityData(this);
			Location floc = player.getZenixUser().getEyeLocation();
		}
	}

	@Override
	protected void updateData(AbilityData d, BendingPlayerInterface player) {
		
	}

	@Override
	protected void advanceLocation(AbilityData d) {
		
	}

	@Override
	protected void operate(AbilityData d, BendingPlayerInterface player) {
		
	}

	@Override
	protected void performChecks(AbilityData d) {

	}

	@Override
	protected void animate(AbilityData d) {
		
	}

}
