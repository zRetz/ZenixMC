package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanCreateCommand extends AbstractClanCommand {

	public ClanCreateCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"c"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[name]";
	}

	@Override
	public String getDescription() {
		return "Creates a clan.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length == 1) {
			if (!(orgManager.clanNameUsed(args[0]))) {
				if (!(sender.zui.getOrganizationPlayer().hasClan())) {
					Clan clan = orgManager.createClan(sender.zui.getOrganizationPlayer(), args[0]);
					sender.zui.sendMessage(StringFormatter.format("You have created a clan called " + clan.getName(), MessageOccasion.CLAN, zenix));
					return true;
				}else {
					sender.zui.sendMessage(StringFormatter.format("You are already apart of a clan. Leave that one before creating another.", MessageOccasion.ERROR, zenix));
					return true;
				}
			}else {
				sender.zui.sendMessage(StringFormatter.format("Clan name already used.", MessageOccasion.ERROR, zenix));
				return true;
			}	
		}else {
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
