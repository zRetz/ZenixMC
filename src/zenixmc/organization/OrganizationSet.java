package zenixmc.organization;

import zenixmc.utils.InterfaceSet;

/**
 * A set of organizations.
 * @author james
 */
public class OrganizationSet extends InterfaceSet<Organization> {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -6982360192844491268L;

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
	 * @return <code>true</code> If the organization contains a clan.
	 */
	public boolean hasClan() {
		return getClan() != null;
	}
}
