package zenixmc.organization.clans;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;

import zenixmc.ZenixMC;
import zenixmc.ZenixMCInterface;
import zenixmc.organization.Influence;
import zenixmc.organization.Influential;
import zenixmc.organization.Members;
import zenixmc.organization.Organization;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.persistance.CachedOrganizationRepository;
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
	protected transient ZenixMCInterface zenix;
	
	/**
	 * The user manager.
	 */
	protected transient ZenixUserManager manager;
	
	/**
	 * The territory manager.
	 */
	protected transient TerritoryManager territoryManager;
	
	/**
	 * Whether the clan has been disbanded.
	 */
	protected transient boolean disbanded = false;
	
	/**
	 * The name of clan.
	 */
	protected String name;
	
	/**
	 * The description of the clan.
	 */
	protected String[] desc;
	
	/**
	 * The clans members.
	 */
	protected Members members;
	
	/**
	 * Clan ban list.
	 */
	protected List<UUID> banlist = new ArrayList<>();
	
	/**
	 * Clan territory Id list.
	 */
	protected List<String> territoryIds = new ArrayList<>();
	
	/**
	 * Whether users need invitations to join the clan.
	 */
	protected boolean invite = true;

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
		if (leader != null) {
			leader.setClan(this);
		}
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
		
		String[] about = new String[9];
		
		String l1 = s + "=====" + p + "Clan About" + s + "======";
		String l2 = s + "+" + p + "Name: " + s + getName();
		String l3 = s + "+" + p + "Influence: " + s + calcTotalInfluence() + p + "/" + s + calcTotalMaxInfluence();
		String l4 = s + "+" + p + "Territory: " + s + territoryAmount();
		String l5 = s + "+" + p + "Description: " + s + JavaUtil.arrayToString(getDescription());
		String l6 = s + "+" + p + "Need Invitation: " + s + needInvite();
		String l7 = s + "+" + p + "Online Members: " + s + getMembers().onlineMembers();
		String l8 = s + "+" + p + "Offline Members: " + s + getMembers().offlineMembers();
		String l9 = s + "+" + p + "Banned Users: " + s + bannedUsers();
		
		about[0] = l1;
		about[1] = l2;
		about[2] = l3;
		about[3] = l4;
		about[4] = l5;
		about[5] = l6;
		about[6] = l7;
		about[7] = l8;
		about[8] = l9;
		
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
	public void setTerritoryManager(TerritoryManager value) {
		this.territoryManager = value;
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
	public boolean isVulnerable() {
		return calcTotalInfluence() < territoryAmount();
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
			territoryIds.add(i);
		}
	}

	@Override
	public void unClaim(Territory territory) {
		if (ownsTerritory(territory)) {
			String i = territory.getId();
			territoryIds.remove(i);
		}
	}

	@Override
	public boolean ownsTerritory(Territory land) {
		return territoryIds.contains(land.getId());
	}
	
	@Override
	public int territoryAmount() {
		System.out.println(territoryIds);
		return territoryIds.size();
	}

	@Override
	public boolean equals(Object ob) {
		if (ob instanceof Organization) {
			if (ob instanceof Clan) {
				Clan c = (Clan) ob;
				return name.equals(c.getName());
			}
		}
		return false;
	}
	
	/**
	 * Serialize this instance.
	 * 
	 * @param out
	 *            Target to which this instance is written.
	 * @throws IOException
	 *             Thrown if exception occurs during serialization.
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.writeUTF(name);
		out.writeObject(desc);
		out.writeObject(members);
		out.writeObject(banlist);
		out.writeObject(territoryIds);
		out.writeBoolean(invite);
	}

	/**
	 * Deserialize this instance from input stream.
	 * 
	 * @param in
	 *            Input Stream from which this instance is to be deserialized.
	 * @throws IOException
	 *             Thrown if error occurs in deserialization.
	 * @throws ClassNotFoundException
	 *             Thrown if expected class is not found.
	 */
	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
		name = in.readUTF();
		desc = (String[]) in.readObject();
		members = (Members) in .readObject();
		banlist = (List<UUID>) in .readObject();
		territoryIds = (List<String>) in .readObject();
		invite = in.readBoolean();
	}
}
