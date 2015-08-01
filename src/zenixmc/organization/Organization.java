/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author james
 */
public interface Organization {
	Map<Organization, OrganizationPlayerInterface> organizations = new HashMap<>();
	Map<OrganizationPlayerInterface, Organization> invites = new HashMap<>();
    
	String getName();
	
	String[] getDescription();
	
	OrganizationPlayerInterface getLeader();
	
	List<OrganizationPlayerInterface> getPlayers();
	
	void sendInvite(OrganizationPlayerInterface player);
	
	void disband();
	
}
