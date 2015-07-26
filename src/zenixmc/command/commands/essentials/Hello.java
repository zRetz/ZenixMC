package zenixmc.command.commands.essentials;

import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserManager;

/**
 * Test Command which returns 'Hello'.
 * @author james
 */
public class Hello extends AbstractEssentialsCommand {

	public Hello(ZenixMCInterface zenix, ZenixUserManager manager) {
		super(zenix, manager);
	}
	
	@Override
	public String getName() {
		return "hello";
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
		return "[no args]";
	}

	@Override
	public String getDescription() {
		return "Returns 'Hello.'";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length == 0) {
			sender.zui.sendMessage(zenix.getSettings().getNotificationColor() + "Zenix greets you!");
			return true;
		}else {
			sender.zui.sendMessage(zenix.getSettings().getErrorColor() + "Not enough arguments.");
			return true;
		}
	}

	@Override
	public List<String> onTabComplete(ZenixCommandSender sender, String alias, String[] args) {
		return null;
	}

}
