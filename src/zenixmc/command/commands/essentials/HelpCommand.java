package zenixmc.command.commands.essentials;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.command.commands.AbstractMainCommand;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class HelpCommand extends AbstractEssentialsCommand {

	public HelpCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}
	
	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[maincommand] || [maincommand] [page]";
	}

	@Override
	public String getDescription() {
		return "Returns help on a main command.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length < 1) {
			sender.zui.sendMessage(StringFormatter.format("Not enough arguments.", MessageOccasion.ERROR, zenix));
			return true;
		}
		
		switch (args.length) {
		case 1:
			
			if (!(executer.isMainCommand(args[0]))) {
				sender.zui.sendMessage(StringFormatter.format("Not a valid main command.", MessageOccasion.ERROR, zenix));
				return false;
			}
			
			AbstractMainCommand c = executer.getMainCommand(args[0]);
			
			executer.showHelp(sender, 0, c);
			return true;
		case 2:
			
			if (!(executer.isMainCommand(args[0]))) {
				sender.zui.sendMessage(StringFormatter.format("Not a valid main command.", MessageOccasion.ERROR, zenix));
				return false;
			}
			
			AbstractMainCommand co = executer.getMainCommand(args[0]);
			
			if (!(JavaUtil.canBeInteger(args[1]))) {
				sender.zui.sendMessage(StringFormatter.format("Not a valid page number.", MessageOccasion.ERROR, zenix));
				return false;
			}
			
			int p = Integer.parseInt(args[1]);
			
			if (JavaUtil.isNegative(p)) {
				sender.zui.sendMessage(StringFormatter.format("Page numbers can't be negative.", MessageOccasion.ERROR, zenix));
				return false;
			}
			
			executer.showHelp(sender, p, co);
			return true;
		default:
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return true;
		}
	}

}
