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

public class ClanNeedInviteCommand extends AbstractClanCommand {

	public ClanNeedInviteCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager) {
		super(zenix, manager, orgManager);
	}

	@Override
	public String getName() {
		return "needinvite";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"ni"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[boolean] || [clan name] [boolean]";
	}

	@Override
	public String getDescription() {
		return "Sets need for an invitation to join a clan.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		switch (args.length) {
		case 1:
			
			OrganizationPlayerInterface orgplayer = sender.zui.getOrganizationPlayer();
			
			if (orgplayer.hasClan()) {
				
				Clan c = orgplayer.getClan();
				
				if (!(JavaUtil.canBeBoolean(args[0]))) {
					sender.zui.sendMessage(StringFormatter.format("That is not a boolean value.", MessageOccasion.ERROR, zenix));
					return true;
				}
				
				boolean v = Boolean.getBoolean(args[0]);
				
				orgManager.setNeedInviteClan(c, v, sender.zui);
				return true;
			}else {
				sender.zui.sendMessage(StringFormatter.format("Not a valid clan.", MessageOccasion.ERROR, zenix));
				return false;
			}
		case 2:
			
			if (orgManager.clanNameUsed(args[0])) {
				
				Clan c = orgManager.getClan(args[0], false);
				
				if (!(JavaUtil.canBeBoolean(args[1]))) {
					sender.zui.sendMessage(StringFormatter.format("That is not a boolean value.", MessageOccasion.ERROR, zenix));
					return true;
				}
				
				boolean v = Boolean.getBoolean(args[1]);
				
				orgManager.setNeedInviteClan(c, v, sender.zui);
				return true;
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

