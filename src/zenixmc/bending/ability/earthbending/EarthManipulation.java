package zenixmc.bending.ability.earthbending;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.bending.ability.AbilityData;
import zenixmc.block.fake.FakeBlockManager;
import zenixmc.event.EventDispatcher;

public class EarthManipulation extends AbstractEarthbendingAbility {
	
	
	public EarthManipulation(FakeBlockManager blockManager, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
        super(blockManager, zenix, eventDispatcher);
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

	@Override
	protected void updateData(AbilityData d, BendingPlayerInterface player) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void advanceLocation(AbilityData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void performChecks(AbilityData d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void animate(AbilityData d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void operate(AbilityData d, BendingPlayerInterface player) {
		// TODO Auto-generated method stub
		
	}

}
