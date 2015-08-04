/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.command.commands;

import java.util.List;
import zenixmc.command.ZenixCommandSender;

/**
 *
 * @author james
 */
public interface CommandInterface {
    
    /**
     * @return The full name of the command.
     */
    String getName();

    /**
     * @return The array of aliases or null.
     */
    String[] getAliases();

    /**
     * @return The array of helptext lines or null.
     */
    String[] getHelp();

    /**
     * @return The usage format {@code "[ ]"} for arguments and {@code "[< >]"}
     *         for optional arguments.
     */
    String getFormat();

    /**
     * @return The description of this subcommand or null.
     */
    String getDescription();

    /**
     * Exectues the subcommand.
     *
     * @param sender
     *            The executor of the command.
     * @param label
     *            The label of the subcommand used.
     * @param args
     *            The arguments passed to the subcommand, note that the 0th
     *            argument is the subcommand name.
     * @return When returned false, the format should be shown to the user.
     */
    boolean onCommand(ZenixCommandSender sender, String label, String[] args);

}
