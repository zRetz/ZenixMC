package zenixmc.command.commands.essentials;

import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserManager;

/**
 * Test Command which returns 'Hello'.
 * @author james
 */
public class HelloCommand extends AbstractEssentialsCommand {

	public HelloCommand(ZenixMCInterface zenix, ZenixUserManager manager) {
		super(zenix, manager);
	}
	
	@Override
	public String getName() {
		return "hello";
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
		return "Returns 'Hello.'";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		switch (args.length) {
		case 0:
			sender.zui.sendMessage(zenix.getSettings().getNotificationColor() + "Zenix greets you!");
			return true;
		default:
			sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Too many arguments.");
			return true;
		}
	}

	@Override
	public List<String> onTabComplete(ZenixCommandSender sender, String alias, String[] args) {
		return null;
	}

}
