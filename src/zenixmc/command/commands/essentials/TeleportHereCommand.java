package zenixmc.command.commands.essentials;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUser;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class TeleportHereCommand extends AbstractEssentialsCommand {

	public TeleportHereCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}
	
	@Override
	public String getName() {
		return "tphere";
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[user name]";
	}

	@Override
	public String getDescription() {
		return "Teleports user to you.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (!(sender.zui.isAuthorised("zenix.essential.tphere"))) {
			sender.zui.sendMessage(StringFormatter.format("You don't have permission to do this.", MessageOccasion.ERROR, zenix));
			return true;
		}
		
		if (args.length > 1) {
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		if (args.length < 1) {
			sender.zui.sendMessage(StringFormatter.format("Not enough arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		if (manager.isOnline(args[0])) {
			ZenixUser zu = manager.getZenixUser(args[0]);
			
			sender.zui.getTeleport().teleportHereUser(zu, zenix.getSettings().getTeleportTime() == 0 ? false : true, zenix.getSettings().canMoveBeforeTeleport(), zenix.getSettings().getTeleportTime());
			return true;
		}else {
			sender.zui.sendMessage(StringFormatter.format("Not valid user.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
