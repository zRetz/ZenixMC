/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.io.File;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Logger;
import org.bukkit.configuration.MemoryConfiguration;
import zenixmc.utils.ExceptionUtils;
import zenixmc.utils.exceptions.NotEvenException;


/**
 * Persistence of data on disk.
 * @author james
 */
public abstract class Repository implements RepositoryInterface {

    /**
     * Logger to debug/log.
     */
    protected final Logger logger;
    
    /**
     * Directory to store in.
     */
    protected File directory;
    
    /**
     * Default configuration of data.
     */
    protected class DynamicDefaultConfiguration extends MemoryConfiguration {
        
        /**
         * Create a default configuration.
         * @param keys
         *      Keys to locate data.
         * @param values
         *      Data values to store.
         * @throws NotEvenException 
         */
        DynamicDefaultConfiguration(TreeSet<String> keys, TreeSet<Object> values) throws NotEvenException {
            if (keys.size() != keys.size()) {
                throw ExceptionUtils.notEvenException("keys and values need to be even");
            }
            for (String s : keys) {
                for (Object o : values) {
                    this.addDefault(s, o);
                }
            }
        }
        
        /**
         * Create a default configuration.
         * @param defaults
         *      The map of data to store.
         */
        DynamicDefaultConfiguration(Map<String, Object> defaults) {
            this.addDefaults(defaults);
        }
    }

    /**
     * Instantiate the repository.
     * @param logger
     *      The logger to log to.
     * @param directory
     *      The directory to store data in.
     */
    public Repository(Logger logger, File directory) {
        this.logger = logger;
        this.directory = directory;
    }
    
}
