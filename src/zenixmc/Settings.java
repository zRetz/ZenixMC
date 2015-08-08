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
    	config.addDefault("matchingNotificationColor", "DARK_GREEN");
    	config.addDefault("sortedColor", "GOLD");
    	config.addDefault("clanColor", "DARK_BLUE");
    	config.addDefault("matchingClanColor", "BLUE");
    	config.addDefault("bendingColor", "DARK_PURPLE");
    	config.addDefault("matchingBendingColor", "LIGHT_PURPLE");
    	config.addDefault("essentialColor", "WHITE");
    	config.addDefault("matchingEssentialColor", "GRAY");
    	config.addDefault("materialBlackList", Arrays.asList("TNT"));
    	config.addDefault("commandBlackList", Arrays.asList("hello"));
    	config.addDefault("allowSilentJoinQuit", true);
    	config.addDefault("quitMessage", "We wish <zenixUser> farewell.");
    	config.addDefault("joinMessage", "We greet <zenixUser>.");
    	config.addDefault("kickMessage", "Zenix kicked <zenixUser>.");
    	config.addDefault("banMessage", "Zenix banned <zenixUser>.");
    	config.addDefault("clanDescMessage", "Default Clan Description ;3");
    	config.addDefault("maxClanDescLength", 21);
    	config.addDefault("clanInviteMessage", "<orgPlayer> has been invited to <clan> by <zenixUser>.");
    	config.addDefault("clanReDescMessage", "<clan>'s description has been changed from <string> to <string> by.");
    	config.addDefault("clanReNameMessage", "<clan>'s name has been changed from <string> to <string>.");
    	config.addDefault("clanJoinMessage", "<orgPlayer> has joined <clan>.");
    	config.addDefault("clanLeaveMessage", "<orgPlayer> has willingly left <clan>.");
    	config.addDefault("clanKickMessage", "<orgPlayer> has been forcefully kicked from <clan> by <zenixUser>.");
    	config.addDefault("clanDisbandMessage", "<clan> has been disbanded by <zenixUser>.");
    	config.addDefault("clanBanMessage", "<orgPlayer> has been banned from <clan> by <zenixUser>.");
    	config.addDefault("clanPardonMessage", "<orgPlayer> has been pardoned from <clan>'s banlist by <zenixUser>.");
    	config.addDefault("clanClaimMessage", "<clan> has claimed Chunk X: <integer> Chunk Z: <integer>");
    	config.addDefault("clanOverClaimedMessage", "<clan>'s land has been overclaimed by <clan> at Chunk X: <integer> Chunk Z: <integer>");
    	config.addDefault("clanUnClaimMessage", "<clan> has unclaimed Chunk X: <integer> Chunk Z: <integer>");
    	config.addDefault("clanDeclareWarMessage", "<clan> has declared war on <clan>.");
    	config.addDefault("clanDeclareNeutralMessage", "<clan> and <clan> have declared neutrality.");
    	config.addDefault("clanInformedNeutralMessage", "Your clan has informed <clan> of your request for neutrality.");
    	config.addDefault("clanWishNeutralMessage", "<clan> wishes to declare neutrality with your clan.");
    	config.addDefault("clanDeclareAllyMessage", "<clan> and <clan> have formed an alliance");
    	config.addDefault("clanInformedAllyMessage", "Your clan has informed <clan> of your request for an alliance.");
    	config.addDefault("clanWishAllyMessage", "<clan> wishes to form an alliance with your clan.");
    	config.addDefault("clanNeedInviteTrueMessage", "Invitations are now required to join <clan>. Courtesy: <zenixUser>");
    	config.addDefault("clanNeedInviteFalseMessage", "Invitations are no longer required to join <clan>. Courtesy: <zenixUser>");
    	
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
	public ChatColor getMatchingNotificationColor() {
		return ChatColor.valueOf(config.getString("matchingNotificationColor"));
	}

	@Override
	public ChatColor getSortedColor() {
		return ChatColor.valueOf(config.getString("sortedColor"));
	}
	
	@Override
	public ChatColor getClanColor() {
		return ChatColor.valueOf(config.getString("clanColor"));
	}
	
	@Override
	public ChatColor getMatchingClanColor() {
		return ChatColor.valueOf(config.getString("matchingClanColor"));
	}

	@Override
	public ChatColor getBendingColor() {
		return ChatColor.valueOf(config.getString("bendingColor"));
	}

	@Override
	public ChatColor getMatchingBendingColor() {
		return ChatColor.valueOf(config.getString("matchingBendingColor"));
	}

	@Override
	public ChatColor getEssentialColor() {
		return ChatColor.valueOf(config.getString("essentialColor"));
	}

	@Override
	public ChatColor getMatchingEssentialColor() {
		return ChatColor.valueOf(config.getString("matchingEssentialColor"));
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
	public String joinMessage() {
		return config.getString("joinMessage");
	}

	@Override
	public String quitMessage() {
		return config.getString("quitMessage");
	}

	@Override
	public String banMessage() {
		return config.getString("banMessage");
	}

	@Override
	public String kickMessage() {
		return config.getString("kickMessage");
	}
	
	@Override
	public String clanDescMessage() {
		return config.getString("clanDescMessage");
	}
	
	@Override
	public int maxClanDescLength() {
		return config.getInt("maxClanDescLength");
	}

	@Override
	public String clanInviteMessage() {
		return config.getString("clanInviteMessage");
	}

	@Override
	public String clanReNameMessage() {
		return config.getString("clanReNameMessage");
	}

	@Override
	public String clanReDescMessage() {
		return config.getString("clanReDescMessage");
	}

	@Override
	public String clanJoinMessage() {
		return config.getString("clanJoinMessage");
	}

	@Override
	public String clanLeaveMessage() {
		return config.getString("clanLeaveMessage");
	}

	@Override
	public String clanKickMessage() {
		return config.getString("clanKickMessage");
	}

	@Override
	public String clanDisbandMessage() {
		return config.getString("clanDisbandMessage");
	}

	@Override
	public String clanBanMessage() {
		return config.getString("clanBanMessage");
	}

	@Override
	public String clanPardonMessage() {
		return config.getString("clanPardonMessage");
	}

	@Override
	public String clanClaimMessage() {
		return config.getString("clanClaimMessage");
	}

	@Override
	public String clanOverClaimedMessage() {
		return config.getString("clanOverClaimMessage");
	}

	@Override
	public String clanUnClaimMessage() {
		return config.getString("clanUnClaimMessage");
	}

	@Override
	public String clanDeclareWarMessage() {
		return config.getString("clanDeclareWarMessage");
	}

	@Override
	public String clanDeclareNeutralMessage() {
		return config.getString("clanDeclareNeutralMessage");
	}

	@Override
	public String clanInformedNeutralMessage() {
		return config.getString("clanInformedNeutralMessage");
	}

	@Override
	public String clanWishNeutralMessage() {
		return config.getString("clanWishNeutralMessage");
	}

	@Override
	public String clanDeclareAllyMessage() {
		return config.getString("clanDeclareAllyMessage");
	}

	@Override
	public String clanInformedAllyMessage() {
		return config.getString("clanInformedAllyMessage");
	}

	@Override
	public String clanWishAllyMessage() {
		return config.getString("clanWishAllyMessage");
	}

	@Override
	public String clanNeedInviteTrueMessage() {
		return config.getString("clanNeedInviteTrueMessage");
	}

	@Override
	public String clanNeedInviteFalseMessage() {
		return config.getString("clanNeedInviteFalseMessage");
	}

}


