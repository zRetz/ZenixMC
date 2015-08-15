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
	
	protected FakeBlockManager blockManager;
	
	protected final Map<BendingPlayerInterface, Boolean> using = new HashMap<>();
	
	public AirManipulation(FakeBlockManager blockManager) {
        this.blockManager = blockManager;
    }

    protected enum AirManipulationState {
        Blasting;
    }

    /**
     * Per player information.
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
			using.remove(bendingPlayer);
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
			}else {
				switch(data.state) {
					case Blasting:
						data.location = data.location.add(player.getZenixUser().getEyeLocation().getDirection().multiply(1));
						ParticleEffect.CLOUD.display(0.3f, 0.3f, 0.3f, 0.1f, 2, data.location, 256D);
						
						if (data.location.getBlock().getType().isSolid()) {
							deactivate(player);
							return;
						}
				}
			}
		}
	}
	
}