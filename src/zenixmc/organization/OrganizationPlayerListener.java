package zenixmc.organization;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.organization.clan.ClanUpdateEvent;
import zenixmc.user.ZenixUserManager;

public class OrganizationPlayerListener extends OrganizationListener {

	public OrganizationPlayerListener(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager,
			EventDispatcher eventDispatcher) {
		super(zenix, manager, orgManager, eventDispatcher);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onClanUpdate(ClanUpdateEvent e) {
		
		if (e.isCancelled()) {
			return;
		}
		
		orgManager.saveClan(e.getClan());
	}
}
