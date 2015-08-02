package zenixmc.bending.ability.earthbending;

import zenixmc.bending.Element;
import zenixmc.bending.ability.AbilityInterface;

/**
 * Base class for earthbending abilities.
 * 
 * @author jayms
 */
public abstract class AbstractEarthbendingAbility implements AbilityInterface {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -4683742822939963991L;

	/**
     * @return {@code Element.AIR}
     */
    @Override
    public Element getRequiredElement() {
        return Element.Earth;
    }
}
