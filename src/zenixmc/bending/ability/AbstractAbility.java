package zenixmc.bending.ability;

import zenixmc.block.fake.FakeBlockManager;

public abstract class AbstractAbility implements AbilityInterface {
	
	/**
	 * Manager of fake block.
	 */
	protected final FakeBlockManager blockManager;
	
	protected AbstractAbility(FakeBlockManager blockManager) {
		this.blockManager = blockManager;
	}
}
