package zenixmc.command.commands.essentials;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class GodCommand extends AbstractEssentialsCommand {

	public GodCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}

	@Override
	public String getName() {
		return "god";
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[user name] [boolean]";
	}

	@Override
	public String getDescription() {
		return "Put a user in god-mode.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {

		if (!(sender.zui.isAuthorised("zenix.essential.god"))) {
			sender.zui.sendMessage(StringFormatter.format("You don't have permission to do this.", MessageOccasion.ERROR, zenix));
			return true;
		}
		
		if (args.length > 2) {
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return true;
		}
		
		if (args.length < 2) {
			sender.zui.sendMessage(StringFormatter.format("Not enough arguments.", MessageOccasion.ERROR, zenix));
			return true;
		}
		
		if (manager.isZenixUser(args[0])) {
			ZenixUserInterface zui = manager.getRegardlessZenixUser(args[0]);
			if (JavaUtil.canBeBoolean(args[1])) {
				boolean value = Boolean.parseBoolean(args[1]);
				
				if (value) {
					if (!(zui.isGodMode())) {
						zui.setGodMode(true);
						sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("User: <zenixUser> is now in god-mode.", zui), MessageOccasion.ESSENTIAL, zenix));
						zui.sendMessage(StringFormatter.format(StringFormatter.format("You've been given god-mode by <zenixUser>.", sender.zui), MessageOccasion.ESSENTIAL, zenix));
					}else {
						sender.zui.sendMessage(StringFormatter.format("User is already in god-mode.", MessageOccasion.ERROR, zenix));
					}
				}else {
					if (zui.isGodMode()) {
						zui.setGodMode(false);
						sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("User: <zenixUser> is no longer in god-mode.", zui), MessageOccasion.ESSENTIAL, zenix));
						zui.sendMessage(StringFormatter.format(StringFormatter.format("Your god-mode has been taken away by <zenixUser>.", sender.zui), MessageOccasion.ESSENTIAL, zenix));
					}else {
						sender.zui.sendMessage(StringFormatter.format("User isn't in god-mode.", MessageOccasion.ERROR, zenix));
					}
				}
				return true;
			}else {
				sender.zui.sendMessage(StringFormatter.format("That is not a boolean value.", MessageOccasion.ERROR, zenix));
				return false;
			}
		} else {
			sender.zui.sendMessage(StringFormatter.format("Not valid user.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
