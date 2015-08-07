package zenixmc.user;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import zenixmc.ZenixMCInterface;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.command.ZenixCommandSender;
import zenixmc.event.EventDispatcher;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Teleport;
import zenixmc.user.objects.Warning;

public class Console implements ZenixUserInterface {
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 8982813029236727452L;

	/**
	 * The plugin.
	 */
	private final ZenixMCInterface zenix;
	
	/**
	 * The name of console.
	 */
	private final String name;
	
	/**
	 * The consoles uuid;
	 */
	private final UUID uuid;
	
	/**
	 * The consoles input command sender.
	 */
	private ZenixCommandSender zenixCommandSender;
	
	public Console(String name, ZenixMCInterface zenix) {
		this.zenix = zenix;
		this.name = name;
		this.uuid = UUID.randomUUID();
	}

	@Override
	public boolean isAuthorised(String node) {
		return false;
	}

	@Override
	public Player getPlayer() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayName() {
		return name;
	}

	@Override
	public UUID getUniqueId() {
		return uuid;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public World getWorld() {
		return null;
	}

	@Override
	public void setHealth(double value) {
		return;
	}

	@Override
	public double getHealth() {
		return 0;
	}

	@Override
	public double getMaxHealth() {
		return 0;
	}

	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public boolean isOnline() {
		return false;
	}

	@Override
	public PlayerInventory getInventory() {
		return null;
	}

	@Override
	public void kickPlayer(String reason) {
		return;
	}

	@Override
	public void setLevel(int value) {
		return;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public void setExp(float value) {
		return;
	}

	@Override
	public float getExp() {
		return 0;
	}

	@Override
	public boolean teleport(Location loc) {
		return false;
	}

	@Override
	public void setBendingPlayer(BendingPlayerInterface value) {
		return;
	}

	@Override
	public BendingPlayerInterface getBendingPlayer() {
		return null;
	}

	@Override
	public void setOrganizationPlayer(OrganizationPlayerInterface value) {
		return;
	}

	@Override
	public OrganizationPlayerInterface getOrganizationPlayer() {
		return null;
	}

	@Override
	public boolean canBuild() {
		return false;
	}

	@Override
	public void setMuted(boolean value) {
		return;
	}

	@Override
	public boolean isMuted() {
		return false;
	}

	@Override
	public void setFrozen(boolean value) {
		return;
	}

	@Override
	public boolean isFrozen() {
		return false;
	}

	@Override
	public void setGodMode(boolean value) {
		return;
	}

	@Override
	public boolean isGodMode() {
		return false;
	}

	@Override
	public void setVanished(boolean value) {
		return;
	}

	@Override
	public boolean isVanished() {
		return false;
	}

	@Override
	public void setSocialSpy(boolean value) {
		return;
	}

	@Override
	public boolean isSocialSpying() {
		return false;
	}

	@Override
	public void incrementWarning(long time, String... reason) {
		return;
	}

	@Override
	public void decrementWarning(String... reason) {
		return;
	}

	@Override
	public Warning getWarning() {
		return null;
	}

	@Override
	public void setHome(String name, Location loc) {
		return;
	}

	@Override
	public void deleteHome(String name) {
		return;
	}

	@Override
	public Home getHome(String name) {
		return null;
	}

	@Override
	public Home getHome(Location location) {
		return null;
	}

	@Override
	public void setHomes(List<Home> homes) {
		return;
	}

	@Override
	public List<Home> getHomes() {
		return null;
	}

	@Override
	public boolean hasHome() {
		return false;
	}

	@Override
	public boolean homeExists(String name) {
		return false;
	}

	@Override
	public boolean homeExists(Location location) {
		return false;
	}

	@Override
	public void clearMail() {
		return;
	}

	@Override
	public void addMail(String mail) {
		return;
	}

	@Override
	public String popMail() {
		return null;
	}

	@Override
	public String popMail(int index) {
		return null;
	}

	@Override
	public String getMail() {
		return null;
	}

	@Override
	public String getMail(int index) {
		return null;
	}

	@Override
	public void setMails(List<String> mails) {
		return;
	}

	@Override
	public List<String> getMails() {
		return null;
	}

	@Override
	public boolean isAFK() {
		return false;
	}

	@Override
	public void setLastLocation(Location loc) {
		return;
	}

	@Override
	public Location getLastLocation() {
		return null;
	}

	@Override
	public Teleport getTeleport() {
		return null;
	}

	@Override
	public void setTeleportRequester(ZenixUserInterface teleportRequester) {
		return;
	}

	@Override
	public ZenixUserInterface getTeleportRequester() {
		return null;
	}

	@Override
	public long getTeleportRequestTime() {
		return 0;
	}

	@Override
	public void setStartActivity(long startActivity) {
		return;
	}

	@Override
	public long getStartActivity() {
		return 0;
	}

	@Override
	public void setLastOnlineActivity(long lastOnlineActivity) {
		return;
	}

	@Override
	public long getLastOnlineActivity() {
		return 0;
	}

	@Override
	public void setLastActivity(long lastActivity) {
		return;
	}

	@Override
	public long getLastActivity() {
		return 0;
	}

	@Override
	public long getLastThrottledAction() {
		return 0;
	}

	@Override
	public void setJail(String jail) {
		return;
	}

	@Override
	public String getJail() {
		return null;
	}

	@Override
	public boolean isJailed() {
		return false;
	}

	@Override
	public void ignoreUser(UUID uuid) {
		return;
	}

	@Override
	public void ignoreUser(ZenixUserInterface zui) {
		return;
	}

	@Override
	public void unIgnoreUser(UUID uuid) {
		return;
	}

	@Override
	public void unIgnoreUser(ZenixUserInterface zui) {
		return;
	}

	@Override
	public void setIgnoredUsers(List<UUID> users) {
		return;
	}

	@Override
	public List<UUID> getIgnoredUsers() {
		return null;
	}

	@Override
	public boolean isIgnoredUser(UUID uuid) {
		return false;
	}

	@Override
	public boolean isIgnoredUser(ZenixUserInterface zui) {
		return false;
	}

	@Override
	public void sendMessage(String message) {
		getCommandSender().commandSender.sendMessage(message);
	}

	@Override
	public ZenixCommandSender getCommandSender() {
		if (zenixCommandSender == null) {
			zenixCommandSender = new ZenixCommandSender(this.zenix.getServer().getConsoleSender(), this);
		}
		return zenixCommandSender;
	}

	@Override
	public void setZenixMC(ZenixMCInterface zenix) {
		return;
	}

	@Override
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		return;
	}

	@Override
	public void setPlayer(Player player) {
		return;
	}

	@Override
	public void setWarning(Warning value) {
		return;
	}

	@Override
	public void setTeleport(Teleport value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleSerialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setExhaustion(float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getExhaustion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFoodLevel(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFoodLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ZenixUser toOnlineUser(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfflineZenixUser toOfflineUser(OfflinePlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPlayerKills() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(ZenixUserInterface zui) {
		return zui.getUniqueId().compareTo(getUniqueId());
	}
	
}
