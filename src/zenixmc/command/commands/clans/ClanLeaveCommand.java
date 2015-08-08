package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanLeaveCommand extends AbstractClanCommand {

	public ClanLeaveCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "leave";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"l"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[no args]";
	}

	@Override
	public String getDescription() {
		return "Leave clan.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length > 0) {
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		if (!(orgManager.leaveClan(sender.zui.getOrganizationPlayer()))) {
			sender.zui.sendMessage(StringFormatter.format("Failed to leave clan.", MessageOccasion.ERROR, zenix));
		}
		return true;
	}

}