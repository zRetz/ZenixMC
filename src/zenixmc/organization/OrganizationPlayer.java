package zenixmc.organization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import zenixmc.ZenixMC;
import zenixmc.organization.clans.Clan;
import zenixmc.persistance.CachedOrganizationRepository;
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
	 * The pending invites of organizationPlayer.
	 */
	private Set<String> invites = new HashSet<>();

	/**
	 * Influence values.
	 */
	private Influence influence = new Influence(10, 10);

	/**
	 * The users organizations.
	 */
	private OrganizationSet organizations = new OrganizationSet();

	/**
	 * Instantiate.
	 * 
	 * @param logger
	 *            The logger to debug/log.
	 */
	public OrganizationPlayer(ZenixUserInterface zui) {
		this.zui = zui;
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
		influence.setInfluence(value);
	}

	@Override
	public int getInfluence() {
		return influence.getInfluence();
	}

	@Override
	public void setMaxInfluence(int value) {
		influence.setMaxInfluence(value);
	}

	@Override
	public int getMaxInfluence() {
		return influence.getMaxInfluence();
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
	
	@Override
	public boolean hasClan() {
		return getClan() != null;
	}
	
	@Override
	public void addInviteRequest(String clanName) {
		if (!(hasInviteFor(clanName))) {
			invites.add(clanName);
		}
	}

	@Override
	public void removeInviteRequest(String clanName) {
		if (hasInviteFor(clanName)) {
			invites.remove(clanName);
		}
	}
	
	@Override
	public boolean hasInviteFor(String clanName) {
		return invites.contains(clanName);
	}
	
	@Override
	public String toString() {
		return this.getZenixUser().getName();
	}

	/**
	 * Serialize this instance.
	 * 
	 * @param out
	 *            Target to which this instance is written.
	 * @throws IOException
	 *             Thrown if exception occurs during serialization.
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.writeObject(invites);
		out.writeObject(influence);
		Map<String, Class<?>> orgs = organizations.getOrgs();
		out.writeObject(orgs);
	}

	/**
	 * Deserialize this instance from input stream.
	 * 
	 * @param in
	 *            Input Stream from which this instance is to be deserialized.
	 * @throws IOException
	 *             Thrown if error occurs in deserialization.
	 * @throws ClassNotFoundException
	 *             Thrown if expected class is not found.
	 */
	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
		invites = (Set<String>) in.readObject();
		influence = (Influence) in.readObject();
		try {
			Field f = ZenixMC.instance.getClass().getDeclaredField("cachedOrganizationRepository");
			f.setAccessible(true);
			CachedOrganizationRepository cor = (CachedOrganizationRepository) f.get(ZenixMC.instance);
			organizations = cor.getOrganizations((Map<String, Class<?>>) in.readObject());
			System.out.println(organizations);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
