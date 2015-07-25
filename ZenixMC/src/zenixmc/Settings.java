package zenixmc;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import zenixmc.utils.MinecraftUtils;

/**
 * Wrapper around configuration file to fetch settings.
 * @author james
 */
public class Settings implements SettingsInterface {
	
	/**
	 * Plugins Configuration file to fetch settings from.
	 */
	private FileConfiguration config;
	
	public Settings(FileConfiguration config) {
		this.config = config;
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
