package zenixmc.command.commands.essentials;

import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.DateUtil;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class WarningDecrementCommand extends AbstractEssentialsCommand {

	public WarningDecrementCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}
	
	@Override
	public String getName() {
		return "unwarn";
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[user] [reason]";
	}

	@Override
	public String getDescription() {
		return "Decrements a users warnings.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length < 2) {
			sender.zui.sendMessage(StringFormatter.format("Not enough arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		if (args.length > 2) {
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		if (manager.isZenixUser(args[0])) {
			ZenixUserInterface zui = manager.getZenixUser(args[0]);
			String[] reason = JavaUtil.removeElementsFromArray(args, String.class, 0, 1, 2);
			zui.decrementWarning(reason);
			return true;
		}else {
			sender.zui.sendMessage(StringFormatter.format("Not valid user.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
