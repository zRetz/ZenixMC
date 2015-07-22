package zenixmc.organization.matt.clans;

import org.bukkit.entity.Player;

import zenixmc.organization.Organization;

public class ClanInvite {
	@SuppressWarnings("unused")
	private Player player;
	@SuppressWarnings("unused")
	private Clan clan;
	
	public ClanInvite(Player player, Clan clan) {
		this.player = player;
		this.clan = clan;
		
		Organization.clanInvites.put(player, clan);
	}

}
