package annona.dao;

import java.util.Date;
import java.util.List;

import annona.domain.HolidayConfiguration;
public interface HolidayConfigurationDAO {
	
	void save(HolidayConfiguration configuration);
	HolidayConfiguration update(HolidayConfiguration configuration);
	public void delete(int year,String state);
	public List<HolidayConfiguration> isPresent(int year,String state);
	public void delete(int year, String branchCode,String state);
	public List<HolidayConfiguration> isPresent(int year, String branchCode,String state);
	public HolidayConfiguration get(int year);
	public HolidayConfiguration getConfigurationByYearAndState(int year, String state);
	public HolidayConfiguration getConfigurationByBranchAndState(int year,String state, String branch);
	public HolidayConfiguration getHolidayConfiguration(Date date);
	public  List<Object> getHolidayConfigurationListForNext10Days(Date date, String branchCode);
	public  List<Object> getStateWiseHolidayConfigurationListForNext10Days(Date date, String state);
	public HolidayConfiguration getHolidayConfiguration(Date maturityDate, String branchCode);
	public HolidayConfiguration getHolidayConfigurationByState(Date maturityDate, String state);
}
