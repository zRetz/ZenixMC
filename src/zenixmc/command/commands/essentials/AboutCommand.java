package zenixmc.command.commands.essentials;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class AboutCommand extends AbstractEssentialsCommand {

	public AboutCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}
	
	@Override
	public String getName() {
		return "about";
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[no args]";
	}

	@Override
	public String getDescription() {
		return "Returns 'Hello.'";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		switch (args.length) {
		case 0:
			sender.zui.sendMessage(StringFormatter.format("Zenix greets you!", MessageOccasion.ESSENTIAL, zenix));
			return true;
		default:
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return true;
		}
	}

}
