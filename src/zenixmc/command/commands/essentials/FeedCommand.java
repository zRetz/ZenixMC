package zenixmc.command.commands.essentials;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;

public class FeedCommand extends AbstractEssentialsCommand {

	public FeedCommand(ZenixMCInterface zenix, ZenixUserManager manager) {
		super(zenix, manager);
	}
	
	@Override
	public String getName() {
		return "feed";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[no args | user]";
	}

	@Override
	public String getDescription() {
		return "Feeds a user.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		switch (args.length) {
		case 0:
			sender.zui.setExhaustion(0);
			sender.zui.setFoodLevel(20);
			sender.zui.sendMessage(zenix.getSettings().getNotificationColor() + "You have been fed.");
			return true;
		case 1:
			if (manager.isZenixUser(args[0])) {
				ZenixUserInterface target = manager.getZenixUser(args[0]);
				target.setExhaustion(0);
				target.setFoodLevel(20);
				target.sendMessage(zenix.getSettings().getNotificationColor() + "You have been fed.");
				sender.zui.sendMessage(zenix.getSettings().getNotificationColor() + "You have fed " + target.getName() + ".");
				return true;
			}else {
				return false;
			}
		default:
			sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Too many arguements.");
			return false;
		}
	}

}
