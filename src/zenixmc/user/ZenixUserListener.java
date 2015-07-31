package zenixmc.user;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import zenixmc.PunishmentHandler;
import zenixmc.ZenixListener;
import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.ReachedMaxWarningEvent;
import zenixmc.event.ReachedZeroWarningEvent;
import zenixmc.user.objects.Warning;
import zenixmc.utils.DateUtil;

public class ZenixUserListener extends ZenixListener {

	/**
	 * The punishmentHandler to handle punishments.
	 */
	private final PunishmentHandler punishmentHandler;
	
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
}
