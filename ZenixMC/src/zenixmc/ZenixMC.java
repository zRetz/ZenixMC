/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author james
 */
public class ZenixMC extends JavaPlugin {

    @Override
    public void onEnable() {
        
        getLogger().log(Level.INFO, "Enabling Zenix. Powered by Zenix.");
    }

    @Override
    public void onDisable() {
        
        getLogger().log(Level.INFO, "Disabling Zenix. Powered by Zenix.");
    }
    
}
