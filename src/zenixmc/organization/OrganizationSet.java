package zenixmc.organization;

import java.util.HashMap;
import java.util.Map;

import zenixmc.organization.clans.Clan;
import zenixmc.utils.InterfaceSet;

/**
 * A set of organizations.
 * @author james
 */
public class OrganizationSet extends InterfaceSet<Organization> {

	/**
	 * Instantiate.
	 */
	public OrganizationSet() {
		super(Organization.class);
	}
	
	/**
	 * Sets the clan inside the organization set.
	 * @param c
	 * 		The clan to set.
	 */
	public void setClan(Clan c) {
		if (hasClan()) {
			this.remove(getClan());
		}
		if (c == null) {
			return;
		}
		this.add(c);
	}
	
	/**
	 * @return The clan inside the organization set.
	 */
	public Clan getClan() {
		
		for (Organization o : this.values) {
			if (o instanceof Clan) {
				return (Clan) o;
			}
		}
		return null;
	}
	
	/**
	 * @return Organization names and class types.
	 */
	public Map<String, Class<?>> getOrgs() {
		Map<String, Class<?>> orgs = new HashMap<>();
		for (Organization o : this.values) {
			if (o instanceof Clan) {
				orgs.put(o.getName(), Clan.class);
			}
		}
		return orgs;
	}
	
	/**
	 * @return <code>true</code> If the organization contains a clan.
	 */
	public boolean hasClan() {
		return getClan() != null;
	}
}
