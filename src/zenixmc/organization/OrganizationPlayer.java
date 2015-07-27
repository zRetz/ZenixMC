package zenixmc.organization;

import zenixmc.organization.clans.Clan;
import zenixmc.user.ZenixUserInterface;

public class OrganizationPlayer implements OrganizationPlayerInterface {

	/**
	 * The user of the organizationPlayer.
	 */
	private ZenixUserInterface zui;
	
	/**
	 * The users organizations.
	 */
	private OrganizationSet organizations;
	
	public OrganizationPlayer() {
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
