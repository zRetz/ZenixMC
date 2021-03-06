package zenixmc.bending;

import zenixmc.utils.EEnumSet;

/**
 * A set of elements.
 */
public class ElementSet extends EEnumSet<Element> {
    /**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 5922342816713933035L;

	/**
     * Creates a new set.
     */
    public ElementSet() {
        super(Element.class);
    }

    /**
     * @return Whether this set would belong to an avatar
     */
    public boolean isAvatar() {
        return size() > 1;
    }

}
