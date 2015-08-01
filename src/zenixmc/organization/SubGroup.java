package zenixmc.organization;

import java.util.List;

public class SubGroup extends Clan {
	
	public OrganizationPlayerInterface leader;
	public List<OrganizationPlayerInterface> players;
	public String name;
	public String title;
	public String[] desc;
	
	public SubGroup(OrganizationPlayerInterface leader, List<OrganizationPlayerInterface> players, String name, String title, String[] desc) {
		super(leader, name, desc);
		
		this.leader = leader;
		this.players = players;
		this.name = name;
		this.title = title;
		this.desc = desc;
		
		subgroups.add(this);
	}
	
	//OGet subgroup name
	
	public String getName() {
		return name;
	}
	
	//Get subgroup description
	
	public String[] getDescription() {
		return desc;
	}
	
	//Get players in the subgroup
	
	public List<OrganizationPlayerInterface> getPlayers() {
		return players;
	}
	
	//Disband the subgroup
	
	public void disband() {
		subgroups.remove(this);
		for (OrganizationPlayerInterface o : getPlayers()) {
			o.getZenixUser().addMail("Your subgroup: " + this.getName() + " has disbanded!");
		}
		
	}
	
	//Remove a player from the subgroup
	
	public void removePlayer(OrganizationPlayerInterface player) {
		players.remove(player);
		player.getZenixUser().addMail("You have been added to the subgroup: " + this.getName() + " !");
	}
	
	//Add a player to a subgroup
	
	public void addPlayer(OrganizationPlayerInterface player) {
		players.add(player);
		player.getZenixUser().addMail("You have been added to the subgroup: " + this.getName() + " !");
	}
	
	//Get clan chat title
	
	public String getTitle() {
		return title;
	}
	
	//Set subgroup title
	
	public String setTitle(String title) {
		if (title != null) {
			for (OrganizationPlayerInterface o : getPlayers()) {
				o.getZenixUser().addMail("Your subgroup, " + this.getName() + " has had it's title(s) changed to: " + title + " !");
			}
			return this.title = new String(title);
		}
		return null;
	}
}
