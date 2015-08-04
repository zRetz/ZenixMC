package zenixmc.command.commands;

import java.util.ArrayList;
import java.util.List;

import zenixmc.ZenixMCInterface;

/**
 * Base class for main commands.
 * @author james
 */
public abstract class AbstractMainCommand extends AbstractCommand implements CommandInterface {
	
	/**
	 * The subcommands of this command.
	 */
	protected List<CommandInterface> subCommands = new ArrayList<>();
	
	/**
	 * Instantiate.
	 * @param zenix
	 * 		The plugin.
	 */
	public AbstractMainCommand(ZenixMCInterface zenix) {
		super(zenix);
	}
	
	@Override
	public String[] getAliases() {
		return null;
	}
	
	/**
	 * Adds a subcommand.
	 * @param command
	 * 		The command to add.
	 */
	protected void addSubCommand(CommandInterface command) {
		if (!(subCommands.contains(command))) {
			subCommands.add(command);
		}
	}
	
	/**
	 * Removes a subcommand.
	 * @param command
	 * 		The command to remove.
	 */
	protected void removeSubCommand(CommandInterface command) {
		if (subCommands.contains(command)) {
			subCommands.remove(command);
		}
	}
	
	/**
     * Finds a subcommand by name or alias.
     *
     * @param alias
     *     	The alias to use.
     * @return The found subcommand or null.
     */
	public CommandInterface findSubCommandByAlias(String alias) {
        for (final CommandInterface command : subCommands) {
            if (alias.equalsIgnoreCase(command.getName())) {
                return command;
            }

            final String[] commandAliases = command.getAliases();

            if (commandAliases != null) {
                for (final String commandAlias : commandAliases) {
                    if (alias.equalsIgnoreCase(commandAlias)) {
                        return command;
                    }
                }
            }
        }

        return null;
    }
	
	/**
	 * @return <code>true</code> If the command has subcommands.
	 */
	public boolean hasSubCommands() {
		return subCommands.size() > 0;
	}
}
