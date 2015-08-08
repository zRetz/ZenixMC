package zenixmc.block;

import java.util.HashMap;

/**
 * A block location to object map.
 *
 * @param <V>
 *    	The value class
 */
public class BlockMap<V> extends HashMap<BlockLocation, V> {
    /**
     * The version of this class, used to serialize/deserialize.
     */
    public static final long serialVersionUID = 1L;
}