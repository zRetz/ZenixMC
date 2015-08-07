/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import zenixmc.ZenixMCInterface;
import zenixmc.command.commands.AbstractMainCommand;
import zenixmc.command.commands.CommandInterface;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;

/**
 * The main command executer.
 * @author james
 */
public class MainCommandExecuter implements Listener {

	/**
	 * The plugin.
	 */
	protected ZenixMCInterface zenix;
	
    /**
     * The amount of commands to list on one page in the help text.
     */
    protected int commandsPerPage = 10;
    
    /**
     * The manager to handle users.
     */
    protected ZenixUserManager manager;

    /**
     * The registered mainCommands.
     */
    protected List<AbstractMainCommand> mainCommands = new ArrayList<>();

    /**
     * Creates the main command executor.
     *
     * @param manager
     *            The manager to use.
     */
    public MainCommandExecuter(ZenixMCInterface zenix, ZenixUserManager manager) {
    	this.zenix = zenix;
        this.manager = manager;
    }
    
    /**
     * Registers a maincommand.
     *
     * @param mainCommand
     *            The maincommand to register.
     */
    public void addMainCommand(AbstractMainCommand command) {
        mainCommands.add(command);
    }

    /**
     * Shows help text.
     *
     * @param sender
     *   	The executor of the command.
     * @param page
     *    	The page of the help text.
     * @param command
     *    	The command to get the help text for, null when main text.
     */
    public void showHelp(ZenixCommandSender sender, int page, AbstractMainCommand command) {
	        final String[] help = command == null ? null : command.getHelp();
	
	        sender.zui.sendMessage(zenix.getSettings().getMatchingNotificationColor() + "---- "
	                + (command == null ? "Zenix" : command.getTitle())
	                + ": "
	                + (command == null ? "Index" : "Help")
	                + " ("
	                + page
	                + "/"
	                + ((command == null ? mainCommands.size() : (help == null ? 0
	                        : help.length)) / commandsPerPage) + ") ------");
	
	        final int offset = page * commandsPerPage;
	
	        for (int i = offset; i < offset + commandsPerPage; i++) {
	            if (command != null && help != null) {
	                if (i >= 0 && i < help.length) {
	                    sender.zui.sendMessage(help[i]);
	                }
	            } else if (i >= 0 && i < (command == null ? mainCommands.size() : command.getSubCommands().size())) {
	                final CommandInterface Command = command == null ? mainCommands
	                        .get(i) : command.getSubCommands().get(i);
	                if (Command != null) {
		                sender.zui.sendMessage(zenix.getSettings().getNotificationColor() + "-- !" + (command == null ? "" : command.getName() + " ") + Command.getName() + " "
		                        + Command.getFormat() + " -- "
		                        + Command.getDescription());
	                }
	            }
	        }
    }
    
    /**
     * @param cmd
     * 		String to check.
     * @return <code>true</code> If the string is equal to a commands name.
     */
    public boolean isMainCommand(String cmd) {
    	
    	for (AbstractMainCommand c : mainCommands) {
    		if (c.getName().equalsIgnoreCase(cmd)) {
    			return true;
    		}
    		if (c.getAliases() != null) {
    			if (JavaUtil.arrayContainsElements(c.getAliases(), cmd)) {
    				return true;
    			}
    		}
    	}
    	return false;
    }

    public boolean onCommand(ZenixCommandSender sender, String label, String[] args) {
    	
		for (AbstractMainCommand c : mainCommands) {
			if (label.equals(c.getName())) {
				if (!(c.onCommand(sender, label, JavaUtil.removeElementsFromArray(args, String.class, 0)))) {
					showHelp(sender, 0, c);
				}
				return true;
			}
		}
		showHelp(sender, 0, null);
		return true;
    }

    /*
    public List<String> onTabComplete(ZenixCommandSender sender, Command command,
            String alias, String[] args) {

        for (final String arg : args) {
            sender.zui.sendMessage(arg);
        }

        if (args.length == 1) {
            final List<String> completions = new ArrayList<String>();

            final String completionTarget = args[0].toLowerCase();

            for (final CommandInterface subCommand : mainCommands) {
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
            final CommandInterface subCommand = findSubCommandByAlias(null, args[0]);

            if (subCommand != null) {
                return subCommand.onTabComplete(sender,
                        alias, args);
            }
        }
        return null;
    }
    */
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCMD(AsyncPlayerChatEvent e){
    	
    	ZenixUserInterface zui = manager.getZenixUser(e.getPlayer());
    	
        if (e.getMessage().startsWith("!")) {
        	String c = e.getMessage().substring(1);
        	String[] a = c.split(" ");
        	if (isMainCommand(a[0])) {
        		onCommand(zui.getCommandSender(), a[0], a);
        	}else {
        		zui.sendMessage("Unknown command: " + a[0]);
        		showHelp(zui.getCommandSender(), 0, null);
        	}
        	e.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void cancelCMD(PlayerCommandPreprocessEvent e) {
    	e.setCancelled(true);
    }
}
