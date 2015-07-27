package zenixmc.persistance;

import java.io.File;
import java.util.logging.Logger;

import zenixmc.organization.OrganizationPlayerInterface;
import zenixmc.user.ZenixUserInterface;

public class OrganizationPlayerRepository extends Repository implements OrganizationPlayerRepositoryInterface {

	public OrganizationPlayerRepository(Logger logger, File directory) {
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
	public OrganizationPlayerInterface getOrganizationPlayer(ZenixUserInterface zui) {
		return null;
	}

	@Override
	public void setZenixUserRepository(ZenixUserRepositoryInterface zenixUserRepository) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(OrganizationPlayerInterface organizationPlayer) {
		// TODO Auto-generated method stub
		
	}

}
