package zenixmc.utils.zenixjava;

import zenixjava.collections.Pair;

public class IntPair extends Pair<Integer, Integer>{

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1980862240101508274L;

	public IntPair(Integer a, Integer b) {
		super(a, b);
	}
	
	public boolean isEqualTo(Integer c, Integer d) {
		return getA() == c && getB() == d;
	}
}
