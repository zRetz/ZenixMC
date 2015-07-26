package zenixmc.bending;

import zenixmc.utils.EEnumSet;

/**
 * A set of elements.
 */
public class ElementSet extends EEnumSet<Element> {
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
