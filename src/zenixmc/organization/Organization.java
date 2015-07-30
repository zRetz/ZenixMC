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
	public Map<OrganizationPlayerInterface, Organization> o = new HashMap<>();
    
	public String getName();
	
	public String[] getDescription();
	
	public OrganizationPlayerInterface getLeader();
	
	public List<OrganizationPlayerInterface> getPlayers();
	
}
