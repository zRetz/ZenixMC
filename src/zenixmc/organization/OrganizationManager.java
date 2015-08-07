package zenixmc.organization;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.organization.clan.ClanInviteEvent;
import zenixmc.event.organization.clan.ClanJoinEvent;
import zenixmc.event.organization.clan.ClanKickEvent;
import zenixmc.event.organization.clan.ClanLeaveEvent;
import zenixmc.event.organization.clan.ClanReDescEvent;
import zenixmc.event.organization.clan.ClanReNameEvent;
import zenixmc.organization.clans.Clan;
import zenixmc.persistance.CachedOrganizationRepository;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

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
	
	public String setClanName(Clan clan, ZenixUserInterface setter, String name) {
		
		String newName = name;
		String oldName = clan.getName();
		
		ClanReNameEvent e = new ClanReNameEvent(clan, setter, newName, oldName, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanReDescMessage(), clan, oldName, newName), MessageOccasion.CLAN, zenix));
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return null;
		}
		
		Clan c = e.getClan();
		
		repository.renameClan(c, e.getOldName(), e.getNewName());
		
		c.sendMessage(e.getMessage(), c.getMembers().getOnlineMembers().toArray(new OrganizationPlayerInterface[c.getMembers().getOnlineMembers().size()]));
		
		return name;
	}
	
	public String setClanDescription(Clan clan, ZenixUserInterface setter, String[] desc) {
		
		String[] newDesc = desc;
		String[] oldDesc = clan.getDescription();
		
		ClanReDescEvent e = new ClanReDescEvent(clan, setter, newDesc, oldDesc, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanReDescMessage(), clan, JavaUtil.arrayToString(oldDesc), JavaUtil.arrayToString(newDesc)), MessageOccasion.CLAN, zenix));
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return null;
		}
		
		Clan c = e.getClan();
		
		c.setDescription(e.getNewDesc());
		
		c.sendMessage(e.getMessage(), c.onlineArray());
		
		return JavaUtil.arrayToString(desc);
	}
	
	public boolean inviteToClan(Clan clan, OrganizationPlayerInterface player, ZenixUserInterface inviter) {
		
		ClanInviteEvent e = new ClanInviteEvent(clan, player, inviter, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanInviteMessage(), player, clan, inviter), MessageOccasion.CLAN, zenix));
		
		e.setCancelled(!clan.invite(player));
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return false;
		}
		
		Clan c = e.getClan();
		
		c.sendMessage(e.getMessage(), c.onlineArray());
		e.getInvited().getZenixUser().sendMessage(StringFormatter.format(StringFormatter.format(StringFormatter.format("You have been invited to <clan> by <zenixUser>", c, e.getInviter()), MessageOccasion.CLAN, zenix)));
		
		return true;
	}

	public boolean joinClan(Clan clan, OrganizationPlayerInterface joining) {
		
		ClanJoinEvent e = new ClanJoinEvent(clan, joining, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanJoinMessage(), joining, clan), MessageOccasion.CLAN, zenix));
		
		if (clan.needInvite()) {
			e.setCancelled(!joining.hasInviteFor(clan.getName()));
		}
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return false;
		}
		
		Clan c = e.getClan();
		OrganizationPlayerInterface join = e.getJoining();
		
		c.sendMessage(e.getMessage(), c.onlineArray());
		join.getZenixUser().sendMessage(StringFormatter.format(StringFormatter.format("You have joined <clan>.", c), MessageOccasion.CLAN, zenix));
		
		if (c.needInvite()) {
			return c.completeInvite(join);
		}else {
			c.addMember(join);
			return true;
		}
	}
	
	public boolean leaveClan(OrganizationPlayerInterface leaving) {
		
		if (!(leaving.hasClan())) {
			return false;
		}
		
		Clan c = leaving.getClan();
		
		ClanLeaveEvent e = new ClanLeaveEvent(c, leaving, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanLeaveMessage(), leaving, c), MessageOccasion.CLAN, zenix));
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return false;
		}
		
		OrganizationPlayerInterface leave = e.getLeaving();
		
		c.removeMember(leave);
		c.sendMessage(e.getMessage(), c.onlineArray());
		leave.getZenixUser().sendMessage(StringFormatter.format(StringFormatter.format("You have left <clan>.", c), MessageOccasion.CLAN, zenix));
		
		return true;
	}
	
	public boolean kickFromClan(Clan clan, OrganizationPlayerInterface kicked, ZenixUserInterface kicking) {
		
		if (!(clan.isMember(kicked))) {
			return false;
		}
		
		ClanKickEvent e = new ClanKickEvent(clan, kicked, kicking, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanLeaveMessage(), kicked, clan), MessageOccasion.CLAN, zenix));
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return false;
		}
		
		Clan c = e.getClan();
		
		c.removeMember(kicked);
		c.sendMessage(e.getMessage(), c.onlineArray());
		return true;
	}
	
}
