package zenixmc.command.commands.clans;

import java.util.List;

import org.bukkit.entity.Player;

import zenixmc.bending.AbilityManager;
import zenixmc.command.ZenixCommandSender;
import zenixmc.organization.matt.clans.Clan;

public class createClan extends AbstractClanCommand {
	public String name;

	public createClan(AbilityManager abilityManager) {
		super(abilityManager);
	}

	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "c" };
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;

		if (!(player.hasPermission("clans.create"))) {
			return true;
		} else if (args.length == 0) {
			return true;
		} else if (args.length > 1) {
			return true;
		}
		String name = args[0];
		new Clan(player, name);
		this.name = name;
		return false;
	}

	@Override
	public List<String> onTabComplete(ZenixCommandSender sender, String alias,
			String[] args) {
		return null;
	}

}
