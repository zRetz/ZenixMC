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
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getDesc() {
		return desc;
	}

}
