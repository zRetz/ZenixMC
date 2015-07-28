/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.io.File;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
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
    
    @Override
    public void open(String openMessage) {
    	if (!(this.directory.exists())) {
    		this.directory.mkdirs();
    	}
    }
    
    @Override
    public void close(String closeMessage) {
    	logger.log(Level.INFO, closeMessage);
        if (this.directory.exists()) {
        	for (File f : this.directory.listFiles()) {
        		if (f.exists()) {
        			f.delete();
        		}
        		this.directory.delete();
        	}
        }
    }
}
