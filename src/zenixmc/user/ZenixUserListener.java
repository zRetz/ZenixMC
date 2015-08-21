package zenixmc.user;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

import zenixmc.PunishmentHandler;
import zenixmc.ZenixListener;
import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.essential.ReachedMaxWarningEvent;
import zenixmc.event.essential.ReachedZeroWarningEvent;
import zenixmc.event.zenix.DoubleTapShiftEvent;
import zenixmc.user.objects.Warning;
import zenixmc.utils.DateUtil;

public class ZenixUserListener extends ZenixListener {

	/**
	 * The punishmentHandler to handle punishments.
	 */
	private final PunishmentHandler punishmentHandler;
	
	private final Map<ZenixUser, Long> doubleTap = new HashMap<>();
	
	public ZenixUserListener(PunishmentHandler punishmentHandler, ZenixMCInterface zenix, ZenixUserManager manager, EventDispatcher eventDispatcher) {
		super(zenix, manager, eventDispatcher);
		this.punishmentHandler = punishmentHandler;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onReachMaxWarnings(ReachedMaxWarningEvent e) {
		
		ZenixUserInterface zui = e.getUser();
		
		//check if bypass
		
		Warning w = zui.getWarning();
		
		StringBuilder reasons = new StringBuilder();
		reasons.append("\n");
		reasons.append("REASON 1: " + w.getReasonOne());
		reasons.append("\n");
		reasons.append("REASON 2: " + w.getReasonTwo());
		reasons.append("\n");
		reasons.append("REASON 3: " + w.getReasonThree());
		
		zui.setWarning(new Warning(zui, eventDispatcher));
		punishmentHandler.tempBan(zenix.getConsole(), zui, "You have reached maximum warnings." + reasons.toString(), w.getSentence());
	}
			
	@EventHandler(priority = EventPriority.LOW)
	public void onReachZeroWarning(ReachedZeroWarningEvent e) {
		
		e.getUser().sendMessage(zenix.getSettings().getNotificationColor() + "Congratulations! You've reached 0 warnings!");
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onMove(PlayerMoveEvent e) {
		
		ZenixUser zui = manager.getZenixUser(e.getPlayer());
		
		if (zui.isFrozen()) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onShift(PlayerToggleSneakEvent e) {
		
		if (!(e.isSneaking())) {
			return;
		}
		
		ZenixUser zui = manager.getZenixUser(e.getPlayer());
		System.out.println("Pressed Shift");
		if(!(doubleTap.containsKey(zui))) {
			doubleTap.put(zui, System.currentTimeMillis());
		}else {
			long ftime = doubleTap.get(zui);
			long timeb = (System.currentTimeMillis() - ftime);
			if (timeb <= ((DateUtil.second/2) - 300)) {
				eventDispatcher.callEvent(new DoubleTapShiftEvent(zui, timeb));
			}
			doubleTap.remove(zui);
		}
	}
}
