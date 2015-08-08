package zenixmc.organization;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.organization.clan.ClanBanEvent;
import zenixmc.event.organization.clan.ClanClaimEvent;
import zenixmc.event.organization.clan.ClanDisbandEvent;
import zenixmc.event.organization.clan.ClanInviteEvent;
import zenixmc.event.organization.clan.ClanJoinEvent;
import zenixmc.event.organization.clan.ClanKickEvent;
import zenixmc.event.organization.clan.ClanLeaveEvent;
import zenixmc.event.organization.clan.ClanPardonEvent;
import zenixmc.event.organization.clan.ClanReDescEvent;
import zenixmc.event.organization.clan.ClanReNameEvent;
import zenixmc.organization.clans.Clan;
import zenixmc.organization.clans.Territory;
import zenixmc.organization.clans.TerritoryManager;
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
	 * Territory Manager.
	 */
	private final TerritoryManager terManager;
	
	/**
	 * Repository to fetch organization data.
	 */
	private final CachedOrganizationRepository repository;
	
	/**
	 * Instantiate.
	 * @param repository
	 * 		Repository to fetch organization data.
	 */
	public OrganizationManager(ZenixMCInterface zenix, CachedOrganizationRepository repository, ZenixUserManager manager, TerritoryManager terManager, EventDispatcher eventDispatcher) {
		this.zenix = zenix;
		this.repository = repository;
		this.manager = manager;
		this.terManager = terManager;
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
	
	/**
	 * Disbands an clan.
	 * @param clan
	 * 		The clan to disband.
	 * @param disbander
	 * 		The user disbanding the clan.
	 */
	public boolean disbandClan(Clan clan, ZenixUserInterface disbander) {
		
		ClanDisbandEvent e = new ClanDisbandEvent(clan, disbander, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanDisbandMessage(), clan, disbander), MessageOccasion.CLAN, zenix));
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return false;
		}
		
		Clan c = e.getClan();
		
		if (c.isDisbanded()) {
			return false;
		}
		
		zenix.message(null, e.getMessage(), c.onlineArray());
		c.disband();
		repository.delete(c);
		return true;
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
		
		zenix.message(null, e.getMessage(), c.onlineArray());
		
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
		saveClan(c);
		
		zenix.message(null, e.getMessage(), c.onlineArray());
		
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
		
		zenix.message(null, e.getMessage(), c.onlineArray());
		e.getInvited().getZenixUser().sendMessage(StringFormatter.format(StringFormatter.format(StringFormatter.format("You have been invited to <clan> by <zenixUser>", c, e.getInviter()), MessageOccasion.CLAN, zenix)));
		
		return true;
	}

	public boolean joinClan(Clan clan, OrganizationPlayerInterface joining) {
		
		ClanJoinEvent e = new ClanJoinEvent(clan, joining, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanJoinMessage(), joining, clan), MessageOccasion.CLAN, zenix));
		
		if (clan.needInvite()) {
			e.setCancelled(!joining.hasInviteFor(clan.getName()));
		}
		
		if (clan.isBanned(joining)) {
			e.setCancelled(true);
		}
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return false;
		}
		
		Clan c = e.getClan();
		OrganizationPlayerInterface join = e.getJoining();
		
		zenix.message(null, e.getMessage(), c.onlineArray());
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
		zenix.message(null, e.getMessage(), c.onlineArray());
		leave.getZenixUser().sendMessage(StringFormatter.format(StringFormatter.format("You have left <clan>.", c), MessageOccasion.CLAN, zenix));
		saveClan(c);
		return true;
	}
	
	public boolean kickFromClan(Clan clan, OrganizationPlayerInterface kicked, ZenixUserInterface kicking) {
		
		if (!(clan.isMember(kicked))) {
			return false;
		}
		
		ClanKickEvent e = new ClanKickEvent(clan, kicked, kicking, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanKickMessage(), kicked, clan), MessageOccasion.CLAN, zenix));
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return false;
		}
		
		Clan c = e.getClan();
		OrganizationPlayerInterface k = e.getKicked();
		ZenixUserInterface ki = e.getKicking();
		
		zenix.message(null, e.getMessage(), c.onlineArray());
		k.getZenixUser().sendMessage(StringFormatter.format(StringFormatter.format("You have been kicked from <clan> by <zenixUser>.", c, ki), MessageOccasion.CLAN, zenix));
		ki.sendMessage(StringFormatter.format(StringFormatter.format("You have kicked <orgPlayer> from <clan>.", k, c), MessageOccasion.CLAN, zenix));
		c.removeMember(kicked);
		saveClan(c);
		return true;
	}
	
	public void banFromClan(Clan clan, OrganizationPlayerInterface banned, ZenixUserInterface banning) {
		
		if (clan.isBanned(banned)) {
			return;
		}
		
		ClanBanEvent e = new ClanBanEvent(clan, banned, banning, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanBanMessage(), banned, clan, banning), MessageOccasion.CLAN, zenix));
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return;
		}
		
		Clan c = e.getClan();
		OrganizationPlayerInterface b = e.getBanned();
		ZenixUserInterface ba = e.getBanner();
		
		if (c.isMember(b)) {
			kickFromClan(c, b, ba);
		}
		
		zenix.message(null, e.getMessage(), c.onlineArray());
		b.getZenixUser().sendMessage(StringFormatter.format(StringFormatter.format("You have been banned from <clan> by <zenixUser>.", c, ba), MessageOccasion.CLAN, zenix));
		ba.sendMessage(StringFormatter.format(StringFormatter.format("You have banned <orgPlayer> from <clan>.", b, c), MessageOccasion.CLAN, zenix));
		c.ban(b);
		saveClan(c);
	}
	
	public void pardonFromClan(Clan clan, OrganizationPlayerInterface pardoned, ZenixUserInterface pardoner) {
		
		if (!(clan.isBanned(pardoned))) {
			return;
		}
		
		ClanPardonEvent e = new ClanPardonEvent(clan, pardoned, pardoner, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanBanMessage(), pardoned, clan, pardoner), MessageOccasion.CLAN, zenix));
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return;
		}
		
		Clan c = e.getClan();
		OrganizationPlayerInterface p = e.getPardoned();
		ZenixUserInterface pa = e.getPardoner();
		
		zenix.message(null, e.getMessage(), c.onlineArray());
		p.getZenixUser().sendMessage(StringFormatter.format(StringFormatter.format("You have been pardoned from <clan>'s banlist by <zenixUser>.", c, pa), MessageOccasion.CLAN, zenix));
		pa.sendMessage(StringFormatter.format(StringFormatter.format("You have pardoned <orgPlayer> from <clan>'s banlist.", p, c), MessageOccasion.CLAN, zenix));
		c.pardon(p);
		saveClan(c);
	}
	
	public void setNeedInviteClan(Clan clan, boolean set, ZenixUserInterface setter) {
		
		if (set) {
			if (!(clan.needInvite())) {
				clan.setInvite(true);
				zenix.broadcastMessage(setter, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanNeedInviteTrueMessage(), clan, setter), MessageOccasion.CLAN, zenix));
			}
		}else {
			if (clan.needInvite()) {
				clan.setInvite(false);
				zenix.broadcastMessage(setter, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanNeedInviteFalseMessage(), clan, setter), MessageOccasion.CLAN, zenix));
			}
		}
		saveClan(clan);
	}
	
	public boolean claimTerritory(OrganizationPlayerInterface claimer, Territory territory) {
		
		if (!(claimer.hasClan())) {
			return false;
		}
		
		Clan clan = claimer.getClan();
		
		ClanClaimEvent e = new ClanClaimEvent(clan, claimer, territory, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanClaimMessage(), clan, territory.getCoords().getA(), territory.getCoords().getB()), MessageOccasion.CLAN, zenix));
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return false;
		}
		
		Clan c = e.getClan();
		OrganizationPlayerInterface o = e.getClaimer();
		Territory t = e.getTerritory();
		
		c.claim(t);
		
		zenix.message(null, e.getMessage(), c.onlineArray());
		o.getZenixUser().sendMessage(StringFormatter.format(StringFormatter.format("You have claimed Chunk X: <integer> Chunk Z: <integer>.", t.getCoords().getA(), t.getCoords().getB()), MessageOccasion.CLAN, zenix));
		
		return true;
	}
	
	public boolean unClaimTerritory(OrganizationPlayerInterface claimer, Territory territory) {
		
		if (!(claimer.hasClan())) {
			return false;
		}
		
		Clan clan = claimer.getClan();
		
		ClanClaimEvent e = new ClanClaimEvent(clan, claimer, territory, StringFormatter.format(StringFormatter.format(zenix.getSettings().clanClaimMessage(), clan, territory.getCoords().getA(), territory.getCoords().getB()), MessageOccasion.CLAN, zenix));
		
		eventDispatcher.callEvent(e);
		
		if (e.isCancelled()) {
			return false;
		}
		
		Clan c = e.getClan();
		OrganizationPlayerInterface o = e.getClaimer();
		Territory t = e.getTerritory();
		
		c.unClaim(t);
		
		zenix.message(null, e.getMessage(), c.onlineArray());
		o.getZenixUser().sendMessage(StringFormatter.format(StringFormatter.format("You have unclaimed Chunk X: <integer> Chunk Z: <integer>.", t.getCoords().getA(), t.getCoords().getB()), MessageOccasion.CLAN, zenix));
		
		return true;
	}
}
