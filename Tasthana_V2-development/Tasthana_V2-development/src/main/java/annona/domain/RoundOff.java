package annona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@XmlRootElement

public class RoundOff {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private int decimalPlaces;
	
	private String nearestHighestLowest; //H for Highest/L For Lowest

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	public void setDecimalPlaces(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	public String getNearestHighestLowest() {
		return nearestHighestLowest;
	}

	public void setNearestHighestLowest(String nearestHighestLowest) {
		this.nearestHighestLowest = nearestHighestLowest;
	}
	
	
}

