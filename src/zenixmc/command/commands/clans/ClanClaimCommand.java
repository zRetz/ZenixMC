package zenixmc.command.commands.clans;

import zenixmc.ZenixMCInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.clans.Territory;
import zenixmc.organization.clans.TerritoryManager;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

public class ClanClaimCommand extends AbstractClanCommand {
	
	private TerritoryManager terManager;
	
	public ClanClaimCommand(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager, TerritoryManager terManager) {
		super(zenix, manager, orgManager);
		this.terManager = terManager;
	}

	@Override
	public String getName() {
		return "claim";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"cl"};
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[auto]";
	}

	@Override
	public String getDescription() {
		return "Claims land.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		switch (args.length) {
		case 0:
			
			Territory t = terManager.getTerritory(sender.zui.getLocation().getChunk());
			
			if (t == null) {
				t = terManager.createTerritory(sender.zui.getLocation().getChunk(), sender.zui.getOrganizationPlayer().getClan());
			}
			
			if (!(orgManager.claimTerritory(sender.zui.getOrganizationPlayer().getClan(), sender.zui.getOrganizationPlayer(), t))) {
				sender.zui.sendMessage(StringFormatter.format("Failed to claim chunk.", MessageOccasion.ERROR, zenix));
			}
			return true;
		case 1:
		default:
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return true;
		}
	}

}
