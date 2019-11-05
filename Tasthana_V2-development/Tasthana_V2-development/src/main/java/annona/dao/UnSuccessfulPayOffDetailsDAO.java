package annona.dao;

import java.util.Date;
import java.util.List;

import annona.domain.UnSuccessfulPayOff;



public interface UnSuccessfulPayOffDetailsDAO 
 {

	List<UnSuccessfulPayOff> unSuccessfulPayOffDetailsList(Date fromDate, Date toDate);
  }
