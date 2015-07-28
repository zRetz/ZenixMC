package zenixmc.persistance;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

import zenixmc.organization.Organization;
import zenixmc.organization.OrganizationSet;
import zenixmc.organization.clans.Clan;

/**
 * Persistence of organizations on disk.
 * @author james
 */
public class OrganizationRepository extends Repository implements OrganizationRepositoryInterface {

	/**
	 * Directory to store clan data.
	 */
	private final File clansDirectory;
	
	/**
	 * Instantiate.
	 * @param logger
	 * 		The logger to debug/log.
	 * @param directory
	 * 		The directory to store organizations in.
	 */
	public OrganizationRepository(Logger logger, File directory) {
		super(logger, directory);
		this.clansDirectory = new File(directory, "clans");
	}

	/**
     * Returns the path to the file to store the specified clan in.
     *
     * @param name
     *     	The name of the clan to find.
     * @return The file of the clan.
     */
    protected File getClanFile(String name) {
        return new File(this.directory, name + ".json");
    }

	
	@Override
	public void open(String openMessage) {
		super.open(openMessage);
		if (!(clansDirectory.exists())) {
			clansDirectory.mkdir();
		}
	}

	@Override
	public void close(String closeMessage) {
		super.close(closeMessage);
	}

	@Override
	public void save(Object object) {
		if (object instanceof Organization) {
			if (object instanceof Clan) {
				save((Organization) object, Clan.class);
			}
		}
	}
	
	@Override
	public OrganizationSet getOrganizations(Map<String, Class<?>> type) {
		
		OrganizationSet result = new OrganizationSet();
		
		for (Map.Entry<String, Class<?>> map : type.entrySet()) {
			result.add(getOrganization(map.getKey(), map.getValue()));
		}
		
		return result;
	}

	@Override
	public Organization getOrganization(String name, Class<?> type) {
		if (type == Clan.class) {
			return getClan(name);
		}
		return null;
	}

	@Override
	public Clan getClan(String name) {
		return null;
	}

	@Override
	public void save(Organization organization, Class<?> type) {
		if (type == Clan.class) {
			save(organization);
			return;
		}
	}

	@Override
	public void save(Clan clan) {
		
	}

}
