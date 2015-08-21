package zenixmc.bending.ability;

import java.io.Serializable;

/**
 * Dummy interface for ability data.
 * 
 * @author james
 */
public abstract class AbilityData implements Serializable {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 2415907554626694689L;
	
	/**
	 * Whether it is a secondary ability or not.
	 */
	protected boolean secondary = false;
	
	/**
	 * Whether the ability has finished.
	 */
	protected boolean finished = false;
	
	/**
	 * Instantiate.
	 * @param secondary
	 * 		Whether the ability is in secondary mode.
	 */
	protected AbilityData(boolean secondary) {
		this.secondary = secondary;
	}
	
	public void setSecondary(boolean set) {
		this.secondary = set;
	}
	
	public void setFinished(boolean set) {
		this.finished = set;
	}
	
	public boolean isSecondary() {
		return secondary;
	}
	
	public boolean isFinished() {
		return finished;
	}
}
