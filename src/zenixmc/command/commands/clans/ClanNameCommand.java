package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanNameCommand extends AbstractClanCommand {

	public ClanNameCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "name";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"n"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[new name] || [clan name] [new name]";
	}

	@Override
	public String getDescription() {
		return "Sets the name of a clan.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		switch (args.length) {
		case 1:
			OrganizationPlayerInterface orgplayer = sender.zui.getOrganizationPlayer();
			if (orgplayer.getClan() != null) {
				Clan c = orgplayer.getClan();
				orgManager.setClanName(c, sender.zui, args[0]);
				return true;
			}else {
				sender.zui.sendMessage(StringFormatter.format("Not a valid clan.", MessageOccasion.ERROR, zenix));
				return false;
			}
		case 2:
			if (orgManager.clanNameUsed(args[0])) {
				Clan c = orgManager.getClan(args[0], false);
				if (!(orgManager.clanNameUsed(args[1]))) {
					orgManager.setClanName(c, sender.zui, args[1]);
					return true;
				}else {
					sender.zui.sendMessage(StringFormatter.format("Clan name already in use.", MessageOccasion.ERROR, zenix));
					return true;
				}
			}else {
				sender.zui.sendMessage(StringFormatter.format("Not a valid clan.", MessageOccasion.ERROR, zenix));
				return false;
			}
		default:
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}