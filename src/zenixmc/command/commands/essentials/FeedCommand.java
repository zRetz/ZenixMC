package zenixmc.command.commands.essentials;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class FeedCommand extends AbstractEssentialsCommand {

	public FeedCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
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
		return "[no args] || [user]";
	}

	@Override
	public String getDescription() {
		return "Feeds a user.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (!(sender.zui.isAuthorised("zenix.essential.feed"))) {
			sender.zui.sendMessage(StringFormatter.format("You don't have permission to do this.", MessageOccasion.ERROR, zenix));
			return true;
		}
		
		switch (args.length) {
		case 0:
			sender.zui.setExhaustion(0);
			sender.zui.setFoodLevel(20);
			sender.zui.sendMessage(StringFormatter.format("You have been fed.", MessageOccasion.ESSENTIAL, zenix));
			return true;
		case 1:
			if (manager.isZenixUser(args[0])) {
				ZenixUserInterface target = manager.getZenixUser(args[0]);
				target.setExhaustion(0);
				target.setFoodLevel(20);
				target.sendMessage(StringFormatter.format("You have been fed.", MessageOccasion.ESSENTIAL, zenix));
				sender.zui.sendMessage(StringFormatter.format("You have fed " + target.getName() + ".", MessageOccasion.ESSENTIAL, zenix));
				return true;
			}else {
				return false;
			}
		default:
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
