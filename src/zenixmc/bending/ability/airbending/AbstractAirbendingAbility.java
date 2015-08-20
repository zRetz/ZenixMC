package zenixmc.bending.ability.airbending;

import org.bukkit.Location;
import org.bukkit.Sound;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.Element;
import zenixmc.bending.ability.AbilityData;
import zenixmc.bending.ability.AbstractAbility;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.utils.MinecraftUtils;
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
	
	protected AbstractAirbendingAbility(FakeBlockManager blockManager, ZenixMCInterface zenix) {
		super(blockManager, zenix);
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
    	MinecraftUtils.playSound(loc, Sound.CREEPER_HISS, 10, 0);
    }
    
    @Override
    protected void playDefaultParticles(float ofsx, float ofsy, float ofsz, float speed, int amount, Location loc) {
    	ParticleEffect.CLOUD.display(ofsx, ofsy, ofsz, speed, amount, loc, 257D);
    }
    
    abstract protected void animate(AbilityData d);
}
