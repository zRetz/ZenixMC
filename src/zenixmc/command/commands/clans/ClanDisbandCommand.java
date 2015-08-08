package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanDisbandCommand extends AbstractClanCommand {

	public ClanDisbandCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "disband";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"d"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[no args] || [clanname]";
	}

	@Override
	public String getDescription() {
		return "Disband a clan.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		switch(args.length) {
		case 0:
			OrganizationPlayerInterface o = sender.zui.getOrganizationPlayer();
			
			if (!(o.hasClan())) {
				sender.zui.sendMessage(StringFormatter.format("You're not in a clan.", MessageOccasion.ERROR, zenix));
				return true;
			}
			
			if (!(orgManager.disbandClan(o.getClan(), sender.zui))) {
				sender.zui.sendMessage(StringFormatter.format("Failed to disband clan.", MessageOccasion.ERROR, zenix));
			}
			return true;
		case 1:
			
			if (!(orgManager.clanNameUsed(args[0]))) {
				sender.zui.sendMessage(StringFormatter.format("That is not a clan.", MessageOccasion.ERROR, zenix));
				return true;
			}
			
			Clan c = orgManager.getClan(args[0], false);
			
			if (!(orgManager.disbandClan(c, sender.zui))) {
				sender.zui.sendMessage(StringFormatter.format("Failed to disband clan.", MessageOccasion.ERROR, zenix));
			}
			return true;
		default:
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
	}

}
