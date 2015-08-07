package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanKickCommand extends AbstractClanCommand {

	public ClanKickCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "kick";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"k"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[username]";
	}

	@Override
	public String getDescription() {
		return "Kick a user.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length < 1) {
			sender.zui.sendMessage(StringFormatter.format("Not enough arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		if (args.length > 1) {
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
		
		if (manager.isZenixUser(args[0])) {
			ZenixUserInterface zui = manager.getRegardlessZenixUser(args[0]);
			OrganizationPlayerInterface o = zui.getOrganizationPlayer();
			if (o.hasClan()) {
				if (!(orgManager.kickFromClan(o.getClan(), o, sender.zui))) {
					sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("<zenixUser> is not in a clan to be kicked from.", zui), MessageOccasion.ERROR, zenix));
				}
			}
			return true;
		}else {
			sender.zui.sendMessage(StringFormatter.format("Not valid user.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
