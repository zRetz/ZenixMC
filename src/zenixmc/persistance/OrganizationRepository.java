package zenixmc.persistance;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import zenixmc.ZenixMCInterface;
import zenixmc.io.SeDe;
import zenixmc.organization.Organization;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.OrganizationSet;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserManager;

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
	 * The plugin.
	 */
	private final ZenixMCInterface zenix;
	
	/**
	 * User manager.
	 */
	private final ZenixUserManager manager;
	
	/**
	 * Instantiate.
	 * @param logger
	 * 		The logger to debug/log.
	 * @param directory
	 * 		The directory to store organizations in.
	 */
	public OrganizationRepository(Logger logger, File directory, ZenixUserManager manager, ZenixMCInterface zenix) {
		super(logger, directory);
		this.clansDirectory = new File(directory, "clans");
		this.manager = manager;
		this.zenix = zenix;
	}

	/**
     * Returns the path to the file to store the specified clan in.
     *
     * @param name
     *     	The name of the clan to find.
     * @return The file of the clan.
     */
    protected File getClanFile(String name) {
        return new File(this.directory, name + ".dat");
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
				save((Organization) object);
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
			return getClan(null, name);
		}
		return null;
	}

	@Override
	public Clan getClan(OrganizationPlayerInterface leader, String name) {
		
		final File f = getClanFile(name);

		Clan clan = null;

		if (!(f.exists())) {
			if (leader != null) {
				clan = new Clan(zenix, manager, null, name);
				save(clan);
				return clan;
			}else {
				logger.log(Level.SEVERE, "New Clan with no leader!");
				return null;
			}
		}

		clan = SeDe.deserialize(f.getName(), Clan.class);
		clan.setZenixMC(zenix);
		clan.setZenixUserManager(manager);
		
		logger.log(Level.INFO, "Clan: " + clan.getName() + " has been loaded.");

		return clan;
	}

	@Override
	public void save(Organization organization) {
		if (organization instanceof Clan) {
			save((Clan) organization);
			return;
		}
	}

	@Override
	public void save(Clan clan) {
		
		final File f = getClanFile(clan.getName());
		
		SeDe.serialize(clan, f.getName());
		
		logger.log(Level.INFO, "Clan: " + clan.getName() + " has been saved.");
	}
	
	@Override
	public void delete(Organization organization) {
		if (organization instanceof Clan) {
			delete((Clan) organization);
			return;
		}
	}

	@Override
	public void delete(Clan clan) {
		
		final File f = getClanFile(clan.getName());
		
		if (!(f.exists())) {
			return;
		}
		
		f.delete();
	}

	@Override
	public boolean clanNameUsed(String name) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}

}
