package zenixmc.organization;

import org.bukkit.ChatColor;

import zenixmc.user.ZenixUserInterface;

public class Message {
	
	public enum Type { USAGE, ERROR, SUCCESS, CLAN };
	
	//Base messages: 
	
	public String usage = "Usage: ";
	public String error = "Error: ";
	public String success = "Success: ";
	public String clan = "Clan: ";
	
	//Constructor vars
	
	public Type type;
	public ZenixUserInterface player;
	public String message;
	
	public Message(ZenixUserInterface player, String message, Type type) {
		this.player = player;
		this.message = message;
		this.type = type;
		
		if (type == Type.USAGE) {
			usage(player, message);
			return;
		} else if (type == Type.ERROR) {
			error(player, message);
			return;
		} else if (type == Type.SUCCESS) {
			success(player, message);
			return;
		} else if (type == Type.CLAN) {
			clan(player, message);
			return;
		}
		
		
	}
	
	//Methods
	
	public void error(ZenixUserInterface player, String message) {
		player.sendMessage(ChatColor.DARK_RED + error + message);
	}
	
	public void success (ZenixUserInterface player, String message) {
		player.sendMessage(ChatColor.DARK_GREEN + success + message);
	}
	
	public void usage (ZenixUserInterface player, String message) {
		player.sendMessage(ChatColor.GREEN + usage + message);
	}
	
	public void clan(ZenixUserInterface player, String message) {
		player.sendMessage(ChatColor.AQUA + clan + message);
	}
	
}
