/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.event;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Small wrapper around the plugins event dispatching api.
 * @author james
 */
public class EventDispatcher {
    /**
     * The pluginmanager to register the listners to.
     */
    private final PluginManager pluginManager;

    /**
     * The plugin to register the events as.
     */
    private JavaPlugin parent;

    /**
     * Create an event dispatcher from an plugin manager and a plugin instance.
     *
     * @param pluginManager
     *            The plugin manager to register listeners to.
     * @param parent
     *            The plugin to register listeners as.
     */
    public EventDispatcher(PluginManager pluginManager, JavaPlugin parent) {
        this.pluginManager = pluginManager;
        this.parent = parent;
    }

    /**
     * Create the eventdispatcher from just a plugin instance, obtains the
     * pluginmanager from the instance.
     *
     * @param parent
     *            The plugin to register listeners as.
     */
    public EventDispatcher(JavaPlugin parent) {
        this(parent.getServer().getPluginManager(), parent);
    }

    /**
     * @return Returns the plugin manager.
     */
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    /**
     * Sets the plugin to register events as.
     *
     * @param parent
     *            The plugin.
     */
    public void setParent(JavaPlugin parent) {
        this.parent = parent;
    }

    /**
     * @return The plugin that is used to register events as.
     */
    public JavaPlugin getParent() {
        return parent;
    }

    /**
     * Register an event listener.
     *
     * @param listener
     *            The listener to regiser.
     */
    public void registerEventListener(Listener listener) {
        pluginManager.registerEvents(listener, parent);
    }

    /**
     * Fires an event.
     *
     * @param event
     *            The event to fire.
     */
    public void callEvent(Event event) {
        pluginManager.callEvent(event);
    }
}