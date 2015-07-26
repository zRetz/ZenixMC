package zenixmc.bending.ability;

import java.util.Set;

/**
 * A preset is the named set of bindings and enabled passive abilities.
 *
 */
public interface PresetInterface {
    /**
     * @return The name of this preset, {@code "default"} if it is the default
     *         preset.
     */
    String getName();

    /**
     * @param slot
     *            The slot to return the ability of
     * @return The ability bound to the specified slot
     */
    AbilityInterface getBinding(int slot);

    /**
     * Sets the ability for a specific slot.
     *
     * @param slot
     *            The slot to set the ability for
     * @param binding
     *            The ability to set it to
     */
    void setBinding(int slot, AbilityInterface binding);

    /**
     * @return The enabled passive abilities
     */
    Set<AbilityInterface> getEnabledPassiveAbilities();

    /**
     * Enables a passive ability.
     *
     * @param ability
     *            The ability to enable
     */
    void enablePassiveAbility(AbilityInterface ability);

    /**
     * Disables a passive ability.
     *
     * @param ability
     *            The ability to disable
     */
    void disablePassiveAbility(AbilityInterface ability);
}
