package zenixmc.persistance;

import java.util.UUID;

import zenixmc.text.TextInterface;

/**
 * Persistence of text in memory.
 * @author james
 */
public interface TextRepositoryInterface extends RepositoryInterface {

	/**
	 * Loads a text into memory.
	 * @param author
	 * 		
	 * @return
	 */
	TextInterface getText(UUID author);
	
	/**
	 * Saves a text.
	 * @param text
	 * 		The text to save.
	 */
	void save(TextInterface text);
}
