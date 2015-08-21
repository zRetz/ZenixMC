package zenixmc.bending.ability.airbending;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.util.Vector;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.bending.ability.AbilityData;
import zenixmc.bending.ability.AnimationState;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.event.EventDispatcher;
import zenixmc.event.update.UpdateEvent;
import zenixmc.utils.DateUtil;
import zenixmc.utils.MinecraftUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.TimedTask;
import zenixmc.utils.VectorUtil;

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
	 * Range of ability.
	 */
	private float range;
	
	/**
	 * Instantiate.
	 * @param blockManager
	 * 		Block Manager.
	 */
	public AirManipulation(FakeBlockManager blockManager, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
        super(blockManager, zenix, eventDispatcher);
        speed = zenix.getSettings().airManipulationSpeed();
        knockback = zenix.getSettings().airManipulationKnockback();
        range = zenix.getSettings().airManipulationRange();
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
    protected class AirManipulationData extends AbilityData {
    	
    	/**
		 * SerialVersionUID.
		 */
		private static final long serialVersionUID = 484017001980666325L;
		
		/**
		 * State of ability.
		 */
		AirManipulationState state;
		
		/**
		 * State in animation.
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
    	
    	/**
    	 * Direction the ability needs to head to.
    	 */
    	Vector direction;   	
    	
    	AirManipulationData(boolean secondary) {
    		super(secondary);
    		state = this.secondary ? null : AirManipulationState.Blasting;
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
				data.origin = floc;
				data.location = data.origin.clone();
				data.direction = floc.getDirection().normalize();
				data.anstate = AnimationState.FREE_MOVE;
				player.setAbilityData(this, data);
			}
			
			if (data.isSecondary()) {
				deactivate(player);
				return;
			}else {
				switch(data.state) {
					case Blasting:
						performChecks(data);
						advanceLocation(data);
						animate(data);
						operate(data, player);
						updateData(data, player);
						
						if (data.isFinished()) {
							deactivate(player);
							return;
						}
				}
			}
		}
	}

	@Override
	protected void updateData(AbilityData d, BendingPlayerInterface player) {
		AirManipulationData data = (AirManipulationData) d;
		data.direction = player.getZenixUser().getEyeLocation().getDirection().normalize();
	}
	
	@Override
	protected void advanceLocation(AbilityData d) {
		AirManipulationData data = (AirManipulationData) d;
		data.location = data.location.add(data.direction.clone().multiply(speed));
	}
	
	@Override
	protected void operate(AbilityData d, BendingPlayerInterface player) {
		AirManipulationData data = (AirManipulationData) d;
		List<Entity> affected = MinecraftUtil.getEntities(2, data.location);
		for (Entity e : affected) {
			if (!(e.getUniqueId().compareTo(player.getZenixUser().getUniqueId()) == 0)) {
				MinecraftUtil.extinguish(e);
				e.setVelocity(data.location.getDirection().clone().add(new Vector(0, 0.17, 0)).multiply(knockback));
				data.setFinished(true);
				return;
			}
		}
	}

	@Override
	protected void performChecks(AbilityData d) {
		
		if (d.isFinished()) {
			return;
		}
		
		AirManipulationData data = (AirManipulationData) d;
		boolean set = false;
		AnimationState stset = AnimationState.FREE_MOVE;
		
		if (MinecraftUtil.isSolid(data.location) || MinecraftUtil.isLiquid(data.location)) {
			stset = AnimationState.BLOCK_HIT;
			set = true;
		}
		if (data.origin.distance(data.location) > range) {
			stset = AnimationState.FIZZLE;
			set = true;
		}
		
		data.anstate = stset;
		data.setFinished(set);
	}
	
	@Override
	protected void animate(AbilityData d) {
		AirManipulationData data = (AirManipulationData) d;
		switch (data.anstate) {
		case FREE_MOVE:
			this.playParticles(data.location, 6);
			this.playDefaultSound(data.location);
			break;
		case BLOCK_HIT:
			System.out.println("hit");
			String animId = new TimedTask(DateUtil.second * 2, 0L, 1L, zenix, eventDispatcher) {
				
				Location temp = MinecraftUtil.getHighestBlockAt(data.location.getChunk(), data.location.getBlockX(), data.location.getBlockZ()).getLocation().add(0, 1, 0);
				Vector v = new Vector(0, 0, 0);
				float j = 0.07f;
				
				@Override
				public void taskRun() {
					if (j < 2) {
						j += 0.5;
					}else {
						this.taskCancel();
					}
					for (int i = 0; i < 360; i += 360/20) {
						v.setX(j * Math.cos(Math.toRadians(i)));
						v.setY(j/3.5);
						v.setZ(j * Math.sin(Math.toRadians(i)));
						temp.add(v);
						playParticles(temp, 0.1f, 0, 0.1f, 4);
						playDefaultSound(temp);
						temp.subtract(v);
					}
				}
				
			}.taskStart();
			break;
		default:
			break;
		}
	}
	
}