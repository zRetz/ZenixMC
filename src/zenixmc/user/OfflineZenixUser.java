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

public class OfflineZenixUser implements ZenixUserInterface {
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1413415934763147360L;
	
	/**
	 * Bukkit representation of the offline user.
	 */
	private final OfflinePlayer offlinePlayer;
	
	/**
	 * Online user data.
	 */
	private final ZenixUser user;
	
	public OfflineZenixUser(OfflinePlayer player, ZenixUser user) {
		this.offlinePlayer = player;
		this.user = user;
	}
	
	public OfflinePlayer getOfflinePlayer() {
		return offlinePlayer;
	}

	@Override
	public void setZenixMC(ZenixMCInterface zenix) {
		user.setZenixMC(zenix);
	}

	@Override
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		user.setEventDispatcher(eventDispatcher);
	}

	@Override
	public boolean isAuthorised(String node) {
		return user.isAuthorised(node);
	}

	@Override
	public void setPlayer(Player player) {
		return;
	}

	@Override
	public Player getPlayer() {
		return null;
	}

	@Override
	public String getName() {
		return user.getName();
	}

	@Override
	public String getDisplayName() {
		return user.getDisplayName();
	}

	@Override
	public UUID getUniqueId() {
		return user.getUniqueId();
	}

	@Override
	public Location getLocation() {
		return user.getLocation();
	}

	@Override
	public World getWorld() {
		return user.getWorld();
	}

	@Override
	public void setExhaustion(float value) {
		return;
	}

	@Override
	public float getExhaustion() {
		return 0;
	}

	@Override
	public void setFoodLevel(int value) {
		return;
	}

	@Override
	public int getFoodLevel() {
		return 0;
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
		return user.getBendingPlayer();
	}

	@Override
	public void setOrganizationPlayer(OrganizationPlayerInterface value) {
		return;
	}

	@Override
	public OrganizationPlayerInterface getOrganizationPlayer() {
		return user.getOrganizationPlayer();
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
		return user.isMuted();
	}

	@Override
	public void setFrozen(boolean value) {
		return;
	}

	@Override
	public boolean isFrozen() {
		return user.isFrozen();
	}

	@Override
	public void setGodMode(boolean value) {
		return;
	}

	@Override
	public boolean isGodMode() {
		return user.isGodMode();
	}

	@Override
	public void setVanished(boolean value) {
		return;
	}

	@Override
	public boolean isVanished() {
		return user.isVanished();
	}

	@Override
	public void setSocialSpy(boolean value) {
		return;
	}

	@Override
	public boolean isSocialSpying() {
		return user.isSocialSpying();
	}

	@Override
	public void incrementWarning(long time, String... reason) {
		user.incrementWarning(time, reason);
	}

	@Override
	public void decrementWarning(String... reason) {
		user.decrementWarning(reason);
	}

	@Override
	public void setWarning(Warning value) {
		user.setWarning(value);
	}

	@Override
	public Warning getWarning() {
		return user.getWarning();
	}

	@Override
	public void setHome(String name, Location loc) {
		return;
	}

	@Override
	public void deleteHome(String name) {
		user.deleteHome(name);
	}

	@Override
	public Home getHome(String name) {
		return user.getHome(name);
	}

	@Override
	public Home getHome(Location location) {
		return user.getHome(location);
	}

	@Override
	public void setHomes(List<Home> homes) {
		return;
	}

	@Override
	public List<Home> getHomes() {
		return user.getHomes();
	}

	@Override
	public boolean hasHome() {
		return user.hasHome();
	}

	@Override
	public boolean homeExists(String name) {
		return user.homeExists(name);
	}

	@Override
	public boolean homeExists(Location location) {
		return user.homeExists(location);
	}

	@Override
	public void clearMail() {
		user.clearMail();
	}

	@Override
	public void addMail(String mail) {
		user.addMail(mail);
	}

	@Override
	public String popMail() {
		return user.popMail();
	}

	@Override
	public String popMail(int index) {
		return user.popMail(index);
	}

	@Override
	public String getMail() {
		return user.getMail();
	}

	@Override
	public String getMail(int index) {
		return user.getMail(index);
	}

	@Override
	public void setMails(List<String> mails) {
		user.setMails(mails);
	}

	@Override
	public List<String> getMails() {
		return user.getMails();
	}

	@Override
	public boolean isAFK() {
		return user.isAFK();
	}

	@Override
	public void setLastLocation(Location loc) {
		return;
	}

	@Override
	public Location getLastLocation() {
		return user.getLastLocation();
	}

	@Override
	public void setTeleport(Teleport value) {
		return;
	}

	@Override
	public Teleport getTeleport() {
		return user.getTeleport();
	}

	@Override
	public void setTeleportRequester(ZenixUserInterface teleportRequester) {
		return;
	}

	@Override
	public ZenixUserInterface getTeleportRequester() {
		return user.getTeleportRequester();
	}

	@Override
	public long getTeleportRequestTime() {
		return user.getTeleportRequestTime();
	}

	@Override
	public void setStartActivity(long startActivity) {
		return;
	}

	@Override
	public long getStartActivity() {
		return user.getStartActivity();
	}

	@Override
	public void setLastOnlineActivity(long lastOnlineActivity) {
		return;
	}

	@Override
	public long getLastOnlineActivity() {
		return user.getLastOnlineActivity();
	}

	@Override
	public void setLastActivity(long lastActivity) {
		return;
	}

	@Override
	public long getLastActivity() {
		return user.getLastActivity();
	}

	@Override
	public long getLastThrottledAction() {
		return user.getLastThrottledAction();
	}

	@Override
	public void setJail(String jail) { 
		user.setJail(jail);
	}

	@Override
	public String getJail() {
		return user.getJail();
	}

	@Override
	public boolean isJailed() {
		return user.isJailed();
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
		return user.getIgnoredUsers();
	}

	@Override
	public boolean isIgnoredUser(UUID uuid) {
		return user.isIgnoredUser(uuid);
	}

	@Override
	public boolean isIgnoredUser(ZenixUserInterface zui) {
		return user.isIgnoredUser(zui);
	}

	@Override
	public void sendMessage(String message) {
		throw new UnsupportedOperationException("OfflienUsers can't be sent messages.");
	}

	@Override
	public ZenixCommandSender getCommandSender() {
		return user.getCommandSender();
	}

	@Override
	public void handleSerialize() {
		throw new UnsupportedOperationException("OfflineUsers can't handle serialization.");
	}

	@Override
	public ZenixUser toOnlineUser(Player player) {
		user.setPlayer(player);
		return user;
	}

	@Override
	public OfflineZenixUser toOfflineUser(OfflinePlayer player) {
		return this;
	}

	@Override
	public int getPlayerKills() {
		return 0;
	}

	@Override
	public int compareTo(ZenixUserInterface zui) {
		return zui.getUniqueId().compareTo(this.getUniqueId());
	}
}
