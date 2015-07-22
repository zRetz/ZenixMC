package zenixmc.command.commands.clans;

import java.util.List;


import zenixmc.bending.AbilityManager;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.matt.clans.Clan;

public class createClan extends AbstractClanCommand {
	public String name;

	public createClan() {
		super();
	}

	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "c" };
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

		if (!(sender.zui.isAuthorised("clans.create"))) {
			return false;
		} else if (args.length == 0) {
			return false;
		} else if (args.length > 1) {
			return true;
		}
		String name = args[0];
		new Clan(sender.zui, name);
		this.name = name;
		return true;
	}

	@Override
	public List<String> onTabComplete(ZenixCommandSender sender, String alias,
			String[] args) {
		return null;
	}

}
