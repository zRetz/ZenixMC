/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.bending;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import zenixmc.bending.ability.AbilityInterface;
import zenixmc.bending.ability.Preset;
import zenixmc.bending.ability.PresetInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.bending.AbilityUpdateEvent;
import zenixmc.event.bending.BindEvent;
import zenixmc.event.bending.PresetSwitchEvent;
import zenixmc.utils.StringFormatter;

/**
 * Manages the usage of abilities, this includes binding, using and presets.
 * 
 * @author james
 */
public class AbilityManager {
	
    /**
     * All registered abilities.
     */
    protected Set<AbilityInterface> abilities = new HashSet<AbilityInterface>();

    /**
     * The event dispatcher that is used.
     */
    protected EventDispatcher eventDispatcher;

    /**
     * Instantiate the abilitymanager.
     *
     * @param eventDispatcher
     *            The event dispatcher to use.
     */
    public AbilityManager(final EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    /**
     * @return The set of available abilities
     */
    public Set<AbilityInterface> getAvailableAbilities() {
        return Collections.unmodifiableSet(abilities);
    }

    /**
     * Registers an ability. Duplicate registrations will simply be ignored.
     *
     * @param ability
     *            The ability to register.
     */
    public void registerAbility(final AbilityInterface ability) {
        abilities.add(ability);
        eventDispatcher.registerEventListener(ability);
    }

    /**
     * Returns the abilities that are available to the player.
     *
     * @param player
     *            The player
     * @return The list of abilities usable by this player.
     */
    public Set<AbilityInterface> getAbilitiesOf(
            final BendingPlayerInterface player) {
        final Set<AbilityInterface> abilities = new HashSet<AbilityInterface>(
                getAvailableAbilities());

        final Iterator<AbilityInterface> it = abilities.iterator();
        while (it.hasNext()) {
            final AbilityInterface ability = it.next();

            if (!isUsableBy(ability, player)) {
                it.remove();
            }
        }

        return abilities;
    }

    /**
     * Checks if a specific ability is usable by the specified player.
     *
     * @param ability
     *            The ability to check
     * @param player
     *            The player to check for
     * @return Whether this ability is usable by the player
     */
    public boolean isUsableBy(final AbilityInterface ability,
            final BendingPlayerInterface player) {
        final Element requiredElement = ability.getRequiredElement();

        // TODO: add permission check

        return requiredElement == Element.None
                || player.getElements().contains(requiredElement);
    }

    /**
     * Find an ability by name.
     *
     * @param name
     *            The name of the ability
     * @param abilities
     *            The set of abilities to look through
     * @return The named ability or null
     */
    public AbilityInterface findNamedAbility(String name,
            final Set<AbilityInterface> abilities) {
        name = name.toLowerCase();

        for (final AbilityInterface ability : abilities) {
            if (ability.getName().toLowerCase().equals(name)) {
                return ability;
            }
        }

        return null;
    }

    /**
     * Find an ability by name.
     *
     * @param name
     *            The name of the ability
     * @return The named ability or null
     */
    public AbilityInterface findNamedAbility(final String name) {
        return findNamedAbility(name, abilities);
    }

    /**
     * Create a default preset for a player.
     *
     * @param player
     *            The player to create the preset for
     * @return The newly created default preset.
     */
    public PresetInterface createDefaultPreset(
            final BendingPlayerInterface player) {
        final PresetInterface preset = new Preset("default");

        for (final AbilityInterface ability : abilities) {
            if (ability.isPassive() && isUsableBy(ability, player)) {
                preset.enablePassiveAbility(ability);
            }
        }

        return preset;
    }

    /**
     * Disable all abilities in the preset of the specified player.
     *
     * @param player
     *            The player to disable the abilities for.
     * @param preset
     *            The preset to disable the abilities of.
     */
    public void disableAllAbilities(final BendingPlayerInterface player,
            final PresetInterface preset) {
        for (final AbilityInterface ability : preset
                .getEnabledPassiveAbilities()) {
            ability.deactivate(player);
        }

        for (int i = 0; i < Slot.SLOT_COUNT; i++) {
            final AbilityInterface ability = preset.getBinding(i);
            if (ability != null) {
                ability.deactivate(player);
            }
        }
    }

    /**
     * Activate all passive abilities in the preset of the specified player.
     *
     * @param player
     *            The player to enable the passives for
     * @param preset
     *            The preset to disable the abilities of
     */
    public void activatePassives(final BendingPlayerInterface player,
            final PresetInterface preset) {
        for (final AbilityInterface ability : preset
                .getEnabledPassiveAbilities()) {
            ability.activate(player, false);
        }
    }

    /**
     * Binds an ability to the specified slot. May be cancelled by event
     * listeners.
     *
     * <b>Fires:</b> {@link BindEvent} and {@link AbilityUpdateEvent}
     *
     * @param player
     *            The player to bind the slot for
     * @param slot
     *            The slot to bind on
     * @param ability
     *            The ability to bind on the slot
     * @return Whether the bind has taken place, false when the
     *         {@link BindEvent} was cancelled.
     */
    public boolean bindAbility(final BendingPlayerInterface player,
            final int slot, final AbilityInterface ability) {
        // TODO: move message to event listener
        final BindEvent event = new BindEvent(slot, ability, player,
                "Bound slot %%{slot}s to: %%{ability.displayName}s");

        eventDispatcher.callEvent(event);

        if (!event.isCancelled()) {
            final String messageFormat = event.getMessage();
            if (messageFormat != null) {
                final Map<String, Object> format = StringFormatter
                        .createFormat();

                final AbilityInterface newAbility = event.getAbility();

                format.put("rawslot", event.getSlot());
                format.put("slot", event.getSlot() + 1);
                format.put("ability.displayName", newAbility == null ? "None"
                        : newAbility.getDisplayName());
                format.put("ability.name", newAbility == null ? "none"
                        : newAbility.getName());

                event.getPlayer().getZenixUser().sendMessage(
                                StringFormatter.format(messageFormat, format));
            }
            
            final AbilityInterface oldAbility = player.getCurrentPreset()
                    .getBinding(event.getSlot());

            if (oldAbility != null) {
                oldAbility.deactivate(player);
            }

            player.getCurrentPreset().setBinding(event.getSlot(),
                    event.getAbility());

            eventDispatcher.callEvent(new AbilityUpdateEvent(event.getSlot(),
                    event.getPlayer()));

            return true;
        }

        return false;
    }

    /**
     * Switch between presets for a player.
     *
     * @param player
     *            The player to switch presets for
     * @param preset
     *            The preset to switch to, when this is null, a default preset
     *            is generated.
     * @return Whether the change has taken place, false when the
     *         {@link PresetSwitchEvent} has taken place.
     */
    public boolean changePreset(final BendingPlayerInterface player,
            PresetInterface preset) {

        if (preset == null) {
            preset = createDefaultPreset(player);
            player.setPreset(preset);
        }

        final PresetSwitchEvent event = new PresetSwitchEvent(preset, player);

        if (!event.isCancelled()) {

            final PresetInterface currentPreset = player.getCurrentPreset();

            if (currentPreset != null) {
                disableAllAbilities(player, currentPreset);
            }

            player.setCurrentPreset(event.getPreset());

            activatePassives(player, player.getCurrentPreset());

            return true;
        }

        return false;
    }
}