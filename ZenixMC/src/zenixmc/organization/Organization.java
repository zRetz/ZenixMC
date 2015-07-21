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

/**
 *
 * @author james
 */
public interface Organization {
	public List<Clan> Clans = new ArrayList<>();
	public HashMap<Player, Clan> pClan = new HashMap<>();

	public String getName();
	public String[] getDesc();

}