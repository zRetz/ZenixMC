package zenixmc.organization.matt.clans;

import org.bukkit.entity.Player;

import zenixmc.organization.Organization;

public class ClanJoin {
	@SuppressWarnings("unused")
	private Player player;
	@SuppressWarnings("unused")
	private Clan clan;

	public ClanJoin(Player player, Clan clan) {
		if (Organization.clanInvites.containsKey(player)
				&& Organization.clanInvites.containsValue(clan)) {
			Organization.clanInvites.remove(player);
			Organization.playerClan.put(player, clan);
		}
	}
}
