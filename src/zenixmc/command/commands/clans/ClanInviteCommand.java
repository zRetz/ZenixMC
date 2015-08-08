package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUser;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanInviteCommand extends AbstractClanCommand {

	public ClanInviteCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "invite";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"i"};
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
		return "Invites an user to clan.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		switch (args.length) {
		case 1:
			
			if (!(sender.zui.getOrganizationPlayer().hasClan())) {
				sender.zui.sendMessage(StringFormatter.format("Can't find clan to invite to.", MessageOccasion.ERROR, zenix));
				return true;
			}
			
			Clan c = sender.zui.getOrganizationPlayer().getClan();
			
			if (manager.isZenixUser(args[0])) {
				if (manager.isOnline(args[0])) {
					ZenixUser t = manager.getZenixUser(args[0]);
					
					if (c.isMember(t.getOrganizationPlayer())) {
						sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("<zenixUser> is already apart of this clan.", t), MessageOccasion.ERROR, zenix));
						return true;
					}
					
					if (!(orgManager.inviteToClan(c, t.getOrganizationPlayer(), sender.zui))) {
						sender.zui.sendMessage(StringFormatter.format(StringFormatter.format("<zenixUser> has already been invited.", t), MessageOccasion.ERROR, zenix));
					}
				}else {
					sender.zui.sendMessage(StringFormatter.format("User not online.", MessageOccasion.ERROR, zenix));
				}
			}else {
				sender.zui.sendMessage(StringFormatter.format("Not a valid user.", MessageOccasion.ERROR, zenix));
			}
			return true;
		case 2:
			
		default:
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return true;
		}
	}

}
