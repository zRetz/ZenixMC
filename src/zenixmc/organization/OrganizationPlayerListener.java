package zenixmc.organization;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.organization.clan.ClanInviteEvent;
import zenixmc.event.organization.clan.ClanJoinEvent;
import zenixmc.event.organization.clan.ClanReDescEvent;
import zenixmc.event.organization.clan.ClanReNameEvent;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class OrganizationPlayerListener extends OrganizationListener {

	public OrganizationPlayerListener(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager,
			EventDispatcher eventDispatcher) {
		super(zenix, manager, orgManager, eventDispatcher);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onReDescClan(ClanReDescEvent e) {
		
		if (e.isCancelled()) {
			return;
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onReNameClan(ClanReNameEvent e) {
		
		if (e.isCancelled()) {
			return;
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onClanInvite(ClanInviteEvent e) {
		
		if (e.isCancelled()) {
			return;
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onClanJoin(ClanJoinEvent e) {
		
		if (e.isCancelled()) {
			return;
		}

	}
}
