package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.commands.AbstractCommand;
import zenixmc.organization.OrganizationManager;
import zenixmc.user.ZenixUserManager;

public abstract class AbstractClanCommand extends AbstractCommand {
	
	protected final OrganizationManager orgManager;
	
    public AbstractClanCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) { 
        super(zenix, manager);
        this.orgManager = orgManager;
    }
}
