package zenixmc.bending;

/**
 * Elements in the avatar universe.
 */
public enum Element {

    Air, Water, Earth, Fire, None;

    /**
     * Find an element by case independent name.
     *
     * @param name
     *            Name of the element to find.
     * @return The element or null.
     */
    public static Element getType(final String name) {
        for (final Element element : Element.values()) {
            if (element.toString().equalsIgnoreCase(name)) {
                return element;
            }
        }
        return null;
    }

    /**
     * @return Returns a set containing all the names of the {@code enum}
     *         members
     */
    public static Set<String> nameSet() {
        final Set<String> set = new HashSet<String>();

        for (final Element e : Element.values()) {
            set.add(e.name());
        }

        return set;
    }
}