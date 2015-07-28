package zenixmc.organization;

import java.util.HashMap;

public class DefaultOrganizationPlayerData extends OrganizationPlayerData {

	public DefaultOrganizationPlayerData() {
		super();
		this.maxInfluence = 10;
		this.influence = 10;
		this.organizations = new HashMap<String, String>();
	}
	
}
