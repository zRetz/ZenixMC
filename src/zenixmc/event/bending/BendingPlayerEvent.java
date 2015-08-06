package zenixmc.event.bending;

import zenixmc.bending.BendingPlayerInterface;
import zenixmc.event.ZenixEvent;

/**
 * An event that is related to a player.
 */
public abstract class BendingPlayerEvent extends ZenixEvent {
	
    /**
     * The player in question.
     */
    private BendingPlayerInterface player = null;

    /**
     * Creates a new event for the specified player.
     *
     * @param player
     *            The player.
     */
    public BendingPlayerEvent(BendingPlayerInterface player) {
        this.player = player;
    }

    /**
     * @return The player that is relevant for this event.
     */
    public BendingPlayerInterface getPlayer() {
        return player;
    }

    /**
     * Changes the player this event is about.
     *
     * @param player
     *            The player.
     */
    public void setPlayer(BendingPlayerInterface player) {
        this.player = player;
    }
}
