package zenixmc.organization;

/**
 * Interface that handles organizations with influence.
 * 
 * @author james
 */
public interface Influential extends Organization {

	/**
	 * Calculates the total influence of a set of members.
	 * @return The total influence of the members.
	 */
	int calcTotalInfluence();
	
	/**
	 * Calculates the total max influence of a set of members.
	 * @return The total max influence of the members.
	 */
	int calcTotalMaxInfluence();
	
	/**
	 * @param members
	 * 		The members to compare.
	 * @return <code>true</code> If sub-class' members have more influence than other members. 
	 */
	boolean isGreaterThan(Influential members);
	
	/**
	 * @param members
	 * 		The members to compare.
	 * @return <code>true</code> If sub-class' members have less influence than other members. 
	 */
	boolean isLessThan(Influential members);
}
