package zenixmc.persistance;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

import zenixmc.text.TextInterface;

public class TextRepository extends Repository implements TextRepositoryInterface {

	public TextRepository(Logger logger, File directory) {
		super(logger, directory);
	}

	@Override
	public void open(String openMessage) {
	}

	@Override
	public void close(String closeMessage) {
	}

	@Override
	public void save(Object object) {
	}

	@Override
	public TextInterface getText(UUID author) {
		return null;
	}

	@Override
	public void save(TextInterface text) {
		
	}
	
}
