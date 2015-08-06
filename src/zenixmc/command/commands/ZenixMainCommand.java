package zenixmc.command.commands;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserManager;

public class ZenixMainCommand extends AbstractMainCommand {

	public ZenixMainCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}

	@Override
	public String getName() {
		return "z";
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Zenix Command";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		return false;
	}

	@Override
	public String getTitle() {
		return "Zenix";
	}

}
