package zenixmc.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Set to allow only one implementation each of an interface.
 * @author james
 */
public class InterfaceSet<E> implements Set<E> {

	/**
	 * Values.
	 */
	protected Set<E> values;
	
	/**
	 * Implementations of interface added.
	 */
	protected Set<Class<?>> implementationsAdded;
	
	/**
	 * The interface type class.
	 */
	protected Class<E> type;
	
	/**
	 * Instantiate.
	 * @param type
	 * 		The class type of interface.
	 */
	public InterfaceSet(Class<E> type) {
		this.type = type;
		this.values = new HashSet<E>();
		this.implementationsAdded = new HashSet<Class<?>>();
	}
	
	/**
	 * Instantiate.
	 * @param type
	 * 		The class type of interface.
	 * @param values
	 * 		The pre-set values.
	 */
	public InterfaceSet(Class<E> type, Set<E> values) {
		this(type);
		for (E e : values) {
			this.add(e);
		}
	}

	@Override
	public boolean add(E e) {
		
		if (implementationsAdded.contains(e.getClass())) {
			return false;
		}
		
		if (!(e.getClass().isAssignableFrom(this.getInterfaceClass()))) {
			return false;
		}
		
		implementationsAdded.add(e.getClass());
		return values.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		
		for (Object e : c) {
			if (implementationsAdded.contains(e.getClass())) {
				c.remove(e);
			}
			if (!(e.getClass().isAssignableFrom(this.getInterfaceClass()))) {
				c.remove(e);
			}
		}
		
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
	public boolean isEmpty() {
		return values.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return values.iterator();
	}

	@Override
	public boolean remove(Object o) {
		
		if (!(contains(o))) {
			return false;
		}
		
		implementationsAdded.remove(o.getClass());
		return values.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		
		List<Object> temp = new ArrayList<>();
		
		for (Object o : c) {
			remove(o);
			temp.add(o);
			if (temp.size() == c.size()) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return values.retainAll(c);
	}

	@Override
	public int size() {
		return values.size();
	}
	
	/**
	 * @return The size of implementations added.
	 */
	public int implementationAddedSize() {
		return implementationsAdded.size();
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
	 * @return The interface type class.
	 */
	public Class<E> getInterfaceClass() {
		return type;
	}
}
