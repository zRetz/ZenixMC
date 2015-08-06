package zenixmc.organization;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.organization.clan.ClanUpdateEvent;
import zenixmc.organization.clans.Clan;
import zenixmc.persistance.CachedOrganizationRepository;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;

/**
 * Class to handle organization events, etc.
 * 
 * @author james
 */
public class OrganizationManager {

	/**
	 * The plugin.
	 */
	private final ZenixMCInterface zenix;
	
	/**
	 * Event Dispatcher to fire events.
	 */
	private final EventDispatcher eventDispatcher;
	
	/**
	 * User Manager.
	 */
	private final ZenixUserManager manager;
	
	/**
	 * Repository to fetch organization data.
	 */
	private final CachedOrganizationRepository repository;
	
	/**
	 * Instantiate.
	 * @param repository
	 * 		Repository to fetch organization data.
	 */
	public OrganizationManager(ZenixMCInterface zenix, CachedOrganizationRepository repository, ZenixUserManager manager, EventDispatcher eventDispatcher) {
		this.zenix = zenix;
		this.repository = repository;
		this.manager = manager;
		this.eventDispatcher = eventDispatcher;
	}
	
	/**
	 * Creates a new clan.
	 * @param leader
	 * 		The leader of the clan.
	 * @param name
	 * 		The name of the clan.
	 * @return The clan made.
	 */
	public Clan createClan(OrganizationPlayerInterface leader, String name) {
		
		if (clanNameUsed(name)) {
			return null;
		}
		
		return repository.getClan(leader, name, true);
	}
	
	/**
	 * Disbands an organization.
	 * @param organization
	 * 		The organization to disband.
	 */
	public void disbandOrganization(Organization organization) {
		
		if (organization.isDisbanded()) {
			repository.delete(organization);
		}else {
			organization.disband();
		}
	}
	
	/**
	 * @param name
	 * 		The name of the organization.
	 * @param type
	 * 		The class type of the organization.
	 * @return The requested organization.
	 */
	public Organization getOrganization(String name, Class<?> type) {
		return repository.getOrganization(name, type);
	}
	
	/**
	 * @param name
	 * 		The name of the clan.
	 * @return The requested clan.
	 */
	public Clan getClan(String name, boolean create) {
		return repository.getClan(null, name, create);
	}
	
	/**
	 * Returns clan from a reference.
	 * @param ref
	 * 		Reference to a clan, can be player or clan.
	 * @return The clan.
	 */
	public Clan getClanFromReference(String ref) {
		
		Clan result = null;
		
		if (clanNameUsed(ref)) {
			result = getClan(ref, false);
		}
		
		if (result == null) {
			if (manager.isZenixUser(ref)) {
				ZenixUserInterface zui = manager.getRegardlessZenixUser(ref);
				result = zui.getOrganizationPlayer().getClan();
			}
		}
		
		return result;
	}
	
	/**
	 * @param name
	 * 		The name to check.
	 * @return <code>true</code> If the name is already used by a clan.
	 */
	public boolean clanNameUsed(String name) {
		return repository.clanNameUsed(name);
	}
	
	public void saveOrganization(Organization org) {
		repository.save(org);
	}
	
	public void saveClan(Clan clan) {
		repository.save(clan);
	}
	
	public String setClanName(Clan clan, String name) {
		
		clan.setName(name);
		eventDispatcher.callEvent(new ClanUpdateEvent(clan));
		
		return name;
	}
	
	public String setClanDescription(Clan clan, String[] desc) {
		
		clan.setDescription(desc);
		eventDispatcher.callEvent(new ClanUpdateEvent(clan));
		
		return JavaUtil.arrayToString(desc);
	}
}
