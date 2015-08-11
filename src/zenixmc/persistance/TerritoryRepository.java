package zenixmc.persistance;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Chunk;

import zenixmc.ZenixMCInterface;
import zenixmc.io.SeDe;
import zenixmc.organization.Organization;
import zenixmc.organization.clans.Territory;
import zenixmc.utils.zenixjava.IntPair;

public class TerritoryRepository extends Repository implements TerritoryRepositoryInterface {

	/**
	 * The plugin.
	 */
	private ZenixMCInterface zenix;
	
	public TerritoryRepository(Logger logger, File directory, ZenixMCInterface zenix) {
		super(logger, directory);
		this.zenix = zenix;
	}

	protected File getTerritoryFile(String id) {
		return new File(this.directory, id + ".dat");
	}
	
	@Override
	public Territory getTerritory(String id, Chunk c, Organization org) {
		
		System.out.println(id);
		final File f = getTerritoryFile(id);
		
		Territory result = null;
		
		if (!(f.exists()) && org != null && c != null) {
			result = new Territory(id, c, org, zenix);
			result.handleChunk(zenix.getWorld(result.getWorld()));
			save(result);
			return result;
		}
		
		result = SeDe.deserialize(f, Territory.class);
		
		result.setZenix(zenix);
		result.handleChunk(zenix.getWorld(result.getWorld()));
		
		return result;
	}
	
	@Override
	public Territory getTerritory(Chunk c) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}
	
	@Override
	public void save(Object object) {
		if (object instanceof Territory) {
			save((Territory) object);
		}
	}
	
	@Override
	public void save(Territory territory) {
		
		final File f = getTerritoryFile(territory.getId());
		
		SeDe.serialize(territory, f);
	}

	@Override
	public void delete(Object object) {
		if (object instanceof Territory) {
			delete((Territory) object);
		}
	}
	
	@Override
	public void delete(Territory territory) {
		
		final File f = getTerritoryFile(territory.getId());
		
		if (!(f.exists())) {
			return;
		}
		
		f.delete();
		
		logger.log(Level.INFO, "Territory: " + territory.toString() + " has been deleted.");
	}

	@Override
	public Set<String> fileNames() {
		
		Set<String> result = new HashSet<>();
		
		File[] files = files();
		
		for (File f : files) {
			result.add(f.getName().substring(0, f.getName().length()-4));
		}
		
		return result;
	}

	@Override
	public File[] files() {
		return this.directory.listFiles(new FileFilter() {

			@Override
			public boolean accept(File path) {
				return path.getName().endsWith(".dat");
			}
			
		});
	}

	@Override
	public boolean isTerritory(String id) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}
	
	@Override
	public boolean isTerritory(Chunk c) {
		throw new UnsupportedOperationException("This is not a cache class.");
	}
}
