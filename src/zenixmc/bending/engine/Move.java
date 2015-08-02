package zenixmc.bending.engine;

import java.util.ArrayList;
import java.util.List;

public class Move implements Engine {
	public List<State> states = new ArrayList<>();
	public String name;
	public String[] desc;
	
	public Move(String name, String[] desc) {
		this.name = name;
		this.desc = desc;
		
		states.add(new State(name));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getHelp() {
		return desc;
	}

	@Override
	public double getSpeed() {
		return 0;
	}

	@Override
	public State getStates() {
		return null;
	}

}
