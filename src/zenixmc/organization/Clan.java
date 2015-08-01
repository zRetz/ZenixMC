package zenixmc.organization;

import java.util.ArrayList;
import java.util.List;

public class Clan implements Organization {

	private OrganizationPlayerInterface player;
	private String name;
	private String[] desc;

	public Clan(OrganizationPlayerInterface player, String name, String[] desc) {
		this.player = player;
		this.name = name;
		this.desc = desc;

		player.setClan(this);
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
	public OrganizationPlayerInterface getLeader() {
		return player;
	}

	@Override
	public List<OrganizationPlayerInterface> getPlayers() {
		List<OrganizationPlayerInterface> l = new ArrayList<>();
		for (OrganizationPlayerInterface p : organizations.values()) {
			if (!organizations.containsKey(this)) {
				return null;
			}
			l.add(p);
		}
		return l;
	}

	@Override
	public void sendInvite(OrganizationPlayerInterface player) {
		invites.put(player, this);
		player.getZenixUser().addMail("You have been invited to:" + this.getName());
		
	}

	@Override
	public void disband() {
		if (organizations.containsKey(this)) {
			
		}
		for (OrganizationPlayerInterface p : organizations.values()) {
			p.setClan(null);
			p.getZenixUser().addMail("The clan you we're in, " + this.getName() + " has disbanded!");
		}
		
	}
}
