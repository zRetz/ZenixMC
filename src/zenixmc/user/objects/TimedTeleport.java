package zenixmc.user.objects;

import org.bukkit.Location;

import zenixmc.ZenixMCInterface;
import zenixmc.user.ZenixUserInterface;
import zenixmc.utils.MinecraftUtils;

/**
 * Class for Timed Teleporting.
 * @author james
 * 
 * <b>NOTE</b> Inspiration from Essentials' TimedTeleport! Not copied.
 */
public class TimedTeleport implements Runnable {

	/**
	 * The plugin.
	 */
	private final ZenixMCInterface zenix;
	
	/**
	 * The user requesting for teleport.
	 */
	private final ZenixUserInterface teleportOwner;
	
	/**
	 * User to teleport.
	 */
	private final ZenixUserInterface teleportee;
	
	/**
	 * X of location teleportee is supposed to stay at.
	 */
	private final int stayX;
	
	/**
	 * Y of location teleportee is supposed to stay at.
	 */
	private final int stayY;
	
	/**
	 * Z of location teleportee is supposed to stay at.
	 */
	private final int stayZ;
	
	/**
	 * Whether the teleportee needs to stay still.
	 */
	private final boolean stay;
	
	/**
	 * Time to countdown.
	 */
	private long time;
	
	/**
	 * Target location.
	 */
	private Location target;
	
	/**
	 * Tasks identifier.
	 */
	private int taskId = -1;
	
	/**
	 * Instantiate.
	 * @param zenix
	 * 		The plugin.
	 * @param teleportOwner
	 * 		The user requesting the teleport.
	 * @param teleportee
	 * 		The user being teleported.
	 * @param target
	 * 		The target location for teleportation.
	 * @param time
	 * 		The time to countdown before teleportation.
	 * @param stay
	 * 		Whether the teleportee is allowed to move before teleportation.
	 */
	public TimedTeleport(ZenixMCInterface zenix, ZenixUserInterface teleportOwner, ZenixUserInterface teleportee, Location target, long time, boolean stay) {
		
		this.zenix = zenix;
		this.teleportOwner = teleportOwner;
		this.teleportee = teleportee;
		this.stayX = this.teleportee.getLocation().getBlockX();
		this.stayY = this.teleportee.getLocation().getBlockY();
		this.stayZ = this.teleportee.getLocation().getBlockZ();
		this.stay = stay;
		this.target = target;
		
		if ((!(time > 1000)) || (!(time % 1000 == 0))) {
			this.time = zenix.getSettings().getTeleportTime();
		}else {
			this.time = time;
		}
		
		this.taskId = zenix.runTaskTimerAsynchronously(this, 0L, 20L).getTaskId();
	}

	@Override
	public void run() {
		
		if (teleportOwner == null ||!(teleportOwner.isOnline())) {
			cancel(true, "User teleporting request is no longer online.");
			return;
		}
		
		if (teleportee == null || !(teleportee.isOnline())) {
			cancel(true, "User to be teleported is no longer online.");
			return;
		}
		
		if (!(MinecraftUtils.isSafeLocation(target))) {
			target = MinecraftUtils.getSafeLocation(target);
		}
		
		Location curLoc = teleportee.getLocation();
		
		if ((!(stayX == curLoc.getBlockX())) || (!(stayY == curLoc.getBlockY())) || (!(stayZ == curLoc.getBlockZ())) && stay) {
			cancel(true, "User to be teleported moved.");
			return;
		}
		
		time -= 1000;
		
		if (time <= 0) {
			teleportee.getTeleport().teleportToLocation(teleportee, target, false, false, 0);
			cancel(true, "Teleporting.");
		}
	}
	
	/**
	 * Cancels timed teleport.
	 * @param notifyUser
	 * 		Whether it should notify users.
	 * @param reason
	 * 		The reason for cancelling.
	 */
	public void cancel(boolean notifyUser, String reason) {
		
		if (taskId == -1) {
			return;
		}
		
		zenix.getScheduler().cancelTask(taskId);
		taskId = -1;
		
		if (notifyUser) {
			if (teleportOwner != null) {
				if (teleportOwner.isOnline()) {
					
					if (reason.equals("Teleporting.")) {
						teleportOwner.sendMessage(zenix.getSettings().getNotificationColor() + reason);
						return;
					}
					
					teleportOwner.sendMessage(zenix.getSettings().getErrorColor() + "Teleportation cancelled.");
					teleportOwner.sendMessage(zenix.getSettings().getErrorColor() + reason);
				}
			}
			if (teleportee != null) {
				if (teleportee.isOnline()) {
					
					if (reason.equals("Teleporting.")) {
						teleportOwner.sendMessage(zenix.getSettings().getNotificationColor() + reason);
						return;
					}
					
					teleportee.sendMessage(zenix.getSettings().getErrorColor() + "Teleportation cancelled.");
					teleportee.sendMessage(zenix.getSettings().getErrorColor() + reason);
				}
			}
		}
	}
	
}
