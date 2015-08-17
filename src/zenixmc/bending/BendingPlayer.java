package zenixmc.bending;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import zenixmc.bending.ability.AbilityInterface;
import zenixmc.bending.ability.PresetInterface;
import zenixmc.io.SerializableLocation;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Warning;

/**
 * The default implementation of a bendingPlayer.
 */
public class BendingPlayer implements BendingPlayerInterface {
	
    /**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 5489947461704879269L;

	/**
     * The user.
     */
    private transient ZenixUserInterface zui;
    
    /**
     * The ability data.
     */
    private transient Map<String, Object> abilityData = new HashMap<>();

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
    private List<PresetInterface> presets = new ArrayList<>();

    /**
     * The currently in use preset.
     */
    private PresetInterface currentPreset = null;

    /**
     * Instantiate.
     */
    public BendingPlayer(ZenixUserInterface zui) {
    	this.zui = zui;
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
    public void setPreset(PresetInterface preset) {
    	if (preset == null) {
    		return;
    	}
        presets.add(preset);
    }

    @Override
    public PresetInterface getPreset(String name) {
        for (PresetInterface p : presets) {
        	if (p.getName().equals(name)) {
        		return p;
        	}
        }
        return null;
    }

    @Override
    public Set<String> getPresetNames() {
    	Set<String> result = new HashSet<>();
    	
    	for (PresetInterface p : presets) {
    		result.add(p.getName());
    	}
    	
        return result;
    }

    @Override
    public Object getAbilityData(AbilityInterface ability) {
        return abilityData.get(ability.getName());
    }

    @Override
    public void setAbilityData(AbilityInterface ability, Object data) {
        abilityData.put(ability.getName(), data);
    }

    @Override
    public String toString() {
    	return this.getZenixUser().getName();
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
		abilityData.clear();
		out.writeUTF(nativeElement.toString());
		out.writeObject(elements);
		out.writeObject(presets);
		out.writeObject(currentPreset);
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
		abilityData = new HashMap<>();
		nativeElement = Element.getType(in.readUTF());
		elements = (ElementSet) in.readObject();
		presets = (List<PresetInterface>) in.readObject();
		currentPreset = (PresetInterface) in.readObject();
	}
}
