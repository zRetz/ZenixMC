package zenixmc.persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import com.google.gson.Gson;

import zenixmc.bending.BendingPlayerInterface;
import zenixmc.user.DefaultUserData;
import zenixmc.user.ZenixUser;
import zenixmc.user.ZenixUserData;
import zenixmc.user.ZenixUserInterface;

public class BendingPlayerRepository extends Repository implements BendingPlayerRepositoryInterface {

	private ZenixUserRepository zenixUserRepository;
	
	public BendingPlayerRepository(Logger logger, File directory) {
		super(logger, directory);
		
	}
	
	protected File getBendingPlayerFile() {
		
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Object object) {
		if (object instanceof BendingPlayerInterface) {
			save((BendingPlayerInterface) object);
		}
	}

	@Override
	public BendingPlayerInterface getBendingPlayer(Player player) {
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
	public void save(BendingPlayerInterface bendingPlayer) {
		
	}

}
