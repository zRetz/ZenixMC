package zenixmc.persistance;

import org.bukkit.Chunk;

import zenixmc.organization.Organization;
import zenixmc.organization.clans.Territory;

/**
 * Repository to persist territory information.
 * 
 * @author james
 */
public interface TerritoryRepositoryInterface extends RepositoryInterface {

	/**
	 * @param id
	 * 		The id of the territory.
	 * @param c
	 * 		The chunk of territory.
	 * @param org
	 * 		The organization owning the territory.
	 * @return The requested territory.
	 */
	Territory getTerritory(String id, Chunk c, Organization org);
	
	/**
	 * @param c
	 * 		The chunk of territory.
	 * @return The requested territory.
	 */
	Territory getTerritory(Chunk c);
	
	/**
	 * @param id
	 * 		The id to check.
	 * @return <code>true</code> If the id correlates to some territory.
	 */
	boolean isTerritory(String id);
	
	/**
	 * @param c		
	 * 		The chunk to check.
	 * @return <code>true</code> If the chunk correlates to some territory.
	 */
	boolean isTerritory(Chunk c);
	
	/**
     * Save territory.
     * @param territory
     *      The territory to save. 
     */
    void save(Territory territory);
    
    /**
     * Delete territory.
     * @param territory
     * 		The territory to delete.
     */
    void delete(Territory territory);
	
}
