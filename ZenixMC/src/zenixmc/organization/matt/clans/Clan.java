package zenixmc.organization.matt.clans;

import org.bukkit.entity.Player;

import zenixmc.organization.Organization;
import zenixmc.user.ZenixUserInterface;

public class Clan implements Organization {
    
	public ZenixUserInterface zui;
	private String name;
	private String[] desc;

	public Clan(ZenixUserInterface zui, String name) {
		this.zui = zui;
		this.name = name;
		
	Clans.add(this);
	playerClan.put(zui, this);
		
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
		if (playerClan.containsKey(zui) && playerClan.containsValue(this)) {
			return null; //Todo return with a list of the clans players
		}
		return null;
	}
}
