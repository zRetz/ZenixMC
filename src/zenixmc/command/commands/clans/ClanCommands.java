package zenixmc.command.commands.clans;

import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;

public class ClanCommands extends AbstractClanCommand {
	
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
		switch(args.length) {
		case 0:
			break;
		case 1:
			if (args[0].equalsIgnoreCase("invite")) {
				if(sender.zui.isAuthorised("clan.invite")) {
					if (args.length > 2) {
						sender.zui.sendMessage("You have invited " +  args[1] + " to the Clan!");
						sender.zui.getOrganizationPlayer().getClan().sendInvite(null);
						break;
					}
				}
				break;
			} else if (args[0].equalsIgnoreCase("kick")) {
				
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
