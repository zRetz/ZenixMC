/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import zenixmc.ZenixMC;
import zenixmc.text.Text;
import zenixmc.user.ZenixUserInterface;
import zenixmc.user.objects.Home;
import zenixmc.user.objects.Warning;
import zenixmc.utils.ConversionUtils;
import zenixmc.utils.exceptions.NotEvenException;
import zenixmc.utils.io.SerialisableList;

/**
 * Persistence of users on disk.
 * @author james
 */
public class ZenixUserRepository extends Repository implements ZenixUserRepositoryInterface {
    
    /**
     * The plugin.
     */
    private final ZenixMC zenix;
    
    private class DefaultConfiguration {
        
        private final String[] defKeys = {"name", "uuid", "muted", "frozen", "godmode", "vanished", "warnings", "homes", "mails", "lastOnlineActivity", "lastActivity"};
        private final Object[] defValues = {"", "", false, false, false, false, new Warning().serialise(), new SerialisableList(Arrays.asList(new Home("spawn", zenix.getSpawnLocation(zenix.getWorld())))).serialise(), new SerialisableList(Arrays.asList(new Text("Welcome", "Zenix greets you!"))).serialise(), 0L, 0L};
        
        DynamicDefaultConfiguration def;
        
        DefaultConfiguration() {
            try {
                def = new DynamicDefaultConfiguration(ConversionUtils.arraysToTreeMap(defKeys, defValues));
            }catch (final NotEvenException e) {
                ZenixUserRepository.super.logger.log(Level.SEVERE, "Failed to instantiate DefaultConfiguration in ZenixUserRepository.");
            }
        }
    }
    
    /**
     * Instantiate.
     * @param logger
     *      The logger to log to.
     * @param directory 
     *      The directory to store data in.
     * @param zenix
     *      The plugin.
     */
    public ZenixUserRepository(Logger logger, File directory, ZenixMC zenix) {
        super(logger, directory);
        this.zenix = zenix;
    }
    
    /**
     * Returns the path to the file to store the specified user in.
     *
     * @param player
     *            The player to find the file for.
     * @return The file of the specified player.
     */
    protected File getZenixUserFile(Player player) {
        return new File(this.directory, player.getUniqueId() + ".yml");
    }
    
    @Override
    public ZenixUserInterface getZenixUser(Player player) {
        final File f = getZenixUserFile(player);
    }

    @Override
    public ZenixUserInterface getZenixUser(Object key) {
        throw new UnsupportedOperationException("This is not a cache class.");
    }

    @Override
    public ZenixUserInterface getZenixUser(String name) {
        throw new UnsupportedOperationException("This is not a cache class.");
    }

    @Override
    public ZenixUserInterface getZenixUser(UUID uuid) {
        throw new UnsupportedOperationException("This is not a cache class.");
    }

    @Override
    public void save(ZenixUserInterface zenixUser) {
        
    }

    @Override
    public void open() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Object object) {
        if (object instanceof ZenixUserInterface) {
            save((ZenixUserInterface) object);
        }
    }
    
}
