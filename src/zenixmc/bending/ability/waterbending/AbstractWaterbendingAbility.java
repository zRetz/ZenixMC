package zenixmc.bending.ability.waterbending;

import zenixmc.bending.Element;
import zenixmc.bending.ability.AbilityInterface;

/**
 * Base class for waterbending abilities.
 *
 */

public abstract class AbstractWaterbendingAbility implements AbilityInterface {
	
    /**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -3093182425112527315L;

	/**
     * @return {@code Element.WATER}
     */
    @Override
    public Element getRequiredElement() {
        return Element.Water;
    }
}

