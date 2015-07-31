package zenixmc.command.commands.essentials;

import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.DateUtil;
import zenixmc.utils.JavaUtil;

public class WarningCommand extends AbstractEssentialsCommand {

	public WarningCommand(ZenixMCInterface zenix, ZenixUserManager manager) {
		super(zenix, manager);
	}
	
	@Override
	public String getName() {
		return "warn";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"warning"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[user] [duration_format] [duration] [reason]";
	}

	@Override
	public String getDescription() {
		return "Warns a user.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length < 4) {
			sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Not enough arguments.");
			return false;
		}
		
		if (manager.isZenixUser(args[0])) {
			ZenixUserInterface zui = manager.getZenixUser(args[0]);
			if (DateUtil.isFormat(args[1])) {
				String format = args[1];
				if (JavaUtil.canBeInteger(args[2])) {
					long duration = Long.parseLong(args[2]);
					String[] reason = JavaUtil.removeElementsFromArray(args, String.class, 0, 1, 2);
					zui.incrementWarning(DateUtil.formatDuration(format, duration), reason);
					return true;
				}else {
					sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Cannot be an integer.");
					return false;
				}
			}else {
				sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Not valid format.");
				return false;
			}
		}else {
			sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Not valid user.");
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(ZenixCommandSender sender, String alias, String[] args) {
		return null;
	}

}
