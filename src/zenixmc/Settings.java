package zenixmc;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import zenixmc.utils.DateUtil;
import zenixmc.utils.ExceptionUtil;
import zenixmc.utils.MinecraftUtils;

/**
 * Wrapper around configuration file to fetch settings.
 * @author james
 */
public class Settings implements SettingsInterface {
	
	/**
	 * The plugin.
	 */
	private ZenixMCInterface zenix;
	
	/**
	 * Plugins Configuration file to fetch settings from.
	 */
	private FileConfiguration config;
	
	/**
	 * Instantiate.
	 * @param zenix
	 * 		The plugin.
	 * @param config
	 * 		The plugins configuration file.
	 */
	public Settings(ZenixMCInterface zenix, FileConfiguration config) {
		
		config.addDefault("warningBanFormat", "minute");
		config.addDefault("teleportTime", 5000);
    	config.addDefault("canMoveBeforeTeleport", false);
    	config.addDefault("errorColor", "RED");
    	config.addDefault("notificationColor", "GREEN");
    	config.addDefault("sortedColor", "GOLD");
    	config.addDefault("materialBlackList", Arrays.asList("TNT"));
    	config.addDefault("commandBlackList", Arrays.asList("hello"));
    	config.addDefault("allowSilentJoinQuit", true);
    	config.addDefault("quitMessage", "Zenix wishes you farewell, ");
    	config.addDefault("joinMessage", "Zenix greets you, ");
    	config.addDefault("kickMessage", "Zenix kicked you.");
    	config.addDefault("banMessage", "Zenix banned you.");
    	
    	config.options().copyDefaults(true);
		
    	zenix.saveConfig();
    	
		this.config = config;
		this.zenix = zenix;
	}
	
	@Override
	public String getWarningBanFormat() {
		
		String format = config.getString("warningBanFormat");
		
		return format;
	}
	
	@Override
	public long getTeleportTime() {
		return config.getLong("teleportTime");
	}
	
	@Override
	public boolean canMoveBeforeTeleport() {
		return config.getBoolean("canMoveBeforeTeleport");
	}
	
	@Override
	public ChatColor getErrorColor() {
		return ChatColor.valueOf(config.getString("errorColor"));
	}

	@Override
	public ChatColor getNotificationColor() {
		return ChatColor.valueOf(config.getString("notificationColor"));
	}

	@Override
	public ChatColor getSortedColor() {
		return ChatColor.valueOf(config.getString("sortedColor"));
	}

	@Override
	public List<Material> getBlockBlackList() {
		return MinecraftUtils.stringToMaterialList(config.getStringList("materialBlackList"));
	}
	
	@Override
	public List<String> getCommandBlackList() {
		return config.getStringList("commandBlackList");
	}

	@Override
	public boolean allowSilentJoinQuit() {
		return config.getBoolean("allowSilentJoinQuit");
	}

	@Override
	public String getJoinMessage() {
		return config.getString("joinMessage");
	}

	@Override
	public String getQuitMessage() {
		return config.getString("quitMessage");
	}

	@Override
	public String getBanMessage() {
		return config.getString("banMessage");
	}

	@Override
	public String getKickMessage() {
		return config.getString("kickMessage");
	}

}
