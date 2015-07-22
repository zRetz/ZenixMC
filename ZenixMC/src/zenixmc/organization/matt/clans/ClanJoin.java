package zenixmc.organization.matt.clans;


import zenixmc.organization.Organization;
import zenixmc.user.ZenixUserInterface;

public class ClanJoin {
	@SuppressWarnings("unused")
	private ZenixUserInterface zui;
	@SuppressWarnings("unused")
	private Clan clan;

	public ClanJoin(ZenixUserInterface zui, Clan clan) {
		if (Organization.clanInvites.containsKey(zui)
				&& Organization.clanInvites.containsValue(clan)) {
			Organization.clanInvites.remove(zui);
			Organization.playerClan.put(zui, clan);
		}
	}
}
