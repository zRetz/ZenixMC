package zenixmc.persistance;

import zenixmc.bending.BendingPlayerInterface;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.ZenixUserInterface;

/**
 * Repository to persist organizationPlayers information when they go offline.
 * @author james
 */
public interface OrganizationPlayerRepositoryInterface extends RepositoryInterface {

	/**
	 * Loads a organizationPlayer into memory.
	 * @param zui
	 * 		The user to indicate which organizationPlayer to load.
	 * @return The 
	 */
	OrganizationPlayerInterface getOrganizationPlayer(ZenixUserInterface zui);
	
	/**
	 * Sets the repository's user repository.
	 * @param zenixUserRepository
	 * 		The user repository to set.
	 */
	void setZenixUserRepository(ZenixUserRepositoryInterface zenixUserRepository);
	
	/**
     * Saves a organizationPlayer.
     * @param organizationPlayer
     *      The organizationPlayer to save.
     */
    void save(final OrganizationPlayerInterface organizationPlayer);
}
