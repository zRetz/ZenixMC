package zenixmc.bending.ability;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import zenixmc.ZenixMC;
import zenixmc.bending.AbilityManager;
import zenixmc.bending.Slot;

/**
 * Default implementation of presets.
 */
public class Preset implements PresetInterface {
	
    /**
	 *  SerialVersionUID.
	 */
	private static final long serialVersionUID = -1964827587020057839L;

	/**
     * The name of the preset.
     */
    private String name;

    /**
     * The bindable slots.
     */
    private transient AbilityInterface[] slots = new AbilityInterface[Slot.SLOT_COUNT];

    /**
     * The list of enabled passive abilities.
     */
    private transient Set<AbilityInterface> enabledPassiveAbilities = new HashSet<>();

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
    
    /**
	 * Serialize this instance.
	 * 
	 * @param out
	 *            Target to which this instance is written.
	 * @throws IOException
	 *             Thrown if exception occurs during serialization.
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.writeUTF(name);
		ArrayList<String> tbdata = new ArrayList<>();
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				AbilityInterface a = slots[i];
				tbdata.add(a.getName());
			}
		}
		out.writeObject(tbdata);
	}

	/**
	 * Deserialize this instance from input stream.
	 * 
	 * @param in
	 *            Input Stream from which this instance is to be deserialized.
	 * @throws IOException
	 *             Thrown if error occurs in deserialization.
	 * @throws ClassNotFoundException
	 *             Thrown if expected class is not found.
	 */
	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
		name = in.readUTF();
		try {
			Field f = ZenixMC.instance.getClass().getDeclaredField("abilityManager");
			f.setAccessible(true);
			AbilityManager am = (AbilityManager) f.get(ZenixMC.instance);
			ArrayList<String> tbdata = (ArrayList<String>) in.readObject();
			AbilityInterface[] tbslots = new AbilityInterface[Slot.SLOT_COUNT];
			for (int i = 0; i < tbdata.size(); i++) {
				if (tbdata.get(i) != null) {
					tbslots[i] = am.findNamedAbility(tbdata.get(i));
				}
			}
			slots = tbslots;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		enabledPassiveAbilities = new HashSet<>();
	}

}