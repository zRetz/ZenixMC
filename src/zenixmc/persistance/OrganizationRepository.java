package zenixmc.persistance;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import zenixmc.ZenixMCInterface;
import zenixmc.io.SeDe;
import zenixmc.organization.Organization;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.OrganizationSet;
import zenixmc.organization.clans.Clan;
import zenixmc.organization.clans.TerritoryManager;
import zenixmc.organization.clans.defaults.Wild;
import zenixmc.user.ZenixUserManager;

/**
 * Persistence of organizations on disk.
 * 
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
	 * Territory manager.
	 */
	private final TerritoryManager territoryManager;

	/**
	 * Organization manager.
	 */
	private OrganizationManager orgManager;

	/**
	 * Instantiate.
	 * 
	 * @param logger
	 *            The logger to debug/log.
	 * @param directory
	 *            The directory to store organizations in.
	 */
	public OrganizationRepository(Logger logger, File directory, ZenixUserManager manager,
			TerritoryManager territoryManager, ZenixMCInterface zenix) {
		super(logger, directory);
		this.clansDirectory = new File(directory, "clans");
		this.manager = manager;
		this.territoryManager = territoryManager;
		this.zenix = zenix;
	}

	/**
	 * Returns the path to the file to store the specified clan in.
	 *
	 * @param name
	 *            The name of the clan to find.
	 * @return The file of the clan.
	 */
	protected File getClanFile(String name) {
		return new File(this.clansDirectory, name + ".dat");
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
			return getClan(null, name, false);
		}
		return null;
	}

	@Override
	public Clan getClan(OrganizationPlayerInterface leader, String name, boolean create) {

		final File f = getClanFile(name);

		Clan clan = null;

		if (!(f.exists()) && create) {
			if (name.equals("Wild")) {
				clan = new Wild(zenix, manager, territoryManager);
				System.out.println("wild");
			}else {
				clan = new Clan(zenix, manager, territoryManager, leader, name);
				System.out.println("not wild");
			}
			save(clan);
			return clan;
		}

		clan = SeDe.deserialize(f, Clan.class);
		clan.setZenixMC(zenix);
		clan.setZenixUserManager(manager);
		clan.setTerritoryManager(territoryManager);
		clan.setDisbanded(false);

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

		SeDe.serialize(clan, f);

		logger.log(Level.INFO, "Clan: " + clan.getName() + " has been saved.");
	}

	@Override
	public void renameClan(Clan clan, String oName, String nName) {

		final File f = getClanFile(oName);

		f.renameTo(getClanFile(nName));

		clan.setName(nName);
		save(clan);
	}

	@Override
	public void delete(Object ob) {
		if (ob instanceof Organization) {
			delete(ob);
			return;
		}
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

		logger.log(Level.INFO, "Clan: " + clan.getName() + " has been deleted.");
	}

	@Override
	public boolean clanNameUsed(String name) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}

	@Override
	public Set<String> fileNames() {

		Set<String> result = new HashSet<>();

		File[] files = files();

		for (File f : files) {
			result.add(f.getName().substring(0, f.getName().length() - 4));
		}

		return result;
	}

	@Override
	public File[] files() {
		return this.clansDirectory.listFiles(new FileFilter() {

			@Override
			public boolean accept(File path) {
				return path.getName().endsWith(".dat");
			}

		});
	}

	public void setOrganizationManager(OrganizationManager value) {
		this.orgManager = value;
	}

}
