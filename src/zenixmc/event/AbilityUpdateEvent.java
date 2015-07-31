package zenixmc.event;

import org.bukkit.event.HandlerList;

import zenixmc.bending.BendingPlayerInterface;

public class AbilityUpdateEvent extends BendingPlayerEvent {
    /**
     * The slot that was updated.
     */
    protected final int slot;

    /**
     * List of event handlers.
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Create ability update event.
     *
     * @param slot
     *            The slot that was updated.
     * @param player
     *            The player that was updated.
     */
    public AbilityUpdateEvent(int slot, BendingPlayerInterface player) {
        super(player);
        this.slot = slot;
    }

    /**
     * @return The slot that was updated
     */
    public int getSlot() {
        return slot;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * @return A list of handlers
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
