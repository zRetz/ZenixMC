package zenixmc.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Extendable enum set.
 *
 * @param <E>
 *     	The enum to make a set of.
 */
public class EEnumSet<E extends Enum<E>> implements Set<E>, Serializable {
	
    /**
     *	SerialVersionUID. 
	 */
	private static final long serialVersionUID = -2923315364755481245L;
	
	/**
     * Actual set that contains the values.
     */
    private EnumSet<E> values;

    /**
     * Creates the set.
     *
     * @param type
     *            The class of the {@link Enum} to store
     */
    protected EEnumSet(Class<E> type) {
        values = EnumSet.noneOf(type);
    }

    @Override
    public boolean add(E value) {
        return values.add(value);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return values.addAll(c);
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return values.containsAll(c);
    }

    @Override
    public boolean equals(Object o) {
        return values.equals(o);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return values.iterator();
    }

    @Override
    public boolean remove(Object value) {
        return values.remove(value);
    }

    @Override
    public boolean removeAll(Collection<?> values) {
        return values.removeAll(values);
    }

    @Override
    public boolean retainAll(Collection<?> values) {
        return values.retainAll(values);
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return values.toArray(a);
    }

    /**
     * @return A set containing all the enum values representing as their
     *         {@link Enum#name()} string.
     */
    public Set<String> nameSet() {
        Set<String> set = new HashSet<String>();

        for (E item : this) {
            set.add(item.name());
        }

        return set;
    }
}
