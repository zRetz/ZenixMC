/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.organization;

import zenixmc.user.ZenixUserInterface;

/**
 *
 * @author james
 */
public interface Organization {
    
	public String getName();
	
	public String[] getDescription();
	
	public ZenixUserInterface[] getMembers(String name);
}
