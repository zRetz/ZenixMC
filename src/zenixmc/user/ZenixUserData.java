package zenixmc.user;

import java.util.List;
import java.util.UUID;

import zenixmc.ZenixMCInterface;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Warning;

/**
 * Class to hold a zenix users data before serialisation or de-serialisation.
 * @author james
 */
public class ZenixUserData {
	
	/**
     * The users ability to speak.
     */
	public boolean muted;
	
	/**
     * The users ability to move.
     */
	public boolean frozen;
	
	/**
     * The users ability to take damage.
     */
	public boolean godMode;
	
	/**
     * The users ability to be seen.
     */
	public boolean vanished;
	
	/**
     * The users ability to socially spy.
     */
	public boolean socialSpy;
	
	/**
     * The users amount of warnings and sentence.
     */
	public Warning warning;
	
	/**
     * The users collection of homes.
     */
	public List<Home> homes;
	
	/**
     * The users collection of mail.
     */
	public List<String> mails;
	
	/**
     * The users current jail. (Can be null)
     */
	public String jail;
	
	/**
     * The users collection of ignoredUsers.
     */
	public List<UUID> ignoredUsers;
	
	/**
     * The start time of the users last activity. (Log-in time.) 
     */
	public long startActivity;
	
	/**
     * The duration of the users last session.
     */
	public long lastOnlineActivity;
	
	/**
     * The end time of the users last activity. (Can be log-off time.)
     */
	public long lastActivity;
	
	public ZenixUserData() {
	}
	
}
