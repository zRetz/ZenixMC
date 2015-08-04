package zenixmc.organization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Members implements Serializable {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -4964152485432802327L;
	
	/**
	 * Leader of the members.
	 */
	private OrganizationPlayerInterface leader;
	
	/**
	 * Members.
	 */
	private List<OrganizationPlayerInterface> members;
	
	/**
	 * Instantiate.
	 * @param leader
	 * 		The leader of the members.
	 */
	public Members(OrganizationPlayerInterface leader) {
		this.leader = leader;
		this.members = new ArrayList<>();
	}
	
	/**
	 * Sets the leader.
	 * @param player
	 * 		The player to set to.
	 */
	public void setLeader(OrganizationPlayerInterface player) {
		if (player != null) {
			this.leader = player;
		}
	}
	
	/**
	 * @return The leader of the members.
	 */
	public OrganizationPlayerInterface getLeader() {
		return leader;
	}
	
	/**
	 * Adds a member.
	 * @param player
	 * 		The player to add.
	 */
	public void addMember(OrganizationPlayerInterface player) {
		if (!(isMember(player))) {
			members.add(player);
		}
	}
	
	/**
	 * Removes a member.
	 * @param player
	 * 		The player to remove.
	 */
	public void removeMember(OrganizationPlayerInterface player) {
		if (isMember(player)) {
			members.remove(player);
		}
	}
	
	/**
	 * @param name
	 * 		The name of the member.
	 * @return The member.
	 */
	public OrganizationPlayerInterface getMember(String name) {
		for (OrganizationPlayerInterface o : members) {
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
		for (OrganizationPlayerInterface o : members) {
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
		return members.contains(player);
	}
	
	/**
	 * @return The raw list of members.
	 */
	public List<OrganizationPlayerInterface> getMembers() {
		
		List<OrganizationPlayerInterface> result = new ArrayList<>(members);
		result.add(leader);
		
		return result;
	}
}
