package zenixmc.persistance;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Chunk;

import zenixmc.ZenixMCInterface;
import zenixmc.organization.Organization;
import zenixmc.organization.clans.Territory;

public class CachedTerritoryRepository implements TerritoryRepositoryInterface {

	/**
     * Logger to debug/log.
     */
    private final Logger log;
    
    /**
     * The cached data for territories.
     */
    private final Map<String, Territory> territory = new HashMap<>();
    
    /**
     * The repository to fetch users from that are not in the cache.
     */
    private final TerritoryRepositoryInterface parentRepository;
    
    /**
     * The plugin.
     */
    private final ZenixMCInterface zenix;
    
	public CachedTerritoryRepository(Logger log, TerritoryRepositoryInterface parentRepository,
			ZenixMCInterface zenix) {
		super();
		this.log = log;
		this.parentRepository = parentRepository;
		this.zenix = zenix;
	}

	@Override
	public void open(String openMessage) {
		parentRepository.open(openMessage);
		for (String s : fileNames()) {
        	getTerritory(s, null, null);
        }
	}

	@Override
	public void close(String closeMessage) {
		territory.clear();
		parentRepository.close(closeMessage);
	}

	@Override
	public void save(Object object) {
		parentRepository.save(object);
	}

	@Override
	public void delete(Object object) {
		parentRepository.delete(object);
	}

	@Override
	public Set<String> fileNames() {
		return parentRepository.fileNames();
	}

	@Override
	public File[] files() {
		return parentRepository.files();
	}

	@Override
	public Territory getTerritory(String id, Chunk c, Organization org) {
		
		Territory result = territory.get(id);
		
		if (result == null && org != null && c != null) {
			result = parentRepository.getTerritory(UUID.randomUUID().toString(), c, org);
			territory.put(result.getId(), result);
		}
		
		return result;
	}
	
	@Override
	public boolean isTerritory(String id) {
		return territory.get(id) != null;
	}

	@Override
	public void save(Territory territory) {
		parentRepository.save(territory);
	}

	@Override
	public void delete(Territory territory) {
		parentRepository.delete(territory);
	}

}
