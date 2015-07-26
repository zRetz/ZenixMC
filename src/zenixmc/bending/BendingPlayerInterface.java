/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.bending;

import java.util.Set;

import zenixmc.bending.ability.AbilityInterface;
import zenixmc.bending.ability.PresetInterface;
import zenixmc.user.ZenixUserInterface;

/**
 * A bendingPlayer internally used by this plugin.
 * @author james
 */
public interface BendingPlayerInterface {
    
	/**
	 * Sets the user of the bendingPlayer.
	 * @param zui
	 * 		The user.
	 */
	void setZenixUser(ZenixUserInterface zui);
	
    /**
     * @return The user of the bendingPlayer.
     */
    ZenixUserInterface getZenixUser();
    
    /**
     * @return The set of elements a bendingPlayer is able to bend.
     */
    ElementSet getElements();

    /**
     * Sets the elements the bendingPlayer is able to bend.
     *
     * @param elements
     *            The new elements the bendingPlayer can bend
     */
    void setElements(ElementSet elements);

    /**
     * @return The element the user is native in
     */
    Element getNativeElement();

    /**
     * Sets the element the bendingPlayer is native in.
     *
     * <b>Note:</b> The caller is responsible for disabling abilities that are
     * not applicable for the new element.
     *
     * @see AbilityManager
     * @param element
     *            The element to set the bendingPlayer native to
     */
    void setNativeElement(Element element);

    /**
     * Adds/modifies a preset of the bendingPlayer.
     *
     * <b>Note:</b> This should not be used directly, as it doesn't fire the
     * proper events, please use abilityManager instead.
     *
     * @see AbilityManager
     * @param name
     *            The name of the preset
     * @param preset
     *            The preset to set
     */
    void setPreset(String name, PresetInterface preset);

    /**
     * @return The preset currently in use
     */
    PresetInterface getCurrentPreset();

    /**
     * Updates the preset currently in use.
     *
     * <b>Note:</b> This should not be used directly, as it doesn't fire the
     * proper events, please use abilityManager instead.
     *
     * @see AbilityManager
     * @param preset
     *            The preset to make current
     */
    void setCurrentPreset(PresetInterface preset);

    /**
     * Obtain a certain preset an bendingPlayer has by name.
     *
     * @param name
     *            The name of the preset
     * @return The preset or null when not available
     */
    PresetInterface getPreset(String name);

    /**
     * @return A set containing all the preset's names
     */
    Set<String> getPresetNames();

    /**
     * Return ability specific data. Is used to store the state of an ability
     * while it's being used per player.
     *
     * @param ability
     *            The ability to obtain data for
     * @return The data this ability has set or null when nothing is found
     */
    Object getAbilityData(AbilityInterface ability);

    /**
     * Sets ability specific data.
     *
     * @see #getAbilityData
     * @param ability
     *            The ability to set data for
     * @param data
     *            The data to set, null to clear
     */
    void setAbilityData(AbilityInterface ability, Object data);
    
}