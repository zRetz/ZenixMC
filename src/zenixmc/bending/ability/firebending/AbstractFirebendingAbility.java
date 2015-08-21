package zenixmc.bending.ability.firebending;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.Element;
import zenixmc.bending.ability.AbstractAbility;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.event.EventDispatcher;

/**
 * Base class for firebending abilities.
 * 
 * @author james
 */
public abstract class AbstractFirebendingAbility extends AbstractAbility {
	
	protected AbstractFirebendingAbility(FakeBlockManager blockManager, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
		super(blockManager, zenix, eventDispatcher);
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
	
	abstract protected void playParticles();

}
