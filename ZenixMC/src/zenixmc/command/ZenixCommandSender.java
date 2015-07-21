/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.command;

import org.bukkit.command.CommandSender;
import zenixmc.user.ZenixUserInterface;

/**
 * CommandSender with additional user information.
 * @author james
 */
public class ZenixCommandSender {
    
    /**
     * The underlying {@link CommandSender};
     */
    public final CommandSender commandSender;
    
    /**
     * The user;
     */
    public final ZenixUserInterface zui;
    
    /**
     * Create a {@link CommandSender} without user data.
     *
     * @param commandSender
     *            The command sender.
     */
    public ZenixCommandSender(CommandSender commandSender) {
        this(commandSender, null);
    }

    /**
     * Create a {@link CommandSender} with user data.
     *
     * @param commandSender
     *            The command sender.
     * @param zui
     *            The user information.
     */
    public ZenixCommandSender(CommandSender commandSender,
            ZenixUserInterface zui) {
        this.commandSender = commandSender;
        this.zui = zui;
    }
    
}
