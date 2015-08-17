package zenixmc.bending.ability.airbending;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import zenixmc.bending.BendingPlayerInterface;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.event.update.UpdateEvent;
import zenixmc.utils.particles.ParticleEffect;

public class AirManipulation extends AbstractAirbendingAbility {
	
	/**
	 * The players that are currently using the ability and if they are using the secondary feature or not.
	 */
	protected final Map<BendingPlayerInterface, Boolean> using = new HashMap<>();
	
	/**
	 * Instantiate.
	 * @param blockManager
	 * 		Block Manager.
	 */
	public AirManipulation(FakeBlockManager blockManager) {
        super(blockManager);
    }

    protected enum AirManipulationState {
        Blasting;
    }

    /**
     * Keep all per player data here so things like, the state of the ability, location, damage, etc.
     */
    protected class AirManipulationData {
    	
    	AirManipulationState state;
    	Location location;
    	
    	AirManipulationData(Location origin) {
	        state = AirManipulationState.Blasting;
	        location = origin;
    	}
    }
	
	@Override
	public String getName() {
		return "air_manipulation";
	}

	@Override
	public String getDisplayName() {
		return "AirManipulation";
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

	@Override
	public boolean isPassive() {
		return false;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onUpdate(UpdateEvent e) {
		for (Entry<BendingPlayerInterface, Boolean> entry : using.entrySet()) {
			BendingPlayerInterface player = entry.getKey();
			AirManipulationData data = (AirManipulationData) player.getAbilityData(this);
			
			if (data == null) {
				data = new AirManipulationData(player.getZenixUser().getEyeLocation());
				player.setAbilityData(this, data);
			}
			
			if (entry.getValue()) {
				deactivate(player);
				return;
			}else {
				switch(data.state) {
					case Blasting:
						data.location = data.location.add(player.getZenixUser().getEyeLocation().getDirection().multiply(1));
						ParticleEffect.CLOUD.display(0.3f, 0.3f, 0.3f, 0.1f, 2, data.location, 256D);
						
						if (data.location.getBlock().getType().isSolid()) {
							System.out.println(data.location);
							deactivate(player);
							return;
						}
				}
			}
		}
	}
	
}