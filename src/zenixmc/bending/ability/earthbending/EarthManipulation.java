package zenixmc.bending.ability.earthbending;

import zenixmc.bending.BendingPlayerInterface;
import zenixmc.block.fake.FakeBlockManager;

public class EarthManipulation extends AbstractEarthbendingAbility {
	
	protected FakeBlockManager blockManager;
	
	public EarthManipulation(FakeBlockManager blockManager) {
        this.blockManager = blockManager;
    }

    protected enum EarthManipulationState {
        None, Selecting, Move, Throw;
    }

    /**
     * Per player information.
     */
    protected class EarthManipulationData {
        EarthManipulationState state = EarthManipulationState.None;
    }
	
	@Override
	public String getName() {
		return "basic_earth_manipulation";
	}

	@Override
	public String getDisplayName() {
		return "BasicEarthManipulation";
	}

	@Override
	public void activate(BendingPlayerInterface bendingPlayer, boolean secondary) {
		//allahu akbar
	}

	@Override
	public void deactivate(BendingPlayerInterface bendingPlayer) {
		//de-allahu akbar
	}

	@Override
	public boolean isPassive() {
		return false;
	}

}
