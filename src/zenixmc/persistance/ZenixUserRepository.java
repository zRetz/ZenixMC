/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import com.google.gson.Gson;

import zenixmc.ZenixMCInterface;
import zenixmc.event.EventDispatcher;
import zenixmc.user.DefaultUserData;
import zenixmc.user.ZenixUser;
import zenixmc.user.ZenixUserData;
import zenixmc.user.ZenixUserInterface;

/**
 * Persistence of users on disk.
 * @author james
 */
public class ZenixUserRepository extends Repository implements ZenixUserRepositoryInterface {
    
    /**
     * The plugin.
     */
    private final ZenixMCInterface zenix;
    
    /**
     * The event dispatcher to fire events.
     */
    private final EventDispatcher eventDispatcher;
    
    /**
     * Repository to push/fetch bendingPlayer data.
     */
    private BendingPlayerRepositoryInterface bendingRepository;
    
    /**
     * Repository to push/fetch text data.
     */
    private TextRepositoryInterface textRepository;
    
    /**
     * Instantiate.
     * @param logger
     *      The logger to log to.
     * @param directory 
     *      The directory to store data in.
     * @param zenix
     *      The plugin.
     */
    public ZenixUserRepository(Logger logger, File directory, ZenixMCInterface zenix, EventDispatcher eventDispatcher) {
        super(logger, directory);
        this.zenix = zenix;
        this.eventDispatcher = eventDispatcher;
    }
    
    /**
     * Returns the path to the file to store the specified user in.
     *
     * @param player
     *            The player to find the file for.
     * @return The file of the specified player.
     */
    protected File getZenixUserFile(Player player) {
        return new File(this.directory, player.getUniqueId() + ".json");
    }
    
    @Override
    public ZenixUserInterface getZenixUser(Player player) {
    	final Gson g = new Gson();	
        final File f = getZenixUserFile(player);
        
        final ZenixUserInterface zui = new ZenixUser(player, zenix);
        
        if (!(f.exists())) {
        	zui.fromUserData(new DefaultUserData(zui, eventDispatcher));
        	save(zui);
        	return zui;
        }
        
        ZenixUserData zuiData = null; 
        
        try{
	        BufferedReader reader = new BufferedReader(new FileReader(f.getAbsoluteFile()));
	        
	        zuiData = g.fromJson(reader, ZenixUserData.class);
	        reader.close();
        }catch (IOException e) {
        	logger.log(Level.WARNING, "Zenix User Data is failing to load.");
        }
        
        if (zuiData == null) {
        	zuiData = new DefaultUserData(zui, eventDispatcher);
        	logger.log(Level.WARNING, "Zenix User Data failed to load.");
        }
        
        zui.fromUserData(zuiData);
        
        logger.log(Level.INFO, "Zenix User Data has been loaded.");
        
        return zui;
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
        final Player player = zenixUser.getPlayer();
        
        final File f = getZenixUserFile(player);
        final Gson g = new Gson();
        
        try {
			FileWriter writer = new FileWriter(f.getAbsoluteFile());
			writer.write(g.toJson(zenixUser.toUserData()));
			writer.close();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Zenix User Data is failing to save.");
		}
        
        logger.log(Level.INFO, zenixUser.getName() + " has been saved.");
    }

    @Override
    public void open() {
    	if (!(this.directory.exists())) {
    		this.directory.mkdir();
    	}
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("This is a local repository."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Object object) {
        if (object instanceof ZenixUserInterface) {
            save((ZenixUserInterface) object);
        }
    }
    
    public void setBendingRepository(BendingPlayerRepositoryInterface bendingRepository) {
    	this.bendingRepository = bendingRepository;
    }
    
    public void setTextRepository(TextRepositoryInterface textRepository) {
    	this.textRepository = textRepository;
    }
    
}
