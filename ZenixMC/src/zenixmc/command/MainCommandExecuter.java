/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import zenixmc.command.commands.CommandInterface;
import zenixmc.persistance.ZenixUserRepositoryInterface;

/**
 * The main command executer.
 * @author james
 */
public class MainCommandExecuter implements CommandExecutor, TabCompleter {

    /**
     * The amount of commands to list on one page in the help text.
     */
    protected int commandsPerPage = 10;
    
    /**
     * The repository to use to fetch user information from.
     */
    protected ZenixUserRepositoryInterface repository;

    /**
     * The registered sub commands.
     */
    protected List<CommandInterface> commands = new ArrayList<>();

    /**
     * Creates the main command executor.
     *
     * @param repository
     *            The repository to use.
     */
    public MainCommandExecuter(ZenixUserRepositoryInterface repository) {
        this.repository = repository;
    }

    /**
     * Registers a command.
     *
     * @param command
     *            The command to register.
     */
    public void addSubCommand(CommandInterface command) {
        commands.add(command);
    }

    /**
     * Shows help text.
     *
     * @param sender
     *            The executor of the command.
     * @param page
     *            The page of the help text.
     * @param command
     *            The command to get the help text for, null when main text.
     */
    void showHelp(CommandSender sender, int page, CommandInterface command) {
        final String[] help = command == null ? null : command.getHelp();

        sender.sendMessage("---- "
                + (command == null ? "Zenix" : command.getName())
                + ": "
                + (command == null ? "Index" : "Help")
                + " ("
                + page
                + "/"
                + ((command == null ? commands.size() : (help == null ? 0
                        : help.length)) / commandsPerPage) + ") ------");

        final int offset = page * commandsPerPage;

        for (int i = offset; i < offset + commandsPerPage; i++) {
            if (command != null && help != null) {
                if (i >= 0 && i < help.length) {
                    sender.sendMessage(help[i]);
                }
            } else if (i >= 0 && i < (command == null ? commands.size() : 1)) {
                final CommandInterface subCommand = command == null ? commands
                        .get(i) : command;
                sender.sendMessage("-- /z " + subCommand.getName() + " "
                        + subCommand.getFormat() + " -- "
                        + subCommand.getDescription());
            }
        }
    }

    /**
     * Finds a subcommand by name or alias.
     *
     * @param alias
     *            The alias to use.
     * @return The found subcommand or null.
     */
    public CommandInterface findCommandByAlias(String alias) {
        for (final CommandInterface command : commands) {
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

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {

        if (args.length > 0) {
            final CommandInterface subCommand = findCommandByAlias(args[0]);

            ArrayList<String> argsl = new ArrayList<>(Arrays.asList(args));
            argsl.remove(0);
            
            if (subCommand != null) {
                if (!subCommand.onCommand(getZenixCommandSender(sender),
                        label, argsl.toArray(new String[argsl.size()]))) {
                    showHelp(sender, 0, subCommand);
                }
                return true;
            }

            try {
                showHelp(sender, Integer.parseInt(args[0]), null);
            } catch (final NumberFormatException e) {
                sender.sendMessage("Unkown command: " + args[0]);
            }
            return true;
        }

        showHelp(sender, 0, null);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command,
            String alias, String[] args) {

        for (final String arg : args) {
            sender.sendMessage(arg);
        }

        if (args.length == 1) {
            final List<String> completions = new ArrayList<String>();

            final String completionTarget = args[0].toLowerCase();

            for (final CommandInterface subCommand : commands) {
                final String subCommandName = subCommand.getName();

                if (subCommandName != null
                        && subCommandName.startsWith(completionTarget)) {
                    completions.add(subCommand.getName().toLowerCase());
                } else {
                    final String[] commandAliases = subCommand.getAliases();

                    if (commandAliases != null) {
                        for (String commandAlias : commandAliases) {
                            commandAlias = commandAlias.toLowerCase();
                            if (commandAlias.startsWith(completionTarget)) {
                                completions.add(commandAlias);
                                break;
                            }
                        }
                    }
                }
            }

            return completions;

        } else if (args.length > 1) {
            final CommandInterface subCommand = findCommandByAlias(args[0]);

            if (subCommand != null) {
                return subCommand.onTabComplete(getZenixCommandSender(sender),
                        alias, args);
            }
        }
        return null;
    }

    /**
     * Creates an zenix commandsender from commandsender.
     *
     * @param sender
     *            The commandsender.
     * @return The avatar command sender.
     */
    protected ZenixCommandSender getZenixCommandSender(CommandSender sender) {
        if (sender instanceof Player) {
            return new ZenixCommandSender(sender,
                    repository.getZenixUser((Player) sender));
        } else {
            return new ZenixCommandSender(sender);
        }
    }
    
}
