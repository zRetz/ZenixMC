package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanAboutCommand extends AbstractClanCommand {

	public ClanAboutCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "about";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"a"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[clanname] || [username]";
	}

	@Override
	public String getDescription() {
		return "Returns information about a clan.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length < 1) {
			sender.zui.sendMessage(StringFormatter.format("Not enough arguments.", MessageOccasion.ERROR, zenix));
		}
		
		if (args.length == 1) {
			if (orgManager.getClanFromReference(args[0]) != null) {
				Clan c = orgManager.getClanFromReference(args[0]);
				for (int i = 0; i < c.about().length; i++) {
					sender.zui.sendMessage(c.about()[i]);
				}
				return true;
			}else {
				sender.zui.sendMessage(StringFormatter.format("Can't reference anything from arguments.", MessageOccasion.ERROR, zenix));
				return true;
			}	
		}else {
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
