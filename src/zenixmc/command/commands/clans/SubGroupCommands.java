package zenixmc.command.commands.clans;

import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;

public class SubGroupCommands extends AbstractClanCommand {

	public SubGroupCommands(ZenixMCInterface zenix) {
		super(zenix);
	}

	@Override
	public String getName() {
		return "sub";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "s" };
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label,
			String[] args) {
		return false;
	}

	@Override
	public List<String> onTabComplete(ZenixCommandSender sender, String alias,
			String[] args) {
		return null;
	}

}
