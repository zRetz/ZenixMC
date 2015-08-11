package zenixmc.organization.clans.defaults;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;

import zenixmc.ZenixMC;
import zenixmc.ZenixMCInterface;
import zenixmc.organization.OrganizationManager;
import zenixmc.organization.clans.Clan;
import zenixmc.organization.clans.TerritoryManager;
import zenixmc.user.ZenixUserManager;
import zenixmc.utils.JavaUtil;

public class Wild extends Clan {
	
	private static Wild singleton;
	
	public static void setUp() {
		ZenixMCInterface zenix = ZenixMC.instance;
		OrganizationManager manager = null;
		Field f = null;
		try {
			f = zenix.getClass().getDeclaredField("orgManager");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		f.setAccessible(true);
		try {
			manager = (OrganizationManager) f.get(zenix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (manager.clanNameUsed("Wild")) {
			singleton = (Wild) manager.getClan("Wild", false);
		}else {
			singleton = (Wild) manager.createClan(null, "Wild");
		}
	}
	
	public static Wild getWild() {
		return singleton;	
	}
	
	/**
	 * SerialVersionUID. 
	 */
	private static final long serialVersionUID = -9121177294826119402L;

	public Wild(ZenixMCInterface zenix, ZenixUserManager manager, TerritoryManager terManager) {
		super(zenix, manager, terManager, null, "Wild");
		this.desc = new String[]{"Programmer Joke: Why do Java programmers wear glasses? Because they can't C#.", " lel."};
		this.invite = false;
	}
	
	@Override
	public String[] about() {
		
		ChatColor p = zenix.getSettings().getClanColor();
		ChatColor s = zenix.getSettings().getMatchingClanColor();
		
		String[] about = new String[9];
		
		String l1 = s + "=====" + p + "Clan About" + s + "======";
		String l2 = s + "+" + p + "Name: " + s + getName();
		String l3 = s + "+" + p + "Influence: " + s + calcTotalInfluence() + p + "/" + s + calcTotalMaxInfluence();
		String l4 = s + "+" + p + "Territory: " + s + territoryAmount();
		String l5 = s + "+" + p + "Description: " + s + JavaUtil.arrayToString(getDescription());
		String l6 = s + "+" + p + "Need Invitation: " + s + needInvite();
		String l7 = s + "+" + p + "Online Members: " + s + getMembers().onlineMembers();
		String l8 = s + "+" + p + "Offline Members: " + s + getMembers().offlineMembers();
		String l9 = s + "+" + p + "Banned Users: " + s + bannedUsers();
		
		about[0] = l1;
		about[1] = l2;
		about[2] = l3;
		about[3] = l4;
		about[4] = l5;
		about[5] = l6;
		about[6] = l7;
		about[7] = l8;
		about[8] = l9;
		
		return about;
	}
	
	@Override
	public int calcTotalInfluence() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public int calcTotalMaxInfluence() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public boolean isVulnerable() {
		return true;
	}
}
