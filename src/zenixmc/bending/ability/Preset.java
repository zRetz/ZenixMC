package zenixmc.bending.ability;

import java.util.HashSet;
import java.util.Set;

import zenixmc.bending.Slot;

/**
 * Default implementation of presets.
 */
public class Preset implements PresetInterface {
    /**
     * The name of the preset.
     */
    private final String name;

    /**
     * The bindable slots.
     */
    private AbilityInterface[] slots = new AbilityInterface[Slot.SLOT_COUNT];

    /**
     * The list of enabled passive abilities.
     */
    private Set<AbilityInterface> enabledPassiveAbilities = new HashSet<AbilityInterface>();

    /**
     * Create a preset by name.
     *
     * @param name
     *            The name of the preset.
     */
    public Preset(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbilityInterface getBinding(int slot) {
        return slots[slot];
    }

    @Override
    public void setBinding(int slot, AbilityInterface binding) {
        slots[slot] = binding;
    }

    @Override
    public Set<AbilityInterface> getEnabledPassiveAbilities() {
        return enabledPassiveAbilities;
    }

    @Override
    public void enablePassiveAbility(AbilityInterface ability) {
        enabledPassiveAbilities.add(ability);
    }

    @Override
    public void disablePassiveAbility(AbilityInterface ability) {
        enabledPassiveAbilities.remove(ability);
    }

    @Override
    public String toString() {
        return "Preset(" + name + ",slots=" + slots + ",passives="
                + enabledPassiveAbilities + ")";
    }

}