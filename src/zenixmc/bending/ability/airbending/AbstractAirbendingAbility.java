package zenixmc.bending.ability.airbending;

import org.bukkit.Location;
import org.bukkit.Sound;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.bending.Element;
import zenixmc.bending.ability.AbilityData;
import zenixmc.bending.ability.AbstractAbility;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.event.EventDispatcher;
import zenixmc.utils.MinecraftUtil;
import zenixmc.utils.particles.ParticleEffect;

/**
 * Base class for airbending abilities.
 * 
 * @author james
 */
public abstract class AbstractAirbendingAbility extends AbstractAbility {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -4683742822939963991L;
	
	protected AbstractAirbendingAbility(FakeBlockManager blockManager, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
		super(blockManager, zenix, eventDispatcher);
	}

	/**
     * @return {@code Element.AIR}
     */
    @Override
    public Element getRequiredElement() {
        return Element.Air;
    }
    
    @Override
    protected void playDefaultSound(Location loc) {
    	MinecraftUtil.playSound(loc, Sound.CREEPER_HISS, 50, 7);
    }
    
    protected void playParticles(Location loc, int amount) {
    	playParticles(loc, (float) Math.random(), (float) Math.random(), (float) Math.random(), amount);
    }
    
    protected void playParticles(Location center, float offsetX, float offsetY, float offsetZ, int amount) {
    	playParticles(center, offsetX, offsetY, offsetZ, 0, amount);
    }
    
    protected void playParticles(Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
    	for (int i = 0; i < amount; i++) {
    		ParticleEffect.CLOUD.display(center, offsetX, offsetY, offsetZ, 0, 1);
    	}
    }
}
