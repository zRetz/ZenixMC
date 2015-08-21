package zenixmc.bending.ability.earthbending;

import org.bukkit.Location;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.Element;
import zenixmc.bending.ability.AbstractAbility;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.event.EventDispatcher;

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

	protected AbstractEarthbendingAbility(FakeBlockManager blockManager, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
		super(blockManager, zenix, eventDispatcher);
	}
	
	@Override
	protected void playDefaultSound(Location loc) {
		
	}
	
	/**
     * @return {@code Element.AIR}
     */
    @Override
    public Element getRequiredElement() {
        return Element.Earth;
    }
}
