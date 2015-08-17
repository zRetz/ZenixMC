package zenixmc.bending.ability.waterbending;

import zenixmc.bending.Element;
import zenixmc.bending.ability.AbilityInterface;
import zenixmc.bending.ability.AbstractAbility;
import zenixmc.block.fake.FakeBlockManager;

/**
 * Base class for waterbending abilities.
 *
 */

public abstract class AbstractWaterbendingAbility extends AbstractAbility {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -3093182425112527315L;

	protected AbstractWaterbendingAbility(FakeBlockManager blockManager) {
		super(blockManager);
	}
	
	/**
     * @return {@code Element.WATER}
     */
    @Override
    public Element getRequiredElement() {
        return Element.Water;
    }
}

