package zenixmc.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import zenixmc.bending.BendingPlayerInterface;
import zenixmc.bending.ability.AbilityInterface;

public class BindEvent extends BendingPlayerEvent implements Cancellable {
    /**
     * The handlers.
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * The slot to bind to.
     */
    protected int slot;

    /**
     * The ability to bind.
     */
    protected AbilityInterface ability;

    /**
     * Whether the event is cancelled.
     */
    protected boolean cancelled = false;

    /**
     * The message to send to the player.
     */
    protected String message = null;

    /**
     * Create a bind event.
     *
     * @param slot
     *            The slot to bind to.
     * @param ability
     *            The ability to bind.
     * @param player
     *            The player to bind to.
     * @param message
     *            The message to send to the player.
     */
    public BindEvent(int slot, AbilityInterface ability,
            BendingPlayerInterface player, String message) {
        super(player);
        this.slot = slot;
        this.ability = ability;
        this.message = message;
    }

    /**
     * @return The slot to bind to.
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Changes the slot to bind to.
     *
     * @param slot
     *            The slot to bind to.
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * @return The ability to bind.
     */
    public AbilityInterface getAbility() {
        return ability;
    }

    /**
     * Changes the ability to bind.
     *
     * @param ability
     *            The ability to bind.
     */
    public void setAbility(AbilityInterface ability) {
        this.ability = ability;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * @return The message to send to the player.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Changes the message to send to the player.
     *
     * @param message
     *            The message to send to the player.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * @return The list of handlers
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
