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
		for (OrganizationPlayerInterface p : o.keySet()) {
			if (!o.containsKey(p)) {
				return null;
			}
			if (o.get(p) == this) {
				l.add(p);
			}
		}
		return l;
	}
}