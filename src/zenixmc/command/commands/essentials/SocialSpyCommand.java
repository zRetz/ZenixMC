package zenixmc.command.commands.essentials;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class SocialSpyCommand extends AbstractEssentialsCommand {

	public SocialSpyCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}

	@Override
	public String getName() {
		return "socialspy";
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
		return "Socially spy on user.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {

		if (!(sender.zui.isAuthorised("zenix.essential.socialspy"))) {
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
					if (!(zui.isSocialSpying())) {
						zui.setSocialSpy(true);
						sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("User: <zenixUser> is now frozen.", zui), MessageOccasion.ESSENTIAL, zenix));
						zui.sendMessage(StringFormatter.format(StringFormatter.format("You've been given socialspy by <zenixUser>.", sender.zui), MessageOccasion.ESSENTIAL, zenix));
					}else {
						sender.zui.sendMessage(StringFormatter.format("User is already frozen.", MessageOccasion.ERROR, zenix));
					}
				}else {
					if (zui.isSocialSpying()) {
						zui.setSocialSpy(false);
						sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("User: <zenixUser> is now un-frozen.", zui), MessageOccasion.ESSENTIAL, zenix));
						zui.sendMessage(StringFormatter.format(StringFormatter.format("Your socialspy has been taken away by <zenixUser>.", sender.zui), MessageOccasion.ESSENTIAL, zenix));
					}else {
						sender.zui.sendMessage(StringFormatter.format("User isn't in socialspy.", MessageOccasion.ERROR, zenix));
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
