package zenixmc.bending.ability.firebending;

import zenixmc.bending.Element;
import zenixmc.bending.ability.AbilityInterface;
import zenixmc.bending.ability.AbstractAbility;
import zenixmc.block.fake.FakeBlockManager;

/**
 * Base class for firebending abilities.
 * 
 * @author james
 */
public abstract class AbstractFirebendingAbility extends AbstractAbility {
	
	protected AbstractFirebendingAbility(FakeBlockManager blockManager) {
		super(blockManager);
	}

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -4683742822939963991L;

	/**
	 * @return {@code Element.AIR}
	 */
	@Override
	public Element getRequiredElement() {
		return Element.Fire;
	}

}
