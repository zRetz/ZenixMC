package zenixmc.organization.clans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;

import zenixmc.ZenixMCInterface;
import zenixmc.organization.Influential;
import zenixmc.organization.Members;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.OfflineZenixUser;
import zenixmc.user.ZenixUser;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;

/**
 * Class Representation of Clans.
 * 
 * @author james
 */
public class Clan implements Influential {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 6504052661081675252L;

	/**
	 * The plugin.
	 */
	private transient ZenixMCInterface zenix;
	
	/**
	 * The user manager.
	 */
	private transient ZenixUserManager manager;
	
	/**
	 * The territory manager.
	 */
	private transient TerritoryManager territoryManager;
	
	/**
	 * Whether the clan has been disbanded.
	 */
	private transient boolean disbanded = false;
	
	/**
	 * The name of clan.
	 */
	private String name;
	
	/**
	 * The description of the clan.
	 */
	private String[] desc;
	
	/**
	 * The clans members.
	 */
	private Members members;
	
	/**
	 * Clan ban list.
	 */
	private List<UUID> banlist = new ArrayList<>();
	
	/**
	 * Clan territory Id list.
	 */
	private List<String> territoryIds = new ArrayList<>();
	
	/**
	 * Whether users need invitations to join the clan.
	 */
	private boolean invite = true;

	/**
	 * Instantiate.
	 * @param leader
	 * 		The leader of the clan.
	 * @param name
	 * 		The name of the clan.
	 */
	public Clan(ZenixMCInterface zenix, ZenixUserManager manager, TerritoryManager terManager, OrganizationPlayerInterface leader, String name) {
		this.zenix = zenix;
		this.manager = manager;
		this.territoryManager = terManager;
		this.name = name;
		this.desc = zenix.getSettings().clanDescMessage().length() < zenix.getSettings().maxClanDescLength() ? new String[]{zenix.getSettings().clanDescMessage()} : new String[]{"Default Clan Description ;3"};
		this.members = new Members(leader, this.manager);
		leader.setClan(this);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setDescription(String[] desc) {
		this.desc = desc;
	}
	
	@Override
	public String[] getDescription() {
		return desc;
	}
	
	@Override
	public String[] about() {
		
		ChatColor p = zenix.getSettings().getClanColor();
		ChatColor s = zenix.getSettings().getMatchingClanColor();
		
		String[] about = new String[8];
		
		String l1 = s + "=====" + p + "Clan About" + s + "======";
		String l2 = s + "+" + p + "Name: " + s + getName();
		String l3 = s + "+" + p + "Influence: " + s + calcTotalInfluence() + p + "/" + s + calcTotalMaxInfluence();
		String l4 = s + "+" + p + "Description: " + s + JavaUtil.arrayToString(getDescription());
		String l5 = s + "+" + p + "Need Invitation: " + s + needInvite();
		String l6 = s + "+" + p + "Online Members: " + s + getMembers().onlineMembers();
		String l7 = s + "+" + p + "Offline Members: " + s + getMembers().offlineMembers();
		String l8 = s + "+" + p + "Banned Users: " + s + bannedUsers();
		
		about[0] = l1;
		about[1] = l2;
		about[2] = l3;
		about[3] = l4;
		about[4] = l5;
		about[5] = l6;
		about[6] = l7;
		about[7] = l8;
		
		return about;
	}

	@Override
	public void setLeader(OrganizationPlayerInterface value) {
		getLeader().setClan(null);
		value.setClan(this);
		members.setLeader(value);
	}
	
	@Override
	public OrganizationPlayerInterface getLeader() {
		return members.getLeader();
	}
	
	@Override
	public boolean isLeader(OrganizationPlayerInterface player) {
		return members.getLeader().getZenixUser().compareTo(player.getZenixUser()) == 0;
	}
	
	@Override
	public void addMember(OrganizationPlayerInterface player) {
		if (!(members.isMember(player))) {
			members.addMember(player);
			player.setClan(this);
		}
		
	}

	@Override
	public void removeMember(OrganizationPlayerInterface player) {
		if (members.isMember(player)) {
			
			if (isLeader(player)) {
				if (getMembers().size() > 1) {
					setLeader(getMembers().getMembers().get(0));
				}
			}
			
			members.removeMember(player);
			player.setClan(null);
		}
	}
	
	@Override
	public boolean isMember(OrganizationPlayerInterface player) {
		return members.isMember(player);
	}

	@Override
	public boolean isMember(String name) {
		
		if (!(manager.isZenixUser(name))) {
			return false;
		}
		
		return isMember(manager.getRegardlessZenixUser(name).getOrganizationPlayer());
	}

	@Override
	public boolean isMember(UUID uuid) {
		return members.isMember(uuid);
	}
	
	@Override
	public void setInvite(boolean set) {
		this.invite = set;
		System.out.println(invite);
	}
	
	@Override
	public boolean needInvite() {
		return invite;
	}

	@Override
	public boolean invite(OrganizationPlayerInterface player) {
		if (!(player.hasInviteFor(name))) {
			player.addInviteRequest(name);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean completeInvite(OrganizationPlayerInterface player) {
		
		if (!(player.hasInviteFor(name))) {
			return false;
		}
		
		player.removeInviteRequest(name);
		addMember(player);
		return true;
	}

	@Override
	public void disband() {
		
		disbanded = true;
		
		for (OrganizationPlayerInterface o : members.getMembers()) {
			removeMember(o);
		}
	}

	@Override
	public OrganizationPlayerInterface getMember(String name) {
		return members.getMember(name);
	}
	
	@Override
	public OrganizationPlayerInterface getMember(UUID uuid) {
		return members.getMember(uuid);
	}
	
	@Override
	public Members getMembers() {
		return members;
	}
	
	@Override
	public ZenixUserInterface[] onlineArray() {

		List<OrganizationPlayerInterface> onmembers = members.getOnlineMembers();
		
		ZenixUserInterface[] result = new ZenixUser[onmembers.size()];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = onmembers.get(i).getZenixUser();
		}
		
		return result;
	}

	@Override
	public ZenixUserInterface[] offlineArray() {
		
		List<OrganizationPlayerInterface> ofmembers = members.getOfflineMembers();
		
		ZenixUserInterface[] result = new OfflineZenixUser[ofmembers.size()];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = ofmembers.get(i).getZenixUser();
		}
		
		return result;
	}
	
	@Override
	public ZenixUserInterface[] bannedArray() {
		
		List<OrganizationPlayerInterface> bamembers = getBannedUsers();
		
		ZenixUserInterface[] result = new OfflineZenixUser[bamembers.size()];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = bamembers.get(i).getZenixUser();
		}
		
		return result;
	}

	@Override
	public void setZenixMC(ZenixMCInterface value) {
		this.zenix = value;
	}
	
	@Override
	public void setZenixUserManager(ZenixUserManager value) {
		this.manager = value;
		this.members.setZenixUserManager(value);
	}
	
	@Override
	public Set<String> nameSet() {
		Set<String> names = new HashSet<>();
		
		for (OrganizationPlayerInterface o : members.getMembers()) {
			names.add(o.getZenixUser().getName());
		}
		names.add(getLeader().getZenixUser().getName());
		
		return Collections.unmodifiableSet(names);
	}

	@Override
	public int calcTotalInfluence() {
		
		int result = 0;
		
		for (OrganizationPlayerInterface o : members.getOnlineMembers()) {
			result += o.getInfluence();
		}
		
		return result;
	}
	
	@Override
	public int calcTotalMaxInfluence() {
		
		int result = 0;
		
		for (OrganizationPlayerInterface o : members.getOnlineMembers()) {
			result += o.getMaxInfluence();
		}
		
		return result;
	}

	@Override
	public boolean isGreaterThan(Influential members) {
		return this.calcTotalInfluence() > members.calcTotalInfluence();
	}

	@Override
	public boolean isLessThan(Influential members) {
		return this.calcTotalInfluence() < members.calcTotalInfluence();
	}

	@Override
	public void setDisbanded(boolean set) {
		this.disbanded = set;
	}
	
	@Override
	public boolean isDisbanded() {
		return disbanded;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void ban(OrganizationPlayerInterface player) {
		if (!(isBanned(player))) {
			banlist.add(player.getZenixUser().getUniqueId());
		}
	}

	@Override
	public void pardon(OrganizationPlayerInterface player) {
		if (isBanned(player)) {
			banlist.remove(player.getZenixUser().getUniqueId());
		}
	}
	
	@Override
	public OrganizationPlayerInterface getBanned(UUID uuid) {
		if (isBanned(uuid)) {
			return manager.getRegardlessZenixUser(uuid).getOrganizationPlayer();
		}
		return null;
	}
	
	@Override
	public List<OrganizationPlayerInterface> getBannedUsers() {
		
		List<OrganizationPlayerInterface> result = new ArrayList<>();
		
		for (UUID uuid : banlist) {
			result.add(manager.getRegardlessZenixUser(uuid).getOrganizationPlayer());
		}
		
		return result;
	}
	
	@Override
	public String bannedUsers() {
		
		StringBuilder result = new StringBuilder();
		
		List<OrganizationPlayerInterface> ofs = getBannedUsers();
		
		for (OrganizationPlayerInterface o : ofs) {
			result.append(o.getZenixUser().getName());
			if (ofs.indexOf(o) != ofs.size()-1) {
				result.append(", ");
			}
		}
		
		return result.toString();
	}
	
	@Override
	public boolean isBanned(OrganizationPlayerInterface player) {
		return isBanned(player.getZenixUser().getUniqueId());
	}

	@Override
	public boolean isBanned(UUID uuid) {
		return banlist.contains(uuid);
	}

	@Override
	public void claim(Territory territory) {
		if (!(ownsTerritory(territory))) {
			String i = territory.getId();
			territoryManager.claimTerritory(i, this);
			territoryIds.add(territory.getId());
		}
	}

	@Override
	public void unClaim(Territory territory) {
		if (ownsTerritory(territory)) {
			String i = territory.getId();
			territoryManager.unClaimTerritory(i, this);
			territoryIds.remove(i);
		}
	}

	@Override
	public boolean ownsTerritory(Territory land) {
		return territoryIds.contains(land.getId());
	}

}
