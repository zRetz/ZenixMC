package zenixmc.organization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;

import zenixmc.organization.Message.Type;
import zenixmc.user.ZenixUserInterface;

public class Clan implements Organization {
	public static List<String> Clans = new ArrayList<>();
	public static HashMap<ZenixUserInterface, String> playerClan = new HashMap<>();
	public static HashMap<ZenixUserInterface, String> clanInvites;
	public static HashMap<Location, String> clanHomes;

	public ZenixUserInterface zui;
	private String name;
	private String[] desc;

	public Clan(ZenixUserInterface zui, String name) {
		this.zui = zui;
		this.name = name;

		if (used(name)) {
			return;
		}

		create(zui, name);
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

	@Override
	public ZenixUserInterface[] getMembers(String name) {
		if (!Clans.contains(name)) {
			return null;
		} else if (name == null) {
			return null;
		}
		return members(name);
	}

	public void create(ZenixUserInterface player, String name) {
		Clans.add(name);
		playerClan.put(player, name);
	}

	public void disband(ZenixUserInterface player) {
		String name = getClan(player);
		for (ZenixUserInterface z : members(name)) {
			playerClan.remove(z);
		}
		Clans.remove(name);
		new Message(player, "You have disbanded the Clan: " + name + "!",
				Type.CLAN);
	}

	// Methods

	public static void invite(ZenixUserInterface player,
			ZenixUserInterface target) {
		String name = getClan(player);
		if (target != null) {
			if (clanInvites.get(target).contains(name)) {
				for (ZenixUserInterface z : members(name)) {
					new Message(z, "The invite of " + target
							+ " has been revoked!", Type.CLAN);
					clanInvites.remove(target);
					return;
				}
			} else if (playerClan.get(target) == name) {
				new Message(player, target
						+ " is already apart of the Clan!", Type.CLAN);
				return;
			} else {
				clanInvites.put(target, name);
				for (ZenixUserInterface z : members(name)) {
					new Message(z, target
							+ " has been invited to the Clan!", Type.CLAN);
					return;
				}
			}
		}
	}

	public static void join(ZenixUserInterface player) {
		String name = getClan(player);
		if (playerClan.containsKey(player)) {
			new Message(player, "You are already in a Clan!", Type.CLAN);
			return;
		} else {
			playerClan.put(player, name);
			new Message(player, "You have joined " + name + "!", Type.CLAN);
			return;
		}
	}

	public static void leave(ZenixUserInterface player) {
		String name = getClan(player);
		if (playerClan.get(player) == null) {
			new Message(player, "You are not in a Clan!", Type.CLAN);
			return;
		} else {
			for (ZenixUserInterface z : members(name)) {
				new Message(z, player.getName() + " has left the Clan!",
						Type.CLAN);
			}
			new Message(player, "You have left " + name, Type.CLAN);
			playerClan.remove(player);
			return;
		}
	}

	public static void kick(ZenixUserInterface player, ZenixUserInterface target) {
		String name = getClan(target);
		if (!playerClan.containsKey(target) || playerClan.get(target) != name) {
			new Message(player, target.getName()
					+ " is not apart of your Clan!", Type.CLAN);
			return;
		} else {
			playerClan.remove(target);
			for (ZenixUserInterface z : members(name)) {
				new Message(z, target.getName() + " has been kicked by "
						+ player.getName() + "!", Type.CLAN);
				return;
			}
		}
	}

	public static void sethome(ZenixUserInterface player) {
		String name = getClan(player);
		clanHomes.put(player.getLocation(), name);
		for (ZenixUserInterface z : members(name)) {
			new Message(z, player.getName()
					+ " has set a new home for your Clan!", Type.CLAN);
		}

	}

	public static void gohome(ZenixUserInterface player) {
		// TODO: Teleport player to home

	}

	// Getting methods

	public static void invites(ZenixUserInterface player) {
		List<String> temp = new ArrayList<>();
		for (ZenixUserInterface z : clanInvites.keySet()) {
			if (z == player) {
				temp.add(clanInvites.get(z));
			}
		}
		clanInvites.remove(player);
		for (int i = 0; temp.size() > i; i++) {
			new Message(player, temp.remove(0).toString(), Type.CLAN);

			if (temp.size() == 0) {
				break;
			}
		}
	}

	public static ZenixUserInterface[] members(String name) {
		for (ZenixUserInterface z : playerClan.keySet()) {
			if (playerClan.get(z) == name) {
				return new ZenixUserInterface[] { z };
			}
		}
		return null;
	}

	public static boolean used(String name) {
		if (Clans.contains(name)) {
			return true;
		}
		return false;
	}

	public static String getClan(ZenixUserInterface player) {
		if (playerClan.get(player) != null) {
			return playerClan.get(player);
		}
		return null;
	}

}
