package annona.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TaxExemptionConfigurationParser {

	private List<TaxExemptionConfigurationDTO> saveExemptionConfigurationList;

	public List<TaxExemptionConfigurationDTO> getSaveExemptionConfigurationList() {
		return saveExemptionConfigurationList;
	}

	public void setSaveExemptionConfigurationList(List<TaxExemptionConfigurationDTO> saveExemptionConfigurationList) {
		this.saveExemptionConfigurationList = saveExemptionConfigurationList;
	}

}


	
