package zenixmc.bending.ability.earthbending;

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

	protected AbstractEarthbendingAbility(FakeBlockManager blockManager) {
		super(blockManager);
	}
	
	/**
     * @return {@code Element.AIR}
     */
    @Override
    public Element getRequiredElement() {
        return Element.Earth;
    }
}
