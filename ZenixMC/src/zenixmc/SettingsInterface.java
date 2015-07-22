/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc;

import java.util.List;
import org.bukkit.Color;
import org.bukkit.Material;

/**
 *
 * @author james
 */
public interface SettingsInterface {
    
    /**
     * @return The colour for errors. (default: red)
     */
    Color getErrorColor();
    
    /**
     * @return The colour for notifications. (default: green)
     */
    Color getNotificationColor();
    
    /**
     * @return The color for sorted situations. (default: gold)
     */
    Color getSortedColor();
    
    /**
     * @return A list of banned blocks.
     */
    List<Material> getBlockBlackList();
    
    /**
     * @return <code>true</code> If silent join/quit is allowed.
     */
    boolean allowSilentJoinQuit();
    
    /**
     * @return The message displayed when joining.
     */
    String getJoinMessage();
    
    /**
     * @return The message displayed when leaving.
     */
    String getQuitMessage();
}
