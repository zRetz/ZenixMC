package zenixmc.organization;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import zenixmc.user.ZenixUserInterface;

public class OrganizationPlayer implements OrganizationPlayerInterface {

	/**
	 * The logger to debug/log.
	 */
	private final Logger logger;
	
	/**
	 * The user of the organizationPlayer.
	 */
	private ZenixUserInterface zui;
	
	/**
	 * The organizationPlayer data.
	 */
	private OrganizationPlayerData d;
	
	/**
	 * The users organizations.
	 */
	private OrganizationSet organizations;
	
	/**
	 * Instantiate.
	 * @param logger
	 * 		The logger to debug/log.
	 */
	public OrganizationPlayer(Logger logger) {
		this.logger = logger;
	}
	
	@Override
	public void setZenixUser(ZenixUserInterface zui) {
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
		
		d.influence = value;
	}

	@Override
	public int getInfluence() {
		return d.influence;
	}
	
	@Override
	public void setMaxInfluence(int value) {
		
		if (value < getInfluence()) {
			setInfluence(value);
		}
		
		d.maxInfluence = value;
	}

	@Override
	public int getMaxInfluence() {
		return d.maxInfluence;
	}
	
	@Override
	public Map<String, Class<?>> getMappedOrganizations() {
		
		Map<String, Class<?>> result = new HashMap<>();
		
		for (Map.Entry<String, String> map : d.organizations.entrySet()) {
			try {
				result.put(map.getKey(), Class.forName(map.getValue()));
			} catch (ClassNotFoundException e) {
				logger.log(Level.WARNING, "Cannot find organization class.");
			}
		}
		
		return result;
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
		d.organizations.remove(organizations.getClan());
		d.organizations.put(clan.getName(), Clan.class.getName());
		organizations.setClan(clan);
	}

	@Override
	public Clan getClan() {
		return organizations.getClan();
	}

	@Override
	public void setData(OrganizationPlayerData data) {
		this.d = data;
	}

	@Override
	public OrganizationPlayerData getData() {
		return d;
	}

}
