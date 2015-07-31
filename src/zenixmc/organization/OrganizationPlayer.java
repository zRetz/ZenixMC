package zenixmc.organization;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import zenixmc.user.ZenixUserInterface;

public class OrganizationPlayer implements OrganizationPlayerInterface {
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 467064890550309721L;

	/**
	 * The user of the organizationPlayer.
	 */
	private transient ZenixUserInterface zui;
	
	/**
	 * The maximum amount of influence.
	 */
	private int maxInfluence = 10;
	
	/**
	 * The amount of influence.
	 */
	private int influence = 0;
	
	/**
	 * The users organizations.
	 */
	private OrganizationSet organizations;
	
	/**
	 * Instantiate.
	 * @param logger
	 * 		The logger to debug/log.
	 */
	public OrganizationPlayer(ZenixUserInterface zui) {
		this.zui = zui;
	}

	@Override
	public ZenixUserInterface getZenixUser() {
		return zui;
	}
	
	@Override
	public void setInfluence(int value) {
		
		if (value > getMaxInfluence()) {
			return;
		}
		
		this.influence = value;
	}

	@Override
	public int getInfluence() {
		return this.influence;
	}
	
	@Override
	public void setMaxInfluence(int value) {
		
		if (value < getInfluence()) {
			setInfluence(value);
		}
		
		this.maxInfluence = value;
	}

	@Override
	public int getMaxInfluence() {
		return this.maxInfluence;
	}

	@Override
	public void setOrganizations(OrganizationSet organizations) {
		this.organizations = organizations;
	}
	
	@Override
	public OrganizationSet getOrganizations() {
		return organizations;
	}

	@Override
	public void setClan(Clan clan) {
		organizations.setClan(clan);
	}

	@Override
	public Clan getClan() {
		return organizations.getClan();
	}

}
