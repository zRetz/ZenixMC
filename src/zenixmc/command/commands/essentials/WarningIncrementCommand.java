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

public class WarningIncrementCommand extends AbstractEssentialsCommand {

	public WarningIncrementCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}
	
	@Override
	public String getName() {
		return "warn";
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
			sender.zui.sendMessage(StringFormatter.format("Not enough arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		System.out.println(args[0]);
		System.out.println(args[1]);
		
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
					sender.zui.sendMessage(StringFormatter.format("Cannot be an integer.", MessageOccasion.ERROR, zenix));
					return false;
				}
			}else {
				sender.zui.sendMessage(StringFormatter.format("Not valid format.", MessageOccasion.ERROR, zenix));
				return false;
			}
		}else {
			sender.zui.sendMessage(StringFormatter.format("Not valid user.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
