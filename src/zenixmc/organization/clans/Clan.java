package zenixmc.organization.clans;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import zenixmc.ZenixMCInterface;
import zenixmc.organization.Influential;
import zenixmc.organization.Members;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.ZenixUserManager;

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
		this.desc = new String[this.zenix.getSettings().getMaxClanDescLength()];
		this.members = new Members(leader);
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String[] getDescription() {
		return desc;
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
	public void addMember(OrganizationPlayerInterface player) {
		if (!(members.isMember(player))) {
			members.addMember(player);
			player.setClan(this);
		}
		
	}

	@Override
	public void removeMember(OrganizationPlayerInterface player) {
		if (members.isMember(player)) {
			members.removeMember(player);
			player.setClan(null);
		}
	}

	@Override
	public void invite(OrganizationPlayerInterface player) {
		if (!(player.hasInviteFor(name))) {
			player.addInviteRequest(name);
		}
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
		disbanded = true;
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
		
		for (OrganizationPlayerInterface o : members.getMembers()) {
			result += o.getInfluence();
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
