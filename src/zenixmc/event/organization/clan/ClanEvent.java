package zenixmc.event.organization.clan;

import zenixmc.event.organization.OrganizationEvent;
import zenixmc.organization.clans.Clan;

public abstract class ClanEvent extends OrganizationEvent {

	/**
     * The clan in question.
     */
    private Clan clan = null;

    /**
     * Creates a new event for the specified clan.
     *
     * @param clan
     *    	The clan.
     * @param player
     * 		The organization player.
     */
    public ClanEvent(Clan clan) {
        this.clan = clan;
    }

    /**
     * @return The clan that is relevant for this event.
     */
    public Clan getClan() {
        return clan;
    }

    /**
     * Changes the clan this event is about.
     *
     * @param clan
     *            The clan.
     */
    public void setClan(Clan clan) {
        this.clan = clan;
    }
}
