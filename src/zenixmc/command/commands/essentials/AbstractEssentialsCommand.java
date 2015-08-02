package zenixmc.command.commands.essentials;

import zenixmc.ZenixMCInterface;
import zenixmc.command.commands.AbstractMainCommand;
import zenixmc.user.ZenixUserManager;

/**
 * Base for essential commands.
 * @author james
 */
public abstract class AbstractEssentialsCommand extends AbstractMainCommand {
	
	/**
	 * Repository to get user data.
	 */
	protected final ZenixUserManager manager;
	
	public AbstractEssentialsCommand(ZenixMCInterface zenix, ZenixUserManager manager) {
		super(zenix);
		this.manager = manager;
	}
	
}
