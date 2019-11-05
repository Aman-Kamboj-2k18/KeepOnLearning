package annona.dao;

import java.util.List;

import annona.domain.RoundOff;

	public interface RoundOffDAO {
	
	public void save(RoundOff roundOff);
	
	public void update(RoundOff roundOff);
	
	public RoundOff getRoundingOff();
	
	public List<RoundOff> getAllDetailsFromRoundOff();
	
	public RoundOff getRoundOffById(Long id);
	
	public Long getCountOfDecimalPlacesAndNearest(int decimalPlaces, String nearestHighestLowest);

}
