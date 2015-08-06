package zenixmc.event.organization;

import zenixmc.organization.OrganizationPlayerInterface;

public abstract class OrganizationPlayerEvent extends OrganizationEvent {

	/**
     * The player in question.
     */
    private OrganizationPlayerInterface player = null;

    /**
     * Creates a new event for the specified player.
     *
     * @param player
     *            The player.
     */
    public OrganizationPlayerEvent(OrganizationPlayerInterface player) {
        this.player = player;
    }

    /**
     * @return The player that is relevant for this event.
     */
    public OrganizationPlayerInterface getPlayer() {
        return player;
    }

    /**
     * Changes the player this event is about.
     *
     * @param player
     *            The player.
     */
    public void setPlayer(OrganizationPlayerInterface player) {
        this.player = player;
    }
	
}
