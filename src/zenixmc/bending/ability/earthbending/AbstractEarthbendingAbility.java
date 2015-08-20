package zenixmc.bending.ability.earthbending;

import org.bukkit.Location;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.Element;
import zenixmc.bending.ability.AbstractAbility;
import zenixmc.block.fake.FakeBlockManager;

/**
 * Base class for earthbending abilities.
 * 
 * @author james
 */
public abstract class AbstractEarthbendingAbility extends AbstractAbility {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -4683742822939963991L;

	protected AbstractEarthbendingAbility(FakeBlockManager blockManager, ZenixMCInterface zenix) {
		super(blockManager, zenix);
	}
	
	@Override
	protected void playDefaultSound(Location loc) {
		
	}
	
	@Override
    protected void playDefaultParticles(float ofsx, float ofsy, float ofsz, float speed, int amount, Location loc) {
		throw new UnsupportedOperationException("Earthbending doesn't have particles yet.");
    }
	
	/**
     * @return {@code Element.AIR}
     */
    @Override
    public Element getRequiredElement() {
        return Element.Earth;
    }
}
