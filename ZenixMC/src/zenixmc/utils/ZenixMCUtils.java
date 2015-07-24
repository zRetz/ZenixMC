/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;

import zenixmc.text.Text;

/**
 * Utilities class for ZenixMC objects.
 * @author james
 * @author Some Guy On Bukkit Forums
 */
public class ZenixMCUtils {
    
    private static final Logger log = Logger.getLogger("ZenixMC");
    
    public static Text instantiateText(String title, String[] line) {
        
        Text result = new Text(System.currentTimeMillis(), log);
        result.addLine(title, new String[][]{line});
        
        return result;
    }
}
