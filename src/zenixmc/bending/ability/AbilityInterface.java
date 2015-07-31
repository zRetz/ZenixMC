package zenixmc.bending.ability;

import java.io.Serializable;

import zenixmc.bending.BendingPlayerInterface;
import zenixmc.bending.Element;

/**
 * An ability is either a to a slot bindable action or a continiously active
 * passive ability.
 */

public interface AbilityInterface extends Serializable {

    /**
     * @return The <b>unique</b> name to identify this ability.
     */
    String getName();

    /**
     * @return The name of the ability for the players.
     */
    String getDisplayName();

    /**
     * @return The element players should be master of in order to use this
     *         ability, {@code Element.None} when no requirements.
     */
    Element getRequiredElement();

    /**
     * Called when a player uses the ability as bound to a slot for non passive
     * abilities. Is called when enabled for passive abilities.
     *
     * @param bendingPlayer
     *            The bendingPlayer that uses the ability
     * @param secondary
     *            Whether this is a secondary action
     */
    void activate(BendingPlayerInterface bendingPlayer, boolean secondary);

    /**
     * Called when a player disables a passive.
     *
     * @param bendingPlayer
     *            The bendingPlayer that disables the ability
     */
    void deactivate(BendingPlayerInterface bendingPlayer);

    /**
     * @return Whether the ability is a passive ability.
     */
    boolean isPassive();

}
