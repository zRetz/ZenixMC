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
    
    Color getNotificationColor();
    
    List<Material> getBlockBlackList();
    
    boolean allowSilentJoinQuit();
    
    String getJoinMessage();
    
    String getQuitMessage();
}
