package zenixmc.command.commands.bending;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.AbilityManager;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.command.commands.AbstractMainCommand;
import zenixmc.command.commands.CommandInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;

public class BendingMainCommand extends AbstractMainCommand {

	public BendingMainCommand(ZenixMCInterface zenix, ZenixUserManager manager, AbilityManager abilityManager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
		this.addSubCommand(new BendingElementCommand(zenix, manager, abilityManager));
		this.addSubCommand(new BendingBindCommand(zenix, manager, abilityManager));
	}

	@Override
	public String getName() {
		return "bending";
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[args]";
	}

	@Override
	public String getDescription() {
		return "Bending Command.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length == 0 ) {
			return false;
		}
		
		CommandInterface subCommand = findSubCommandByAlias(args[0]);
		
		if (subCommand != null) {
			if (!(subCommand.onCommand(sender, args[0], JavaUtil.removeElementsFromArray(args, String.class, 0)))) {
				showSubHelp(sender, subCommand);
			}
			return true;
		}
		
		return false;
	}

	@Override
	public String getTitle() {
		return "Bending";
	}

}

