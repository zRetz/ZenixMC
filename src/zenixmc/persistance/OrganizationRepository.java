package zenixmc.persistance;

import java.io.File;
import java.util.logging.Logger;

import zenixmc.organization.OrganizationPlayerInterface;

public class OrganizationRepository extends Repository implements OrganizationRepositoryInterface {

	public OrganizationRepository(Logger logger, File directory) {
		super(logger, directory);
		// TODO Auto-generated constructor stub
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
	public OrganizationPlayerInterface getOrganizationPlayer(String name) {
		// TODO Auto-generated method stub
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
