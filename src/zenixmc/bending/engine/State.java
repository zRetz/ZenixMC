package zenixmc.bending.engine;

public class State {
	public String name;
	
	public State(String name) {
		this.name = name;
		
		Engine.states.add(this);
	}

}
