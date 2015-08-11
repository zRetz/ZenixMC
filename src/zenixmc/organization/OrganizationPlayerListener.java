package zenixmc.organization;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.event.organization.clan.ClanClaimEvent;
import zenixmc.event.organization.clan.ClanInviteEvent;
import zenixmc.event.organization.clan.ClanJoinEvent;
import zenixmc.event.organization.clan.ClanReDescEvent;
import zenixmc.event.organization.clan.ClanReNameEvent;
import zenixmc.organization.clans.Clan;
import zenixmc.organization.clans.Territory;
import zenixmc.organization.clans.TerritoryManager;
import zenixmc.organization.clans.defaults.Wild;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;
import zenixmc.utils.StringFormatter;
import zenixmc.utils.StringFormatter.MessageOccasion;

/**
 * Listener that handles organization events.
 * 
 * @author james
 */
public class OrganizationPlayerListener extends OrganizationListener {
	
	/**
	 * Territory Manager.
	 */
	private final TerritoryManager terManager;
	
	/**
	 * Map of users and territory parent names to keep track of which territory, the users are in.
	 */
	private final Map<ZenixUserInterface, String> territoryMessages = new HashMap<>();
	
	/**
	 * Instantiate.
	 * @param zenix
	 * 		The plugin.
	 * @param manager
	 * 		The user manager.
	 * @param orgManager
	 * 		The organizationManager.
	 * @param eventDispatcher
	 * 		The eventDispatcher to fire events.
	 */
	public OrganizationPlayerListener(ZenixMCInterface zenix, ZenixUserManager manager, OrganizationManager orgManager, TerritoryManager terManager,
			EventDispatcher eventDispatcher) {
		super(zenix, manager, orgManager, eventDispatcher);
		this.terManager = terManager;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onReDescClan(ClanReDescEvent e) {
		
		if (e.isCancelled()) {
			return;
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onReNameClan(ClanReNameEvent e) {
		
		if (e.isCancelled()) {
			return;
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onClanInvite(ClanInviteEvent e) {
		
		if (e.isCancelled()) {
			return;
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onClanJoin(ClanJoinEvent e) {
		
		if (e.isCancelled()) {
			return;
		}

	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onClanClaim(ClanClaimEvent e) {
		
		if (e.isCancelled()) {
			return;
		}
		
		Territory preT = e.getTerritory();
		
		if (preT.hasParent()) {
			Influential parent = (Influential) preT.getParent();
			System.out.println(parent);
			if (parent.getName().equals(e.getClan().getName())) {
				e.setCancelled(true);
				System.out.println("clans are equal");
			}else {
				if (!(parent.isVulnerable()) || e.getClan().isVulnerable()) {
					e.setCancelled(true);
					System.out.println("parent is either not vulnerable or claim clan is vulnerable");
				}else {
					e.setOverclaim(true);
					e.setMessage(StringFormatter.format(StringFormatter.format(zenix.getSettings().clanOverClaimedMessage(), parent, e.getClan(), preT.getCoords().getA(), preT.getCoords().getB()), MessageOccasion.CLAN, zenix));
				}
			}
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChunkLoad(ChunkLoadEvent e) {
		
		Chunk c = e.getChunk();
		
		if (terManager.isTerritory(c)) {
			return;
		}
				
		Wild w = Wild.getWild();
		
		Territory t = terManager.createTerritory(c, w);
		orgManager.claimTerritory(w, null, t);
		
		zenix.broadcastMessage(null, "Territory Id: " + t.getId() + " Parent Organization: " + t.getParent());
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onMove(PlayerMoveEvent e) {
		
		ZenixUserInterface zui = manager.getZenixUser(e.getPlayer());
		
		Location to = e.getTo();
		Chunk c = to.getChunk();
		if (terManager.isTerritory(c)) {
			Territory t = terManager.getTerritory(c);
			
			if (t.hasParent()) {
				Organization o = t.getParent();
				if (territoryMessages.get(zui) == null) {
					territoryMessages.put(zui, o.getName());
				}
				if (!(territoryMessages.get(zui).equals(o.getName()))) {
					JavaUtil.replaceElementInMap(territoryMessages, zui, new SimpleEntry(zui, t.getParent().getName()));
					zui.sendMessage(StringFormatter.format(zenix.getSettings().clanEntryTerritoryMessage(), o.getName(), JavaUtil.arrayToString(o.getDescription()), MessageOccasion.CLAN, zenix));
				}
			}
		}
	}
	
}
