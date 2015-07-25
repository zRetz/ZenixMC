package zenixmc.combatlog;

import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import zenixmc.ZenixMC;
import zenixmc.ZenixMCInterface;
import zenixmc.persistance.CachedZenixUserRepository;
import zenixmc.user.ZenixUserInterface;

public class CombatLogDamageByEntityListener implements Listener {

	CombatLogManager clm;
	ZenixMC zm;
	CachedZenixUserRepository czur;
	
	public CombatLogDamageByEntityListener(CombatLogManager clm, ZenixMC zm, CachedZenixUserRepository czur){
		this.clm = clm;
		this.zm = zm;
		this.czur = czur;
	}
	
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
		if(!e.isCancelled()){
			Entity victim = e.getEntity();
			Entity attacker = e.getDamager();
		}
	}
	
	public void tagByPlayer(ZenixUserInterface attacker, ZenixUserInterface victim){
		if(!this.clm.tagged.containsKey(attacker.getName())){
			this.clm.tagged.put(attacker.getName(), Long.valueOf(System.currentTimeMillis() / 1000L));
		}
	}
	
	public void tagByMob(Entity attacker, ZenixUserInterface victim){

	}
}
