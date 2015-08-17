package zenixmc.bending.ability.airbending;

import zenixmc.bending.Element;
import zenixmc.bending.ability.AbstractAbility;
import zenixmc.block.fake.FakeBlockManager;

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
	
	protected AbstractAirbendingAbility(FakeBlockManager blockManager) {
		super(blockManager);
	}

	/**
     * @return {@code Element.AIR}
     */
    @Override
    public Element getRequiredElement() {
        return Element.Air;
    }
}
