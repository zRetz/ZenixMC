package zenixmc.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.bending.BendingPlayerInterface;
import zenixmc.bending.ability.PresetInterface;

public class PresetSwitchEvent extends BendingPlayerEvent implements Cancellable {

	/**
	 * The preset to switch too.
	 */
	protected PresetInterface preset;

	/**
	 * Whether the event is cancelled.
	 */
	private boolean cancelled = false;

	/**
	 * The list of handlers.
	 */
	private static final HandlerList handlers = new HandlerList();

	/**
	 * Instantiate the event from a preset and a player.
	 *
	 * @param preset
	 *            The preset to switch to.
	 * @param player
	 *            The player that switches to this preset.
	 */
	public PresetSwitchEvent(PresetInterface preset, BendingPlayerInterface player) {
		super(player);
		this.preset = preset;
	}

	/**
	 * @return The preset to switch to.
	 */
	public PresetInterface getPreset() {
		return preset;
	}

	/**
	 * Changes the preset to switch to.
	 *
	 * @param preset
	 *            The preset to switch to.
	 */
	public void setPreset(PresetInterface preset) {
		this.preset = preset;
	}

	/**
	 * @return Whether the event is cancelled.
	 */
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Mark whether the event is cancelled.
	 *
	 * @param cancel
	 *            True to mark as cancelled.
	 */
	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

	/**
	 * @return The list of handlers.
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * @return The list of handlers.
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
