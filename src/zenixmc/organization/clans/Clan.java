package zenixmc.organization.clans;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;

import zenixmc.ZenixMCInterface;
import zenixmc.organization.Influential;
import zenixmc.organization.Members;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

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
	 * The organization manager.
	 */
	private transient OrganizationManager orgManager;
	
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
	 * Instantiate.
	 * @param leader
	 * 		The leader of the clan.
	 * @param name
	 * 		The name of the clan.
	 */
	public Clan(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationPlayerInterface leader, String name) {
		this.zenix = zenix;
		this.manager = manager;
		this.name = name;
		this.desc = zenix.getSettings().getDefaultClanDesc().length() < zenix.getSettings().getMaxClanDescLength() ? new String[]{zenix.getSettings().getDefaultClanDesc()} : new String[]{"Default Clan Description ;3"};
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
		
		String[] about = new String[6];
		
		String l1 = s + "=====" + p + "Clan About" + s + "======";
		String l2 = s + "+" + p + "Name: " + s + getName();
		String l3 = s + "+" + p + "Influence: " + s + calcTotalInfluence() + p + "/" + s + calcTotalMaxInfluence();
		String l4 = s + "+" + p + "Description: " + s + JavaUtil.arrayToString(getDescription());
		String l5 = s + "+" + p + "Online Members: " + s + getMembers().onlineMembers();
		String l6 = s + "+" + p + "Offline Members: " + s + getMembers().offlineMembers();
		
		about[0] = l1;
		about[1] = l2;
		about[2] = l3;
		about[3] = l4;
		about[4] = l5;
		about[5] = l6;
		
		return about;
	}

	@Override
	public void setLeader(OrganizationPlayerInterface value) {
		value.setClan(this);
		members.setLeader(value);
	}
	
	@Override
	public OrganizationPlayerInterface getLeader() {
		return members.getLeader();
	}
	
	@Override
	public void sendMessage(String message, OrganizationPlayerInterface... members) {
		for (int i = 0; i < members.length; i++) {
			if (isMember(members[i])) {
				members[i].getZenixUser().sendMessage(message);
			}
		}
	}
	
	@Override
	public void addMember(OrganizationPlayerInterface player) {
		if (!(members.isMember(player))) {
			members.addMember(player);
			player.setClan(this);
			sendMessage(StringFormatter.format(player.getZenixUser().getName() + " joined your clan.", MessageOccasion.CLAN, zenix));
		}
		
	}

	@Override
	public void removeMember(OrganizationPlayerInterface player) {
		if (members.isMember(player)) {
			members.removeMember(player);
			player.setClan(null);
			sendMessage(StringFormatter.format(player.getZenixUser().getName() + " left your clan.", MessageOccasion.CLAN, zenix));
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
	public boolean invite(OrganizationPlayerInterface player) {
		if (!(player.hasInviteFor(name))) {
			player.addInviteRequest(name);
			sendMessage(StringFormatter.format(player.getZenixUser().getName() + " has been invited your clan.", MessageOccasion.CLAN, zenix));
			return false;
		}
		return true;
	}
	
	@Override
	public void completeInvite(OrganizationPlayerInterface player) {
		
		if (!(player.hasInviteFor(name))) {
			return;
		}
		
		addMember(player);
	}

	@Override
	public void disband() {
		
		for (OrganizationPlayerInterface o : members.getOnlineMembers()) {
			removeMember(o);
		}
		
		disbanded = true;
		orgManager.disbandOrganization(this);
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
	public void setZenixMC(ZenixMCInterface value) {
		this.zenix = value;
	}
	
	@Override
	public void setZenixUserManager(ZenixUserManager value) {
		this.manager = value;
		this.members.setZenixUserManager(value);
	}
	
	@Override
	public void setOrganizationManager(OrganizationManager value) {
		this.orgManager = value;
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
	public boolean isDisbanded() {
		return disbanded;
	}
	
}
