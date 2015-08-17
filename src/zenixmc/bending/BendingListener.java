package zenixmc.bending;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;

import zenixmc.ZenixListener;
import zenixmc.ZenixMCInterface;
import zenixmc.bending.ability.AbilityInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.user.ZenixUserManager;

public class BendingListener extends ZenixListener {

	public BendingListener(ZenixMCInterface zenix, ZenixUserManager manager, EventDispatcher eventDispatcher) {
		super(zenix, manager, eventDispatcher);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerAnimate(PlayerAnimationEvent ev) {
		final Player p = ev.getPlayer();

		final PlayerAnimationType action = ev.getAnimationType();
		
		final BendingPlayerInterface player = manager.getZenixUser(p).getBendingPlayer();
		final AbilityInterface ability = player.getCurrentPreset().getBinding(p.getInventory().getHeldItemSlot());
		System.out.println(ability);
		if (ability != null) {
			ability.activate(player, false);
			ev.setCancelled(true);
		}
	}
}
