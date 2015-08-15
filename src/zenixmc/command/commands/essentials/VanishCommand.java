package zenixmc.command.commands.essentials;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class VanishCommand extends AbstractEssentialsCommand {

	public VanishCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}

	@Override
	public String getName() {
		return "vanish";
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
		return "Vanish a user.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {

		if (!(sender.zui.isAuthorised("zenix.essential.vanish"))) {
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
					if (!(zui.isVanished())) {
						zui.setVanished(true);
						sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("User: <zenixUser> is now vanished.", zui), MessageOccasion.ESSENTIAL, zenix));
						zui.sendMessage(StringFormatter.format(StringFormatter.format("You've been vanished by <zenixUser>.", sender.zui), MessageOccasion.ESSENTIAL, zenix));
					}else {
						sender.zui.sendMessage(StringFormatter.format("User is already vanished.", MessageOccasion.ERROR, zenix));
					}
				}else {
					if (zui.isVanished()) {
						zui.setVanished(false);
						sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("User: <zenixUser> is no longer vanished.", zui), MessageOccasion.ESSENTIAL, zenix));
						zui.sendMessage(StringFormatter.format(StringFormatter.format("Your vanishment has been taken away by <zenixUser>.", sender.zui), MessageOccasion.ESSENTIAL, zenix));
					}else {
						sender.zui.sendMessage(StringFormatter.format("User isn't vanished.", MessageOccasion.ERROR, zenix));
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
