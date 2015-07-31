package zenixmc.bending.ability.airbending;

import zenixmc.bending.Element;
import zenixmc.bending.ability.AbilityInterface;

/**
 * Base class for airbending abilities.
 * 
 * @author james
 */
public abstract class AbstractAirbendingAbility implements AbilityInterface {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -4683742822939963991L;

	/**
     * @return {@code Element.AIR}
     */
    @Override
    public Element getRequiredElement() {
        return Element.Air;
    }
}
