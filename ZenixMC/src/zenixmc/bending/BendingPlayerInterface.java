/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.bending;

import zenixmc.user.ZenixUserInterface;

/**
 * A bendingPlayer internally used by this plugin.
 * @author james
 */
public interface BendingPlayerInterface {
    
    /**
     * @return The user of the bendingPlayer.
     */
    ZenixUserInterface getUser();
    
    
}
