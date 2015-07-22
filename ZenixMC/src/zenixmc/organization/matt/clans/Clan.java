package zenixmc.organization.matt.clans;

import org.bukkit.entity.Player;

import zenixmc.organization.Organization;

public class Clan implements Organization {
	public Player player;
	public String name;
	public String[] desc;

	public Clan(Player player, String name) {
		this.player = player;
		this.name = name;
		
	Clans.add(this);
	playerClan.put(player, this);
		
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
	public Player[] getMembers() {
		if (playerClan.containsKey(player) && playerClan.containsValue(this)) {
			return null; //Todo return with a list of the clans players
		}
		return null;
	}
}
