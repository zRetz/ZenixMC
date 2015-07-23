package zenixmc.command.commands.clans;

import java.util.List;

import org.bukkit.ChatColor;

import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.matt.clans.Clan;

public class ClanCommands extends AbstractClanCommand {
	public String name;

	public ClanCommands() {
		super();
	}

	@Override
	public String getName() {
		return "clan";
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
		if (args[0].equalsIgnoreCase("create")) {
			if (!sender.zui.isAuthorised("clans.create")) {
				sender.zui.sendMessage(ChatColor.RED + "You do not have the correct clearence to create a Clan!");
				return false;
			} else if (args.length == 0) {
				sender.zui.sendMessage(ChatColor.RED + "You must specify more arguments!");
				return false;
			} else if (args.length > 1) {
				sender.zui.sendMessage(ChatColor.RED + "You must specify less arguments!");
				return false;
			}
			new Clan(sender.zui, args[0]);
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(ZenixCommandSender sender, String alias,
			String[] args) {
		return null;
	}

}
