package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanBanCommand extends AbstractClanCommand {

	public ClanBanCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "ban";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"b"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[username] || [clanname] [username]";
	}

	@Override
	public String getDescription() {
		return "Bans user from clan.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length < 1) {
			sender.zui.sendMessage(StringFormatter.format("Not enough arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		switch(args.length) {
		case 1:
			
			if (!(manager.isZenixUser(args[0]))) {
				sender.zui.sendMessage(StringFormatter.format("Not valid user.", MessageOccasion.ERROR, zenix));
				return false;
			}
			
			ZenixUserInterface target = manager.getRegardlessZenixUser(args[0]);
			
			orgManager.banFromClan(sender.zui.getOrganizationPlayer().getClan(), target.getOrganizationPlayer(), sender.zui);
			return true;
		case 2:
			
			if (!(orgManager.clanNameUsed(args[0]))) {
				sender.zui.sendMessage(StringFormatter.format("That is not a clan.", MessageOccasion.ERROR, zenix));
				return true;
			}
			
			Clan c = orgManager.getClan(args[0], false);
			
			if (!(manager.isZenixUser(args[0]))) {
				sender.zui.sendMessage(StringFormatter.format("Not valid user.", MessageOccasion.ERROR, zenix));
				return false;
			}
			
			ZenixUserInterface targ = manager.getRegardlessZenixUser(args[0]);
			
			orgManager.banFromClan(c, targ.getOrganizationPlayer(), sender.zui);
			return true;
		default:
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
	}

}
