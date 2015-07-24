package zenixmc;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;

import zenixmc.persistance.Repository;

/**
 * Class handles integer identifiers.
 * @author james
 */
public class IDManager extends Repository {
	
	/**
	 * The file integer identifier.
	 */
	private int fileId;
	
	/**
	 * The file to store the ids inside.
	 */
	private File f;
	
	/**
	 * The yaml configuration api on the file.
	 */
	private YamlConfiguration c;
	
	public IDManager(Logger logger, File directory) {
		super(logger, directory);
	}

	@Override
	public void open() {
		f = new File(this.directory, "idManager.yml");
		
		if (!(f.exists())) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				logger.log(Level.SEVERE, "IDManager failed to open.");
			}
		}
		
		c = YamlConfiguration.loadConfiguration(f);
		c.addDefault("id.fileId", 0);
		
		this.fileId = c.getInt("id.fileId");
		
		try {
			c.save(f);
		} catch (IOException e) {
			logger.log(Level.WARNING, "IDManager can't save.");
		}
	}

	@Override
	public void close() {
		
		if (f == null) {
			return;
		}
		
		c.set("id.fileId", fileId);
		
		try {
			c.save(f);
		} catch (IOException e) {
			logger.log(Level.WARNING, "IDManager can't save.");
		}
	}

	@Override
	public void save(Object object) {
		throw new UnsupportedOperationException("IDManager can't save other objects.");
	}
	
	public int assignFileID() {
		
		setFileId(this.fileId + 1);
		
		int result = this.fileId;
		
		return result;
	}
	
	private void setFileId(int newid) {
		this.fileId = newid;
		c.set("id.fileId", newid);
	}
	
}
