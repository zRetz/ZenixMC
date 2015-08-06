package zenixmc.command.commands.essentials;

import java.util.List;

import org.bukkit.Location;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

/**
 * Teleport Command.
 * @author james
 */
public class TeleportCommand extends AbstractEssentialsCommand {

	public TeleportCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager, executer);
	}
	
	@Override
	public String getName() {
		return "teleport";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public String[] getHelp() {
		return null;
	}

	@Override
	public String getFormat() {
		return "[user] || [user] [user] || [x] [y] [z]";
	}

	@Override
	public String getDescription() {
		return "Teleports user to specified location.";
	}

	@Override
	public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
		
		System.out.println(JavaUtil.arrayToString(args));
		
		switch (args.length) {
		case 1:
			if (manager.isZenixUser(args[0])) {
				ZenixUserInterface target = manager.getZenixUser(args[0]);
				sender.zui.getTeleport().teleportToUser(target, zenix.getSettings().getTeleportTime() == 0 ? false : true, zenix.getSettings().canMoveBeforeTeleport(), zenix.getSettings().getTeleportTime());
				return true;
			}else { 
				sender.zui.sendMessage(StringFormatter.format("Not valid user.", MessageOccasion.ERROR, zenix));
				return false;
			}
		case 2:
			if (manager.isZenixUser(args[0]) && manager.isZenixUser(args[1])) {
				ZenixUserInterface teleportee = manager.getZenixUser(args[0]);
				ZenixUserInterface target = manager.getZenixUser(args[0]);
				teleportee.getTeleport().teleportToUser(target, zenix.getSettings().getTeleportTime() == 0 ? false : true, zenix.getSettings().canMoveBeforeTeleport(), zenix.getSettings().getTeleportTime());
				return true;
			}else {
				sender.zui.sendMessage(StringFormatter.format("Not valid user.", MessageOccasion.ERROR, zenix));
				return false;
			}
		case 3:
			if (JavaUtil.canBeIntegers(args[0], args[1], args[2])) {
				
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				int z = Integer.parseInt(args[2]);
				
				if (y < 0 || y > 256) {
					sender.zui.sendMessage(StringFormatter.format("Y Axis too high!", MessageOccasion.ERROR, zenix));
					return false;
				}
				
				Location l = new Location(sender.zui.getWorld(), (double) x, (double) y, (double) z);
				
				if (sender.zui.getTeleport().teleportToLocation(sender.zui, l, zenix.getSettings().getTeleportTime() == 0 ? false : true, zenix.getSettings().canMoveBeforeTeleport(), zenix.getSettings().getTeleportTime())) {
					sender.zui.sendMessage(StringFormatter.format("You are being teleported to X: " + l.getX() + "; Y:" + l.getY() + "; Z:" + l.getZ() + ";", MessageOccasion.ESSENTIAL, zenix));
					return true;
				}
				return false;
			}
		default:
			sender.zui.sendMessage(StringFormatter.format("Too many arguments.", MessageOccasion.ERROR, zenix));
			return false;
		}
	}

}
