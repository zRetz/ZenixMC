package zenixmc.persistance;

import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.ZenixUserInterface;

public interface OrganizationRepositoryInterface {

	/**
	 * Loads a organization into memory.
	 * @param name
	 * 		The name of the organization to load.
	 * @return The organization.
	 */
	OrganizationPlayerInterface getOrganizationPlayer(String name);
	
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
