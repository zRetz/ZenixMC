package zenixmc.organization;

import zenixjava.collections.pair.LowHigh;

/**
 * LowHigh wrapper for influence values.
 *
 * @author james
 */
public class Influence extends LowHigh {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 343015437699229617L;

	/**
	 * Instantiate.
	 * @param a
	 * 		The low value.
	 * @param b
	 * 		The high value.
	 */
	public Influence(Integer a, Integer b) {
		super(a, b);
	}
	
	/**
	 * Sets influence.
	 * @param i
	 * 		The value to set.
	 */
	public void setInfluence(int i) {
		setA(new Integer(i));
	}
	
	/**
	 * Sets maxInfluence.
	 * @param i
	 * 		The value to set.
	 */
	public void setMaxInfluence(int i) {
		setB(new Integer(i));
	}
	
	/**
	 * @return influence value.
	 */
	public int getInfluence() {
		return getA().intValue();
	}
	
	/**
	 * @return maxInfluence value.
	 */
	public int getMaxInfluence() {
		return getB().intValue();
	}
}
