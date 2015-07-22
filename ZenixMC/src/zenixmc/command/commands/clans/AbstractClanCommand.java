package zenixmc.command.commands.clans;

import zenixmc.bending.AbilityManager;
import zenixmc.command.commands.AbstractCommand;

public abstract class AbstractClanCommand extends AbstractCommand {
	
	protected AbilityManager abilityManager;
	
    public AbstractClanCommand(AbilityManager abilityManager) {
        this.abilityManager = abilityManager;
    }
}
