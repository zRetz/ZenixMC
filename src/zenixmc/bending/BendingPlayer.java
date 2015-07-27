package zenixmc.bending;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import zenixmc.bending.ability.AbilityInterface;
import zenixmc.bending.ability.PresetInterface;
import zenixmc.user.ZenixUserInterface;

/**
 * The default implementation of a bendingPlayer.
 */
public class BendingPlayer implements BendingPlayerInterface {
    /**
     * The user.
     */
    private transient ZenixUserInterface zui;

    /**
     * The native element of this player.
     */
    private Element nativeElement = Element.None;

    /**
     * The set containing the elements this player can bend.
     */
    private ElementSet elements = new ElementSet();

    /**
     * The presets this player has set.
     */
    private Map<String, PresetInterface> presets = new HashMap<String, PresetInterface>();

    /**
     * The ability data.
     */
    private Map<String, Object> abilityData = new HashMap<String, Object>();

    /**
     * The currently in use preset.
     */
    private PresetInterface currentPreset = null;

    /**
     * Instantiate.
     */
    public BendingPlayer() {
    }

    @Override
	public void setZenixUser(ZenixUserInterface zui) {
		this.zui = zui;
	}
    
    @Override
    public ZenixUserInterface getZenixUser() {
        return zui;
    }

    @Override
    public ElementSet getElements() {
        return elements;
    }

    @Override
    public void setElements(ElementSet elements) {
        this.elements = elements;
    }

    @Override
    public Element getNativeElement() {
        return nativeElement;
    }

    @Override
    public void setNativeElement(Element element) {
        this.nativeElement = element;
    }

    @Override
    public PresetInterface getCurrentPreset() {
        return currentPreset;
    }

    @Override
    public void setCurrentPreset(PresetInterface preset) {
        this.currentPreset = preset;
    }

    @Override
    public void setPreset(String name, PresetInterface preset) {
        if (name == null || name.isEmpty()) {
            name = "default";
        }
        presets.put(name, preset);
    }

    @Override
    public PresetInterface getPreset(String name) {
        if (name == null || name.isEmpty()) {
            name = "default";
        }
        return presets.get(name);
    }

    @Override
    public Set<String> getPresetNames() {
        return presets.keySet();
    }

    @Override
    public Object getAbilityData(AbilityInterface ability) {
        return abilityData.get(ability.getName());
    }

    @Override
    public void setAbilityData(AbilityInterface ability, Object data) {
        abilityData.put(ability.getName(), data);
    }

}
