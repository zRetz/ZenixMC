package zenixmc.command.commands.essentials;

import org.bukkit.permissions.Permission;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ViewPermissionsCommand extends AbstractEssentialsCommand {

	public ViewPermissionsCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}
	
	@Override
	public String getName() {
		return "viewperms";
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[no args]";
	}

	@Override
	public String getDescription() {
		return "Returns an all registered permissions.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (!(sender.zui.isAuthorised("zenix.essential.viewperms"))) {
			sender.zui.sendMessage(StringFormatter.format("You don't have permission to do this.", MessageOccasion.ERROR, zenix));
			return true;
		}
		
		switch (args.length) {
		case 0:
			for (Permission p : zenix.getDescription().getPermissions()) {
				sender.zui.sendMessage(StringFormatter.format(p.getName(), MessageOccasion.ESSENTIAL, zenix));
			}
			return true;
		default:
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return true;
		}
	}

}