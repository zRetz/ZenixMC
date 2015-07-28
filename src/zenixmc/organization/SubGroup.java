package zenixmc.organization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zenixmc.organization.Message.Type;
import zenixmc.user.ZenixUserInterface;

public class SubGroup {

	public static HashMap<String, String> subGroups = new HashMap<String, String>();
	public static HashMap<ZenixUserInterface, String> subGroup = new HashMap<ZenixUserInterface, String>();

	public String clanname;
	public String subname;

	public SubGroup(String clanname, String subname) {
		this.clanname = clanname;
		this.subname = subname;

		create(clanname, subname);
	}

	public void create(String clanname, String subname) {
		subGroups.put(clanname, subname);
	}

	public void remove(String clanname, String subname) {
		subGroups.remove(subGroups.get(subname));
	}

	public void add(ZenixUserInterface player, ZenixUserInterface target,
			String subname) {
		String name = Clan.getClan(player);
		if (Clan.getClan(target) != name) {
			new Message(player, "This player is not in the clan!", Type.CLAN);
			return;
		} else if (subGroup.get(target) == subname) {
			new Message(player, "Player is already in that subgroup!", Type.CLAN);
			return;
		} else {
			subGroup.put(target, subname);
		}
	}
	
	public void remove(ZenixUserInterface player, ZenixUserInterface target, String subname) {
		String name = Clan.getClan(player);
		if (Clan.getClan(target) != name) {
			new Message(player, "This player is not in the clan!", Type.CLAN);
			return;
		} else if (getSubGroup(player) == null) {
			new Message(player, "This player is not apart of a subgroup!", Type.CLAN);
			return;
		}
		subGroup.remove(target);
	}
	
	public void list(ZenixUserInterface player) {
		String name = Clan.getClan(player);
		List<String> groups = new ArrayList<>();
		groups.add(subGroups.get(name));
		
		for(String s : groups) {
			new Message(player, s, Type.CLAN);
		}
		
	}
	
	public static String getSubGroup(ZenixUserInterface player) {
		if (subGroup.get(player) != null) {
			return subGroup.get(player);
		}
		return null;
	}

}
