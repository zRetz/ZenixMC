package zenixmc.command.commands.essentials;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.commands.AbstractMainCommand;
import zenixmc.user.ZenixUserManager;

/**
 * Base for essential commands.
 * @author james
 */
public abstract class AbstractEssentialsCommand extends AbstractMainCommand {
	
	public AbstractEssentialsCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}
	
	@Override
	public String getTitle() {
		return "Essential";
	}
	
}
