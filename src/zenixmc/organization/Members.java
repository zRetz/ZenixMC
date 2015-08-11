package zenixmc.organization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import zenixmc.ZenixMC;
import zenixmc.persistance.CachedOrganizationRepository;
import zenixmc.user.ZenixUserManager;

public class Members implements Serializable {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -4964152485432802327L;
	
	/**
	 * Manager for users.
	 */
	private transient ZenixUserManager manager;
	
	/**
	 * Leader of the members.
	 */
	private UUID leader;
	
	/**
	 * Members.
	 */
	private List<UUID> members;
	
	/**
	 * Instantiate.
	 * @param leader
	 * 		The leader of the members.
	 */
	public Members(OrganizationPlayerInterface leader, ZenixUserManager manager) {
		this.manager = manager;
		if (leader == null) {
			this.leader = null;
		}else {
			this.leader = leader.getZenixUser().getUniqueId();
		}
		this.members = new ArrayList<>();
	}
	
	/**
	 * Sets the leader.
	 * @param player
	 * 		The player to set to.
	 */
	public void setLeader(OrganizationPlayerInterface player) {
		if (player != null) {
			this.leader = player.getZenixUser().getUniqueId();
		}
	}
	
	/**
	 * @return The leader of the members.
	 */
	public OrganizationPlayerInterface getLeader() {
		return manager.getRegardlessZenixUser(leader).getOrganizationPlayer();
	}
	
	/**
	 * Adds a member.
	 * @param player
	 * 		The player to add.
	 */
	public void addMember(OrganizationPlayerInterface player) {
		if (!(isMember(player))) {
			members.add(player.getZenixUser().getUniqueId());
		}
	}
	
	/**
	 * Removes a member.
	 * @param player
	 * 		The player to remove.
	 */
	public void removeMember(OrganizationPlayerInterface player) {
		if (isMember(player)) {
			members.remove(player.getZenixUser().getUniqueId());
		}
	}
	
	/**
	 * @param name
	 * 		The name of the member.
	 * @return The member.
	 */
	public OrganizationPlayerInterface getMember(String name) {
		for (OrganizationPlayerInterface o : getOnlineMembers()) {
			if (o.getZenixUser().getName().equals(name)) {
				return o;
			}
		}
		return null;
	}
	
	/**
	 * @param uuid
	 * 		The unique identifier of the member.
	 * @return The member.
	 */
	public OrganizationPlayerInterface getMember(UUID uuid) {
		for (OrganizationPlayerInterface o : getOnlineMembers()) {
			if (o.getZenixUser().getUniqueId().compareTo(uuid) == 0) {
				return o;
			}
		}
		return null;
	}
	
	/**
	 * @param player
	 * 		The player to check.
	 * @return <code>true</code> If the player is a member.
	 */
	public boolean isMember(OrganizationPlayerInterface player) {
		return isMember(player.getZenixUser().getUniqueId());
	}
	
	/**
	 * @param uuid
	 * 		The unique identifier to check.
	 * @return <code>true</code> If the unique identifier is in members.
	 */
	public boolean isMember(UUID uuid) {
		return members.contains(uuid) || leader.compareTo(uuid) == 0;
	}
	
	/**
	 * @param lead	
	 * 		Whether the leader should be included in the list.
	 * @return A list of all online members.
	 */
	public List<OrganizationPlayerInterface> getOnlineMembers() {
		
		List<OrganizationPlayerInterface> result = new ArrayList<>();
		
		for (UUID uuid : members) {
			if (manager.isOnline(uuid)) {
				result.add(manager.getZenixUser(uuid).getOrganizationPlayer());
			}
		}
		if (leader != null) {
			if (manager.isOnline(leader)) {
				result.add(getLeader());
			}
		}
		
		return result;
	}
	
	/**
	 * @param lead	
	 * 		Whether the leader should be included in the list.
	 * @return A list of all offline members.
	 */
	public List<OrganizationPlayerInterface> getOfflineMembers() {
		
		List<OrganizationPlayerInterface> result = new ArrayList<>();
		
		for (UUID uuid : members) {
			if (!(manager.isOnline(uuid))) {
				result.add(manager.getZenixUser(uuid).getOrganizationPlayer());
			}
		}
		if (leader != null) {
			if (!(manager.isOnline(leader))) {
				result.add(getLeader());
			}
		}
		
		return result;
	}
	
	/**
	 * @param
	 * 		Whether the leader should be included in the list.
	 * @return The raw list of all members regardless of online/offline.
	 */
	public List<OrganizationPlayerInterface> getMembers() {
		
		List<OrganizationPlayerInterface> result = new ArrayList<>();
		
		result.addAll(getOnlineMembers());
		result.addAll(getOfflineMembers());
		
		return result;
	}
	
	/**
	 * @param leader	
	 * 		Whether the leader should be included in the string.
	 * @return The online members in string form.
	 */
	public String onlineMembers() {
		
		StringBuilder result = new StringBuilder();
		
		List<OrganizationPlayerInterface> ons = getOnlineMembers();
		
		for (OrganizationPlayerInterface o : ons) {
			result.append(o.getZenixUser().getName());
			if (ons.indexOf(o) != ons.size()-1) {
				result.append(", ");
			}
		}
		
		return result.toString();
	}
	
	/**
	 * @param leader	
	 * 		Whether the leader should be included in the string.
	 * @return The offline members in string form.
	 */
	public String offlineMembers() {
		
		StringBuilder result = new StringBuilder();
		
		List<OrganizationPlayerInterface> ofs = getOfflineMembers();
		
		for (OrganizationPlayerInterface o : ofs) {
			result.append(o.getZenixUser().getName());
			if (ofs.indexOf(o) != ofs.size()-1) {
				result.append(", ");
			}
		}
		
		return result.toString();
	}
	
	/**
	 * @return The amount of members.
	 */
	public int size() {
		return members.size() + 1;
	}
	
	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		
		for (OrganizationPlayerInterface o : getMembers()) {
			result.append(o.getZenixUser().getName());
		}
		
		return result.toString();
	}
	
	public void setZenixUserManager(ZenixUserManager value) {
		this.manager = value;
	}
	
}
