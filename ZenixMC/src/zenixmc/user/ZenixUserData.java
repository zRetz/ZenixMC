package zenixmc.user;

import java.util.List;

import zenixmc.user.objects.Home;
import zenixmc.user.objects.Warning;

/**
 * Class to hold a zenix users data before serialisation or de-serialisation.
 * @author james
 */
public class ZenixUserData {
	
	private boolean muted;
	
	private boolean frozen;
	
	private boolean godMode;
	
	private boolean vanished;
	
	private boolean socialSpy;
	
	private Warning warning;
	
	private List<Home> homes;
	
	private List<String> mails;
	
	private String jail;
	
	private List<String> ignoredUsers;
	
	private long lastOnlineActivity;
	
	private long lastActivity;
	
	public ZenixUserData() {
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public boolean isGodMode() {
		return godMode;
	}

	public void setGodMode(boolean godMode) {
		this.godMode = godMode;
	}

	public boolean isVanished() {
		return vanished;
	}

	public void setVanished(boolean vanished) {
		this.vanished = vanished;
	}

	public boolean isSocialSpy() {
		return socialSpy;
	}

	public void setSocialSpy(boolean socialSpy) {
		this.socialSpy = socialSpy;
	}

	public Warning getWarning() {
		return warning;
	}

	public void setWarning(Warning warning) {
		this.warning = warning;
	}

	public List<Home> getHomes() {
		return homes;
	}

	public void setHomes(List<Home> homes) {
		this.homes = homes;
	}

	public List<String> getMails() {
		return mails;
	}

	public void setMails(List<String> mails) {
		this.mails = mails;
	}

	public String getJail() {
		return jail;
	}

	public void setJail(String jail) {
		this.jail = jail;
	}

	public List<String> getIgnoredUsers() {
		return ignoredUsers;
	}

	public void setIgnoredUsers(List<String> ignoredUsers) {
		this.ignoredUsers = ignoredUsers;
	}

	public long getLastOnlineActivity() {
		return lastOnlineActivity;
	}

	public void setLastOnlineActivity(long lastOnlineActivity) {
		this.lastOnlineActivity = lastOnlineActivity;
	}

	public long getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(long lastActivity) {
		this.lastActivity = lastActivity;
	}
	
}
