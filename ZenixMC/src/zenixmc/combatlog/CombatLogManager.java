package zenixmc.combatlog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import zenixmc.ZenixMC;
import zenixmc.ZenixMCInterface;
import zenixmc.user.ZenixUserInterface;

/**
 * The manager class of the whole combat log feature.
 * @author Ron
 *
 */

public class CombatLogManager {
	/**
	 * Zenix User Interface.
	 */
	
	ZenixUserInterface zui;
	
	/**
	 * ZenixMC interface.
	 */
	
	ZenixMCInterface zmi;
	
	/**
	 * Main class.
	 */
	
	ZenixMC zm;

	/**
	 * A HashMap of players' names and the time left until the combat tag expires (Hit by player).
	 */
	
	public HashMap<String, Long> tagged = new HashMap();
	
	/**
	 * A list of players' names who got their items dropped after quitting the game.
	 */
	
	public List<String> droppedItemsPlayer;
	
	/**
	 * true = puts the player's items in a spawned chest when he logs out.
	 */
	
	boolean dropItemsOnLogout;
	
	/**
	 * true = drops the player's EXP when he logs out.
	 */
	
	boolean dropEXPOnLogout;
	
	/**
	 * true = broadcasts the location of the chest that contains the dropped items of the player that logged out.
	 */
	
	boolean broadcastLocationOnLogout;
	
	/**
	 * true = tempbans the player that logged out.
	 */
	
	boolean tempBanOnLogout;
	
	/**
	 * true = kills the player that logged out.
	 */
	
	boolean killOnLogout;
	
	/**
	 * The duration of the combat tag to stay after being hit by a player.
	 */
	
	public int playerTagTime = 10;
	
	/**
	 * The duration of the combat tag to stay after being hit by a mob.
	 */
	
	public int mobTagTime = 5;
	
	/**
	 * The message that will be sent to the player when he is tagged.
	 */
	
	public String taggedMsg = "";
	
	/**
	 * The message that will be sent to the player when he is untagged.
	 */
	
	public String unTaggedMsg = "";
	
	/**
	 * The Combat Log Manager.
	 * @param zm Main class (plugin).
	 */
	
	public CombatLogManager(ZenixMC zm, ZenixMCInterface zmi){
		
	}
	
	public void tagTimer(){
		zmi.getScheduler().scheduleSyncRepeatingTask(zm, new Runnable() {
			
			@Override
			public void run() {
				Iterator<Map.Entry<String, Long>> iterator = tagged.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, Long> iterator2 = (Map.Entry)iterator.next();
					if(System.currentTimeMillis() / 1000L - ((Long)iterator2.getValue()).longValue() >= playerTagTime){
			iterator.remove();
			//TODO send removed tag msg...
			//Other listeners and more...
		}
				}
			}
		}, 0L, 20L);
	}
}
