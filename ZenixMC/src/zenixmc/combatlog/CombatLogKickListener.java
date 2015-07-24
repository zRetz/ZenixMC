package zenixmc.combatlog;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import zenixmc.ZenixMC;
import zenixmc.persistance.CachedZenixUserRepository;
import zenixmc.user.ZenixUserInterface;

public class CombatLogKickListener implements Listener {
	
	CombatLogManager clm;
	ZenixMC zm;
	CachedZenixUserRepository czur;
	
	
	public CombatLogKickListener(CombatLogManager clm, ZenixMC zm, CachedZenixUserRepository czur){
		this.clm = clm;
		this.zm = zm;
		this.czur = czur;
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e){
		ZenixUserInterface player = czur.getZenixUser(e.getPlayer());
		if(this.clm.tagged.containsKey(player)){
			this.clm.tagged.remove(player.getName());
		}
	}

}
