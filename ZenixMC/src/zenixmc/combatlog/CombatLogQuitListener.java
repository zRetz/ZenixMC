package zenixmc.combatlog;

import org.bukkit.Location;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import zenixmc.ZenixMC;
import zenixmc.persistance.CachedZenixUserRepository;
import zenixmc.user.ZenixUserInterface;

/**
 * Class that listens to player's quit and refers to combat log.
 * @author ron
 *
 */

public class CombatLogQuitListener implements Listener {

	CombatLogManager clm;
	ZenixMC zm;
	CachedZenixUserRepository czur;
	
	String DCMsg = "";
	
	public CombatLogQuitListener(CombatLogManager clm, ZenixMC zm, CachedZenixUserRepository czur){
		this.clm = clm;
		this.zm = zm;
		this.czur = czur;
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e){
		ZenixUserInterface player = czur.getZenixUser(e.getPlayer());
		Location loc = player.getLocation();
		if(this.clm.tagged.containsKey(player.getName())){
		   if(clm.broadcastLocationOnLogout){
			   zm.broadcastMessage(null, zm.getSettings().getSortedColor() + player.getName() + " has left the game while being in combat, His items are in a chest "
					   + "located in those coordinates: X: " + loc.getX() + " Y: " + loc.getY() + " Z: " + loc.getZ());
		   }
		   dropItems(player);
		   if(clm.killOnLogout){
			   player.setHealth(0.0D);
		   }
		   
		   if(clm.tempBanOnLogout){
			   //James needs to add the temp ban system. 15 Minutes tempban.
		   }
		   
		   this.clm.tagged.remove(player.getName());
		}
	}
	
	public void setDCMsg(String string){
		DCMsg = string;
	}
	
	public void dropItems(ZenixUserInterface player){
		if(clm.dropItemsOnLogout){
			Location loc = player.getLocation().subtract(0.0D, -1.0D, 0.0D);
			loc.getBlock().setType(Material.CHEST);
			Chest chest = (Chest) loc.getBlock().getState();
			
			ItemStack[] playerInv = player.getInventory().getContents();
			ItemStack[] chestInv = new ItemStack[playerInv.length];
		    
			for(int i = 0; i < player.getInventory().getSize(); i++){
				chestInv[i] = playerInv[i];
			}
			
			for(int ii = 0; ii < chest.getInventory().getSize(); ii++){
				if(chestInv[ii] != null){
					chest.getInventory().addItem(chestInv[ii]);
				}
			}
			player.getInventory().clear();
		
			if(!this.clm.droppedItemsPlayer.contains(player.getName())){
				this.clm.droppedItemsPlayer.add(player.getName());
			}
		}
		
		if(clm.dropEXPOnLogout){
			player.setExp(0.0F);
			for (int g = 0; g < player.getLevel(); g++) {
				((ExperienceOrb)player.getLocation().getWorld().spawn(player.getLocation(), ExperienceOrb.class)).setExperience(2 * g + 1);
			}
			player.setLevel(0);
			if(!this.clm.droppedItemsPlayer.contains(player.getName())){
				this.clm.droppedItemsPlayer.add(player.getName());
			}

		}
		/*
		 * If armor is allowed I'll add it to drop armor too.
		 */
	}
	
	public void takeMoney(){
		//TODO economy system. 15% loss.
	}
	
	
}



