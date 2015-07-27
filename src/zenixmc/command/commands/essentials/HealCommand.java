package zenixmc.command.commands.essentials;

import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;

/**
 * Commad which heals users.
 * @author james
 */
public class HealCommand extends AbstractEssentialsCommand {

	public HealCommand(ZenixMCInterface zenix, ZenixUserManager manager) {
		super(zenix, manager);
	}
	
	@Override
	public String getName() {
		return "heal";
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
		return "Heals a user.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		switch (args.length) {
		case 0:
			sender.zui.setHealth(sender.zui.getMaxHealth());
			sender.zui.sendMessage(zenix.getSettings().getNotificationColor() + "You have been healed.");
			return true;
		case 1:
			if (manager.isZenixUser(args[0])) {
				ZenixUserInterface target = manager.getZenixUser(args[0]);
				target.setHealth(target.getMaxHealth());
				target.sendMessage(zenix.getSettings().getNotificationColor() + "You have been healed.");
				sender.zui.sendMessage(zenix.getSettings().getNotificationColor() + "You have healed " + target.getName() + ".");
				return true;
			}else {
				return false;
			}
		default:
			sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Too many arguements.");
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(ZenixCommandSender sender, String alias, String[] args) {
		return null;
	}

}
