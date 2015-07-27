package zenixmc.command.commands.clans;

import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.Clan;
import zenixmc.organization.Message;
import zenixmc.organization.Message.Type;

public class ClanCommands extends AbstractClanCommand {
	protected int commandsPerPage = 10;
	public String name;

	public ClanCommands(ZenixMCInterface zenix) {
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
		if (args[0].equalsIgnoreCase("create")) {
			if (!sender.zui.isAuthorised("clans.create")) {
				new Message(sender.zui, "No Permissions!", Type.ERROR);
				return false;
			} else if (args.length == 1) {
				new Message(sender.zui, "Not enough Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan create (name)!", Type.USAGE);
				return false;
			} else if (args.length > 2) {
				new Message(sender.zui, "Too many Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan create (name)!", Type.USAGE);
				return false;
			} else {
				new Clan(sender.zui, args[1]);
			}
		} else if (args[0].equalsIgnoreCase("join")) {
			if (!sender.zui.isAuthorised("clans.join")) {
				new Message(sender.zui, "No Permissions!", Type.ERROR);
				return false;
			} else if (args.length == 1) {
				new Message(sender.zui, "Not enough Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan join (name)!", Type.USAGE);
				return false;
			} else if (args.length > 2) {
				new Message(sender.zui, "Too many Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan join (name)!", Type.USAGE);
				return false;
			} else {
				Clan.join(sender.zui, Clan.getClan(sender.zui));
			}
		} else if (args[0].equalsIgnoreCase("leave")) {
			if (!sender.zui.isAuthorised("clans.leave")) {
				new Message(sender.zui, "No Permissions!", Type.ERROR);
				return false;
			} else if (args.length > 1) {
				new Message(sender.zui, "Too many Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan leave!", Type.USAGE);
				return false;
			} else {
				Clan.leave(sender.zui);
			}
		} else if (args[0].equalsIgnoreCase("disband")) {
			if (!sender.zui.isAuthorised("clans.disband")) {
				new Message(sender.zui, "No Permissions!", Type.ERROR);
				return false;
			} else if (args.length > 1) {
				new Message(sender.zui, "Too many Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan disband!", Type.USAGE);
				return false;
			} else {
				Clan.disband(sender.zui);
			}
		} else if (args[0].equalsIgnoreCase("kick")) {
			if (!sender.zui.isAuthorised("clans.kick")) {
				new Message(sender.zui, "No Permissions!", Type.ERROR);
				return false;
			} else if (args.length == 1) {
				new Message(sender.zui, "Not enough Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan kick (name)!", Type.USAGE);
				return false;
			} else if (args.length > 2) {
				new Message(sender.zui, "Too many Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan kick (name)!", Type.USAGE);
				return false;
			} else {
				Clan.kick(sender.zui, args[1]);
			}
		} else if (args[0].equalsIgnoreCase("invites")) {
			if (!sender.zui.isAuthorised("clans.invites")) {
				new Message(sender.zui, "No Permissions!", Type.ERROR);
				return false;
			} else if (args.length > 1) {
				new Message(sender.zui, "Too many Arguments!", Type.ERROR);
				new Message(sender.zui, "/clan invites!", Type.USAGE);
				return false;
			} else {
				Clan.invites(sender.zui);
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
