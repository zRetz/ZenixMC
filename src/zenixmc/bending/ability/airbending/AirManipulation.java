package zenixmc.bending.ability.airbending;

import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.util.Vector;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.bending.ability.AbilityData;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.event.update.UpdateEvent;
import zenixmc.utils.MinecraftUtils;
import zenixmc.utils.particles.ParticleEffect;

public class AirManipulation extends AbstractAirbendingAbility {
	
	/**
	 * Speed factor for ability.
	 */
	private float speed;
	
	/**
	 * Momentum of the entities when colliding.
	 */
	private float knockback;
	
	/**
	 * Instantiate.
	 * @param blockManager
	 * 		Block Manager.
	 */
	public AirManipulation(FakeBlockManager blockManager, ZenixMCInterface zenix) {
        super(blockManager, zenix);
        speed = zenix.getSettings().airManipulationSpeed();
        knockback = zenix.getSettings().airManipulationKnockback();
    }
	
	/**
	 * State of Air Manipulation.
	 * @author james
	 */
    private enum AirManipulationState {
        Blasting;
    }

    /**
     * Keep all per player data here so things like, the state of the ability, location, damage, etc.
     */
    protected class AirManipulationData implements AbilityData {
    	
    	/**
		 * SerialVersionUID.
		 */
		private static final long serialVersionUID = 484017001980666325L;
		
		/**
		 * Whether it is a secondary ability or not.
		 */
		boolean secondary;
		
		/**
		 * State of ability.
		 */
		AirManipulationState state;
		
		/**
		 * Location of ability.
		 */
    	Location location;
    	
    	/**
    	 * Direction the ability needs to head to.
    	 */
    	Vector direction;
    	
    	
    	
    	AirManipulationData(boolean secondary) {
    		this.secondary = secondary;
    		state = this.secondary ? null : AirManipulationState.Blasting;
    	}

		void setState(AirManipulationState state) {
			this.state = state;
		}

		void setLocation(Location location) {
			this.location = location;
		}			

		void setDirection(Vector direction) {
			this.direction = direction;
		}
    	
		public boolean isSecondary() {
			return secondary;
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
			Location floc = player.getZenixUser().getEyeLocation();
			
			if (data == null) {
				data = new AirManipulationData(entry.getValue());
				data.setLocation(floc);
				data.setDirection(floc.getDirection().normalize());
				player.setAbilityData(this, data);
			}
			
			if (data.isSecondary()) {
				deactivate(player);
				return;
			}else {
				switch(data.state) {
					case Blasting:
						if (!(performChecks(data))) {
							deactivate(player);
							return;
						}
						advanceLocation(data);
						updateData(data, player);
				}
			}
		}
	}

	@Override
	protected void updateData(AbilityData d, BendingPlayerInterface player) {
		AirManipulationData data = (AirManipulationData) d;
		data.setDirection(player.getZenixUser().getEyeLocation().getDirection().normalize());
	}
	
	@Override
	protected void advanceLocation(AbilityData d) {
		AirManipulationData data = (AirManipulationData) d;
		data.setLocation(data.location.add(data.direction.clone().multiply(speed)));
		playDefaultSound(data.location);
	}

	@Override
	protected boolean performChecks(AbilityData d) {
		AirManipulationData data = (AirManipulationData) d;
		
		if (MinecraftUtils.isSolid(data.location))
			return false;
		
		return true;
	}
	
	@Override
	protected void animate(AbilityData d) {
		AirManipulationData data = (AirManipulationData) d;
		this.playDefaultParticles(0.3f, 0.3f, 0.3f, 0.1f, 6, data.location);
		this.playDefaultSound(data.location);
	}
}