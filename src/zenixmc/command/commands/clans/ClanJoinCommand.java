package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanJoinCommand extends AbstractClanCommand {

	public ClanJoinCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "join";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"j"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[clanname] || [username] [clanname]";
	}

	@Override
	public String getDescription() {
		return "Join a clan.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length < 1) {
			sender.zui.sendMessage(StringFormatter.format("Not enough arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		if (args.length == 1) {
			if (orgManager.getClanFromReference(args[0]) != null) {
				Clan c = orgManager.getClanFromReference(args[0]);
				if (!(orgManager.joinClan(c, sender.zui.getOrganizationPlayer()))) {
					sender.zui.sendMessage(StringFormatter.format("Failed to join that clan.", MessageOccasion.ERROR, zenix));
				}
			}else {
				sender.zui.sendMessage(StringFormatter.format("Not valid clan.", MessageOccasion.ERROR, zenix));
			}
			return true;
		}else {
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
