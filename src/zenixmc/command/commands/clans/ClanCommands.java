package zenixmc.command.commands.clans;

import java.util.List;

import org.bukkit.ChatColor;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.command.commands.CommandInterface;
import zenixmc.organization.Clan;
import zenixmc.organization.matt.clans.Message;
import zenixmc.organization.matt.clans.Message.Type;

public class ClanCommands extends AbstractClanCommand {
	protected int commandsPerPage = 10;
	public String name;
	
	public public ClanCommands(ZenixMCInterface zenix) {
		super(zenix);
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
		if (args.length == 0) {
			
		}
		if (args[0].equalsIgnoreCase("invite")) {
			if (!sender.zui.isAuthorised("clans.invite")) {
				new Message(sender.zui, "No Permissions!", Type.ERROR);
				return false;
			} else if (args.length == 1) {
				new Message(sender.zui, "Not enough Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan invite (player)!", Type.USAGE);
				return false;
			} else if (args.length > 2) {
				new Message(sender.zui, "Too many Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan invite (player)!", Type.USAGE);
				return false;
			} else {
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(ZenixCommandSender sender, String alias,
			String[] args) {
		return null;
	}

}
