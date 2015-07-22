/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.organization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import zenixmc.organization.matt.clans.Clan;
import zenixmc.user.ZenixUserInterface;


/**
 *
 * @author james
 */
public interface Organization {
    
	public static List<Clan> Clans = new ArrayList<>();
	
	public static HashMap<ZenixUserInterface, Clan> playerClan = new HashMap<>();
	public static HashMap<ZenixUserInterface, Clan> clanInvites = new HashMap<>();
	
	public String getName();
	
	public String[] getDescription();
	
	public Player[] getMembers();

}