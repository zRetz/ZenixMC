package zenixmc.command.commands.essentials;

import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.DateUtil;
import zenixmc.utils.JavaUtil;

public class WarningDecrementCommand extends AbstractEssentialsCommand {

	public WarningDecrementCommand(ZenixMCInterface zenix, ZenixUserManager manager) {
		super(zenix, manager);
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
			sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Not enough arguments.");
			return false;
		}
		
		if (args.length > 2) {
			sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Too many arguments.");
			return false;
		}
		
		if (manager.isZenixUser(args[0])) {
			ZenixUserInterface zui = manager.getZenixUser(args[0]);
			String[] reason = JavaUtil.removeElementsFromArray(args, String.class, 0, 1, 2);
			zui.decrementWarning(reason);
			return true;
		}else {
			sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Not valid user.");
			return false;
		}
	}

}
