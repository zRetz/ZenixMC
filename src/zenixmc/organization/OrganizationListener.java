package zenixmc.organization;

import zenixmc.ZenixListener;
import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.user.ZenixUserManager;

public class OrganizationListener extends ZenixListener {

	/**
	 * The manager of organizations.
	 */
	protected OrganizationManager orgManager;
	
	public OrganizationListener(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager, EventDispatcher eventDispatcher) {
		super(zenix, manager, eventDispatcher);
		this.orgManager = orgManager;
	}
	
}
