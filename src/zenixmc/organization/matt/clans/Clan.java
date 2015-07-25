package zenixmc.organization.matt.clans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import zenixmc.organization.Organization;
import zenixmc.user.ZenixUserInterface;

public class Clan implements Organization {
	public static List<String> Clans = new ArrayList<>();
	public static HashMap<ZenixUserInterface, String> playerClan = new HashMap<>();
	public static HashMap<ZenixUserInterface, String> clanInvites;

	public ZenixUserInterface zui;
	private String name;
	private String[] desc;

	public Clan(ZenixUserInterface zui, String name) {
		this.zui = zui;
		this.name = name;
		
		if (nameIsUsed(name, zui)) {
			return;
		}
		
		createClan(zui, name);
	}

	// Organization methods

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getDescription() {
		return desc;
	}
	
	public void createClan(ZenixUserInterface player, String s) {
		Clans.add(name);
		playerClan.put(player, name);
	}

	// Methods
	
	public static boolean nameIsUsed(String name, ZenixUserInterface player) {
		if (Clans.contains(name)) {
			player.sendMessage(ChatColor.RED + "The name " + name + " has already been used!");
			return true;
		}
		return false;
	}

	public static void inviteToClan(String name, ZenixUserInterface player,
			ZenixUserInterface target) {
		if (player != null && target != null) {
			clanInvites.put(target, name);

			player.sendMessage("You have invited " + target.getName() + " to "
					+ Clan.getClan(player) + "!");
			target.sendMessage("You have been invited to "
					+ Clan.getClan(player) + " by " + player.getName() + "!");
		}
	}

	public static void viewInvites(ZenixUserInterface player) {
		List<String> temp = new ArrayList<>();
		for (ZenixUserInterface z : clanInvites.keySet()) {
			if (z == player) {
				temp.add(clanInvites.get(z));
			}
		}
		clanInvites.remove(player);
		for (int i = 0; temp.size() > i; i++) {
			player.sendMessage(temp.remove(0).toString());

			if (temp.size() == 0) {
				break;
			}
		}
	}

	public static List<ZenixUserInterface> getMembers(Player player, String name) {
		List<ZenixUserInterface> l = new ArrayList<>();
		for (ZenixUserInterface z : playerClan.keySet()) {
			if (playerClan.get(z) == name) {
				l.add(z);
				return l;
			}
		}
		return null;
	}

	public static String getClan(ZenixUserInterface player) {
		if (playerClan.get(player) != null) {
			return playerClan.get(player);
		}
		return null;
	}
}
