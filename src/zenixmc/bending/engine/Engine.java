package zenixmc.bending.engine;

import java.util.ArrayList;
import java.util.List;

public interface Engine {
	
	List<State> states = new ArrayList<>();
	
	String getName();
	
	String[] getHelp();
	
	double getSpeed();
	
	State getStates();

}
