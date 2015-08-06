package zenixmc.command.commands;

import java.util.ArrayList;
import java.util.List;

import zenixmc.ZenixMCInterface;
import zenixmc.command.MainCommandExecuter;
import zenixmc.command.ZenixCommandSender;
import zenixmc.user.ZenixUserManager;

/**
 * Base class for main commands.
 * @author james
 */
public abstract class AbstractMainCommand extends AbstractCommand implements CommandInterface {
	
	/**
	 * The command executer.
	 */
	protected final MainCommandExecuter executer;
	
	/**
	 * The subcommands of this command.
	 */
	protected List<CommandInterface> subCommands = new ArrayList<>();
	
	/**
	 * Instantiate.
	 * @param zenix
	 * 		The plugin.
	 */
	public AbstractMainCommand(ZenixMCInterface zenix, ZenixUserManager manager, MainCommandExecuter executer) {
		super(zenix, manager);
		this.executer = executer;
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
	 * Shows a subcommands help text.
	 * @param sender
	 * 		The executer of the command.
	 * @param subCommand
	 * 		The subCommand to get the help text for.
	 */
	protected void showSubHelp(ZenixCommandSender sender, CommandInterface subCommand) {
		sender.zui.sendMessage(zenix.getSettings().getMatchingNotificationColor() + "---- " + subCommand.getName() + " : Help -----");
		sender.zui.sendMessage(zenix.getSettings().getNotificationColor() + "-- !" + this.getName() + " " + subCommand.getName() + " "
                + subCommand.getFormat() + " -- "
                + subCommand.getDescription());
	}
	
	public List<CommandInterface> getSubCommands() {
		return subCommands;
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
	
	abstract public String getTitle();
}
