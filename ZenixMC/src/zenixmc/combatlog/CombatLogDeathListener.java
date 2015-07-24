package zenixmc.combatlog;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import zenixmc.ZenixMC;
import zenixmc.persistance.CachedZenixUserRepository;
import zenixmc.user.ZenixUserInterface;

public class CombatLogDeathListener {
	CombatLogManager clm;
	ZenixMC zm;
	CachedZenixUserRepository czur;
	
	
	public CombatLogDeathListener(CombatLogManager clm, ZenixMC zm, CachedZenixUserRepository czur){
		this.clm = clm;
		this.zm = zm;
		this.czur = czur;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		ZenixUserInterface player = czur.getZenixUser(e.getEntity());
		if(this.clm.tagged.containsKey(player)){
			this.clm.tagged.remove(player.getName());
		}
	}

}
