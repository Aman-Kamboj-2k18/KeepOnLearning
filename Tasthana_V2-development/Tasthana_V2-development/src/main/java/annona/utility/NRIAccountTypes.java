/**
 * 
 */
package annona.utility;

/**
 * @author INDIAN
 *
 */
public enum NRIAccountTypes {
	NRE("NRE"), NRO("NRO"), FCNR("FCNR"), RFC("RFC"), PRP("PRP");
	private String value;

	private NRIAccountTypes(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}


