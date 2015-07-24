package zenixmc.persistance;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import zenixmc.bending.BendingPlayerInterface;

public class BendingPlayerRepository extends Repository implements BendingPlayerRepositoryInterface {

	public BendingPlayerRepository(Logger logger, File directory) {
		super(logger, directory);
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public BendingPlayerInterface getBendingPlayer(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(BendingPlayerInterface bendingPlayer) {
		// TODO Auto-generated method stub
		
	}

}
