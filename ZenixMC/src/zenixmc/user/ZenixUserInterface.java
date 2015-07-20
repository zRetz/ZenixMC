/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.user;

import org.bukkit.entity.Player;
import zenixmc.bending.BendingPlayerInterface;

/**
 *
 * @author james
 */
public interface ZenixUserInterface {
    
    /**
     * @return The bukkit representation of a player.
     */
    Player getPlayer();
    
    /**
     * @return The bendingPlayer of the user.
     */
    BendingPlayerInterface getBendingPlayer();
}
