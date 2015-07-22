package zenixmc.organization.matt.clans;


import zenixmc.organization.Organization;
import zenixmc.user.ZenixUserInterface;

public class ClanInvite {
    
	@SuppressWarnings("unused")
	private ZenixUserInterface zui;
	@SuppressWarnings("unused")
	private Clan clan;
	
	public ClanInvite(ZenixUserInterface zui, Clan clan) {
		this.zui = zui;
		this.clan = clan;
		
		Organization.clanInvites.put(zui, clan);
	}

}
