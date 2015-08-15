package zenixmc.bending;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import zenixmc.bending.ability.AbilityInterface;
import zenixmc.user.ZenixUserManager;

public class BendingListener implements Listener {

	protected ZenixUserManager manager;

    public BendingListener(ZenixUserManager manager) {
        this.manager = manager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent ev) {
        final Player p = ev.getPlayer();
        
        if (!p.isSneaking()) {
            return;
        }

        final Action action = ev.getAction();

        boolean secondary = true;

        switch (action) {
        case LEFT_CLICK_AIR:
        case LEFT_CLICK_BLOCK:
            secondary = false;
        case RIGHT_CLICK_AIR:
        case RIGHT_CLICK_BLOCK:
        	final BendingPlayerInterface player = manager.getZenixUser(p).getBendingPlayer();
            final AbilityInterface ability = player.getCurrentPreset()
                    .getBinding(p.getInventory().getHeldItemSlot());
            if (ability != null) {
                ability.activate(player, secondary);
                ev.setCancelled(true);
            }
            break;
        }
    }
}
