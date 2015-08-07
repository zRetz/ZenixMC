package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanDescCommand extends AbstractClanCommand {

	public ClanDescCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "description";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"desc"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[new description] || [clan name] [new description]";
	}

	@Override
	public String getDescription() {
		return "Sets the description of a clan.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length < 1) {
			sender.zui.sendMessage(StringFormatter.format("Not enough arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		if (orgManager.clanNameUsed(args[0])) {
			Clan c = orgManager.getClan(args[0], false);
			
			String[] nargs = JavaUtil.removeElementsFromArray(args, String.class, 0);
			
			if (nargs.length < zenix.getSettings().maxClanDescLength()) {
				orgManager.setClanDescription(c, sender.zui, nargs);
				return true;
			}else {
				sender.zui.sendMessage(StringFormatter.format("Description is too big.", MessageOccasion.ERROR, zenix));
				return true;
			}
		}
		
		OrganizationPlayerInterface orgPlayer = sender.zui.getOrganizationPlayer();
		
		if (orgPlayer.getClan() != null) {
			Clan c = orgPlayer.getClan();
			
			if (args.length < zenix.getSettings().maxClanDescLength()) {
				orgManager.setClanDescription(c, sender.zui, args);
				return true;
			}else {
				sender.zui.sendMessage(StringFormatter.format("Description is too big.", MessageOccasion.ERROR, zenix));
				return true;
			}
		}else {
			sender.zui.sendMessage(StringFormatter.format("Not valid clan.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
