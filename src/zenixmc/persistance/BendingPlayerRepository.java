package zenixmc.persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

import zenixmc.bending.BendingPlayer;
import zenixmc.bending.BendingPlayerInterface;
import zenixmc.user.ZenixUserInterface;

/**
 * Persistence of bendingPlayer on disk.
 * @author james
 */
public class BendingPlayerRepository extends Repository implements BendingPlayerRepositoryInterface {
	
	/**
	 * The user repository to fetch user data.
	 */
	private ZenixUserRepositoryInterface zenixUserRepository;
	
	/**
	 * Instantiate.
	 * @param logger
	 *		The logger to debug/log.
	 * @param directory
	 * 		The directory to store in.
	 */
	public BendingPlayerRepository(Logger logger, File directory) {
		super(logger, directory);
	}
	
	/**
     * Returns the path to the file to store the specified user in.
     *
     * @param player
     *            The player to find the file for.
     * @return The file of the specified player.
     */
    protected File getBendingPlayerFile(ZenixUserInterface zui) {
        return new File(this.directory, zui.getUniqueId() + ".json");
    }

	@Override
	public void open() {
		logger.log(Level.INFO, "Opening repository.");
    	if (!(this.directory.exists())) {
    		this.directory.mkdir();
    	}
	}

	@Override
	public void close() {
		logger.log(Level.INFO, "Closing repository.");
        if (this.directory.exists()) {
        	for (File f : this.directory.listFiles()) {
        		if (f.exists()) {
        			f.delete();
        		}
        		this.directory.delete();
        	}
        }
	}

	@Override
	public void save(Object object) {
		if (object instanceof BendingPlayerInterface) {
			save((BendingPlayerInterface) object);
		}
	}

	@Override
	public BendingPlayerInterface getBendingPlayer(ZenixUserInterface zui) {
		final Gson g = new Gson();	
        final File f = getBendingPlayerFile(zui);
        
        BendingPlayerInterface bendingPlayer = new BendingPlayer();
        
        if (!(f.exists())) {
        	bendingPlayer.setZenixUser(zui);
        	save(bendingPlayer);
        	return bendingPlayer;
        }
        
        try{
	        BufferedReader reader = new BufferedReader(new FileReader(f.getAbsoluteFile()));
	        
	        bendingPlayer = g.fromJson(reader, BendingPlayer.class);
	        reader.close();
        }catch (IOException e) {
        	logger.log(Level.WARNING, "Bending Player Data is failing to load.");
        }
        
        logger.log(Level.INFO, "Bending Player Data has been loaded.");
        
        return bendingPlayer;
	}
	
	@Override
	public void setZenixUserRepository(ZenixUserRepositoryInterface zenixUserRepository) {
		this.zenixUserRepository = zenixUserRepository;
	}

	@Override
	public void save(BendingPlayerInterface bendingPlayer) {
		final ZenixUserInterface zui = bendingPlayer.getZenixUser();
		
        final File f = getBendingPlayerFile(zui);
        final Gson g = new Gson();
        
        try {
			FileWriter writer = new FileWriter(f.getAbsoluteFile());
			writer.write(g.toJson(bendingPlayer));
			writer.close();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Bending Player Data is failing to save.");
		}
        
        logger.log(Level.INFO, zui.getName() + " has been saved.");
	}

}
