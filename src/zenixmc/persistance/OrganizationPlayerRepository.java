package zenixmc.persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

import zenixmc.organization.DefaultOrganizationPlayerData;
import zenixmc.organization.OrganizationPlayer;
import zenixmc.organization.OrganizationPlayerData;
import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.ZenixUserInterface;

public class OrganizationPlayerRepository extends Repository implements OrganizationPlayerRepositoryInterface {

	/**
	 * The user repository to fetch user data.
	 */
	private ZenixUserRepositoryInterface zenixUserRepository;
	
	/**
	 * The organization repository to fetch organization data.
	 */
	private OrganizationRepositoryInterface organizationRepository;
	
	/**
	 * Instantiate.
	 * @param logger
	 * 		The logger to debug/log.
	 * @param directory
	 * 		The directory to store in.
	 */
	public OrganizationPlayerRepository(Logger logger, File directory) {
		super(logger, directory);
	}
	
	/**
     * Returns the path to the file to store the specified organizationPlayer in.
     *
     * @param zui
     *            The user to find the file for.
     * @return The file of the organizationPlayer.
     */
    protected File getOrganizationPlayerFile(ZenixUserInterface zui) {
        return new File(this.directory, zui.getUniqueId() + ".json");
    }

	@Override
	public void open(String openMessage) {
		super.open(openMessage);
	}

	@Override
	public void close(String closeMessage) {
		super.close(closeMessage);
	}

	@Override
	public void save(Object object) {
		if (object instanceof OrganizationPlayerInterface) {
			save((OrganizationPlayerInterface) object);
		}
	}

	@Override
	public OrganizationPlayerInterface getOrganizationPlayer(ZenixUserInterface zui) {
		final Gson g = new Gson();	
        final File f = getOrganizationPlayerFile(zui);
        
        OrganizationPlayerInterface organizationPlayer = new OrganizationPlayer(logger);
        
        if (!(f.exists())) {
        	organizationPlayer.setZenixUser(zui);
        	organizationPlayer.setData(new DefaultOrganizationPlayerData());
        	save(organizationPlayer);
        	return organizationPlayer;
        }
        
        try{
	        BufferedReader reader = new BufferedReader(new FileReader(f.getAbsoluteFile()));
	        
	        organizationPlayer.setData(g.fromJson(reader, OrganizationPlayerData.class));
	        reader.close();
        }catch (IOException e) {
        	logger.log(Level.WARNING, "Organization Player Data is failing to load.");
        }
        
        logger.log(Level.INFO, "Organization Player Data has been loaded.");
        
        return organizationPlayer;
	}

	@Override
	public void setZenixUserRepository(ZenixUserRepositoryInterface zenixUserRepository) {
		this.zenixUserRepository = zenixUserRepository;
	}
	
	@Override
	public void setOrganizationRepository(OrganizationRepositoryInterface organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	@Override
	public void save(OrganizationPlayerInterface organizationPlayer) {
		final ZenixUserInterface zui = organizationPlayer.getZenixUser();
		
        final File f = getOrganizationPlayerFile(zui);
        final Gson g = new Gson();
        
        try {
			FileWriter writer = new FileWriter(f.getAbsoluteFile());
			writer.write(g.toJson(organizationPlayer.getData()));
			writer.close();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Organization Player Data is failing to save.");
		}
        
        logger.log(Level.INFO, zui.getName() + "'s Organization Player Data has been saved.");
	}

}
