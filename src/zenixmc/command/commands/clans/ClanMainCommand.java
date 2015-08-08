package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.command.commands.AbstractMainCommand;
import zenixmc.command.commands.CommandInterface;
import zenixmc.organization.OrganizationManager;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;

public class ClanMainCommand extends AbstractMainCommand {

	public ClanMainCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
		this.addSubCommand(new ClanCreateCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanAboutCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanNameCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanDescCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanInviteCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanJoinCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanLeaveCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanKickCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanDisbandCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanBanCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanPardonCommand(zenix, manager, orgManager));
		this.addSubCommand(new ClanNeedInviteCommand(zenix, manager, orgManager));
	}

	@Override
	public String getName() {
		return "clan";
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[args]";
	}

	@Override
	public String getDescription() {
		return "Clan Command.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		if (args.length == 0 ) {
			return false;
		}
		
		CommandInterface subCommand = findSubCommandByAlias(args[0]);
		
		if (subCommand != null) {
			if (!(subCommand.onCommand(sender, args[0], JavaUtil.removeElementsFromArray(args, String.class, 0)))) {
				showSubHelp(sender, subCommand);
			}
			return true;
		}
		
		return false;
	}

	@Override
	public String getTitle() {
		return "Clan";
	}

}
