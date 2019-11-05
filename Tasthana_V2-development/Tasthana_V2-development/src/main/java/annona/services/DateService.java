package annona.services;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * Class to manipulate Dates
 */
@Service
public class DateService {
	
	 public static Date loginDate;
	/**
	 * Method to add four days to current date
	 */
//	public static Date currentDateFourDaysAhead() {
//		Calendar currentDate = Calendar.getInstance();
//		currentDate.add(Calendar.DAY_OF_MONTH, 4);
//		currentDate.set(Calendar.HOUR_OF_DAY, 0);
//		currentDate.set(Calendar.MINUTE, 0);
//		currentDate.set(Calendar.SECOND, 0);
//		currentDate.set(Calendar.MILLISECOND, 0);
//
//		return currentDate.getTime();
//	}

	/**
	 * Add Days to current Date.
	 *
	 */
	public static Date generateDaysDate(Date date, int t) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);
		currentDate.add(Calendar.DAY_OF_MONTH, t - 1);
		return currentDate.getTime();
	}

	/**
	 * Add Months to current Date.
	 *
	 */
	public static Date generateMonthsDate(Date date, int t) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);
		currentDate.add(Calendar.MONTH, t);
		currentDate.add(Calendar.DAY_OF_MONTH, -1);
		return currentDate.getTime();
	}
	
	/**
	 * Add Months to a Date.
	 *
	 */
	public static Date addMonths(Date date, int t) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);
		currentDate.add(Calendar.MONTH, t);
		return currentDate.getTime();
	}

	/**
	 * Add Year to a Date.
	 *
	 */
	public static Date addYear(Date date, int t) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);
		currentDate.add(Calendar.YEAR, t);
		return currentDate.getTime();
	}
	/**
	 * Add Years to current Date.
	 * 
	 */
	public static Date generateYearsDate(Date date, int t) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);
		currentDate.add(Calendar.YEAR, t);
		currentDate.add(Calendar.DAY_OF_MONTH, -1);
		return currentDate.getTime();
	}

	/**
	 * Method to get only date truncating time
	 */
	public static Date getDate(Date givenDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(givenDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * Method to get no of days between two dates
	 */
	public static int getDaysBetweenTwoDates(Date fromDate, Date toDate) {
		fromDate = DateService.getDate(fromDate);
		toDate = DateService.getDate(toDate);
		long diff = toDate.getTime() - fromDate.getTime();
		int days = (int) (diff / (1000 * 60 * 60 * 24));

		return days;
	}

	public static int compareDate(Date fromDate, Date toDate) {

		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		String fromDateStr = ft.format(fromDate);
		String toDateStr = ft.format(toDate);

		try {

			fromDate = ft.parse(fromDateStr);
			toDate = ft.parse(toDateStr);
		} catch (Exception e) {
		}
		return toDate.compareTo(fromDate);
	}

	/**
	 * Method to generate Current Date without time
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		Calendar currentDate = Calendar.getInstance();
		if(DateService.loginDate == null)
			DateService.loginDate = currentDate.getTime();
		
		currentDate.setTime(DateService.loginDate);

		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);

		return currentDate.getTime();
	}
	
	/**
	 * Method to generate Current Date without time
	 * 
	 * @return
	 */
	public static Date getNewDate() {
		Calendar currentDate = Calendar.getInstance();		
		currentDate.setTime(DateService.loginDate);
		
		Calendar currentDateNew = Calendar.getInstance();
		

		currentDate.set(Calendar.HOUR_OF_DAY, currentDateNew.get(Calendar.HOUR_OF_DAY));
		currentDate.set(Calendar.MINUTE, currentDateNew.get(Calendar.MINUTE));
		currentDate.set(Calendar.SECOND, currentDateNew.get(Calendar.SECOND));
		currentDate.set(Calendar.MILLISECOND, currentDateNew.get(Calendar.MILLISECOND));

		return currentDate.getTime();
	}

	/**
	 * Method to generate Current Date without time
	 * 
	 * @return
	 */
	public static Date getCurrentDateTime() {
//		Calendar currentDate = Calendar.getInstance();
//		return currentDate.getTime();
		
		return getNewDate();
	}

	public static Date getLastDateOfCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateService.loginDate);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Date getLastDateOfMonth(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Date setDate(Date givenDate, int day) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(givenDate);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	public static Period calculateAge(Date birthDate) {
		LocalDate birthdateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate currentDate =   getCurrentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if ((birthDate != null) && (currentDate != null)) {
//			return String.valueOf(Period.between(birthdateLocal, currentDate).getYears());
			return Period.between(birthdateLocal, currentDate);
		} else {
			return null;
		}
	}

	public static String getRandomNumBasedOnDate() {
		Date dNow = getCurrentDateTime();//new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
		String datetime = ft.format(dNow);
		return datetime;
	}

	public static Date getFirstDateOfNextMonth(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date nextMonthFirstDay = calendar.getTime();
		return nextMonthFirstDay;
	}

	public static Date getFirstDateOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date nextMonthFirstDay = calendar.getTime();
		return nextMonthFirstDay;
	}

	public static Date getFirstDateOfCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateService.loginDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date nextMonthFirstDay = calendar.getTime();
		return nextMonthFirstDay;
	}

	public static String getQuarter(Date date) {
		// Format: "2017-Q4" for december,2017
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int quarterNo = (calendar.get(Calendar.MONTH) - 1) / 3 + 1;
		return "calendar.get(Calendar.Year)-Q" + quarterNo;
	}

	public static int getQuarterNo(Date date) {
		// Format: "4" for december,2017
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int quarterNo = (calendar.get(Calendar.MONTH) - 1) / 3 + 1;
		return quarterNo;
	}

	public static String getFinancialYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		int currentYear = calendar.get(Calendar.YEAR);
		int prevYear = currentYear - 1;
		int nextYear = currentYear + 1;
		String financialYear = null;

		if (month > 2)
			financialYear = currentYear + "-" + nextYear;
		else
			financialYear = prevYear + "-" + currentYear;

		return financialYear;
	}
	
	public static Date getStartDateOfFinancialYear(String financialYear) {
		String[] yrs = financialYear.split("-");
		Integer startYear= Integer.parseInt(yrs[0]);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 3);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.YEAR, startYear);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
	
	public static Date getEndDateOfFinancialYear(String financialYear) {
		String[] yrs = financialYear.split("-");
		Integer endYear= Integer.parseInt(yrs[1]);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.YEAR, endYear);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Integer getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	public static Integer getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static Date getDateInDDmmYYYY() {
		Date dNow = getCurrentDateTime();//new Date();
		SimpleDateFormat ft = new SimpleDateFormat("ddMMyyhhmmss");
		String datetime = ft.format(dNow);
		try {
			System.out.println("try..time.." + ft.parse(datetime));
			return ft.parse(datetime);
		} catch (Exception e) {
		}
		return dNow;
	}

	public static Date addDays(Date date, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date); //
		c.add(Calendar.DATE, days);
		String datetime = sdf.format(c.getTime());
		try {
			date = sdf.parse(datetime);
			return date;
		} catch (Exception e) {
		}
		return date;

	}

	public static Date getLastDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); //

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
	
	public static int getNoOfDaysInMonth(Date date) {
				
		return getDaysBetweenTwoDates(getFirstDateOfMonth(date), getLastDate(date)) + 1;
	}

	public static Date getQuaterlyDeductionDate(int deductionDay) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateService.loginDate);
		int quarterNo = DateService.getQuarterNo(calendar.getTime());

		int month = calendar.get(Calendar.MONTH);

		if(month == 2 || month == 5 || month == 8 || month == 11)
			quarterNo = quarterNo + 1;
			
		if (quarterNo == 1) {
			calendar.set(Calendar.MONTH, 2);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (quarterNo == 2) {
			calendar.set(Calendar.MONTH, 5);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (quarterNo == 3) {
			calendar.set(Calendar.MONTH, 8);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (quarterNo == 4) {
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.set(Calendar.DAY_OF_MONTH, deductionDay);
		
		if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), calendar.getTime()) > 0)
			return calendar.getTime();
		else
		{
			calendar.add(Calendar.MONTH, 3);
			return calendar.getTime();
		}

	}

	public static Date getHalfYearlyDeductionDate(int deductionDay) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateService.loginDate);
		if(calendar.MONTH >=3 && calendar.MONTH <8)
		{
			
			calendar.set(Calendar.MONTH, 8);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		else
		{
			if(calendar.MONTH>=8)
			{
				calendar.add(Calendar.YEAR, 1);
			}
			
			if(calendar.MONTH==2)
				calendar.add(Calendar.MONTH, 6);
			else
				calendar.set(Calendar.MONTH, 2);
			
			calendar.set(Calendar.DAY_OF_MONTH, 1);
	
		}
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.set(Calendar.DAY_OF_MONTH, deductionDay);
		
		if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), calendar.getTime()) > 0)
			return calendar.getTime();
		else
		{
			calendar.add(Calendar.MONTH, 6);
			return calendar.getTime();
		}

	}
    
	public static Date getAnnualDeductionDate(int deductionDay) {

		Date currentDate = DateService.loginDate;
		Date dt = DateService.addYear(currentDate, 1);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);	
		calendar.set(Calendar.DAY_OF_MONTH, deductionDay);	
		
		return calendar.getTime();

	}
    
    public static Integer getDayOfMonth(Date date)
    {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.DAY_OF_MONTH);
    }
    
	public static Date getQuaterlyDeductionDateOnContinuePayment(int deductionDay) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateService.loginDate);
		int quarterNo = DateService.getQuarterNo(calendar.getTime());

		int month = calendar.get(Calendar.MONTH);

	/*	if(month == 2 || month == 5 || month == 8 || month == 11)
			quarterNo = quarterNo + 1;*/
			
		if (quarterNo == 1) {
			calendar.set(Calendar.MONTH, 2);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (quarterNo == 2) {
			calendar.set(Calendar.MONTH, 5);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (quarterNo == 3) {
			calendar.set(Calendar.MONTH, 8);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (quarterNo == 4) {
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.set(Calendar.DAY_OF_MONTH, deductionDay);
		
		if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), calendar.getTime()) > 0)
			return calendar.getTime();
		else
		{
			calendar.add(Calendar.MONTH, 3);
			return calendar.getTime();
		}

	}
	
	public static Date getHalfYearlyDeductionDateOnContinuePayment(int deductionDay) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateService.loginDate);
		if(calendar.MONTH >=3 && calendar.MONTH <=8)
		{
			
			calendar.set(Calendar.MONTH, 8);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		else
		{
			if(calendar.MONTH>=9)
			{
				calendar.add(Calendar.YEAR, 1);
			}

			calendar.set(Calendar.MONTH, 2);			
			calendar.set(Calendar.DAY_OF_MONTH, 1);	
		}
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.set(Calendar.DAY_OF_MONTH, deductionDay);
		
		if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), calendar.getTime()) > 0)
			return calendar.getTime();
		else
		{
			calendar.add(Calendar.MONTH, 6);
			return calendar.getTime();
		}
	}

	public static Date getAnnualDeductionDateOnContinuePayment() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateService.loginDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);		
		
		if(calendar.MONTH>=2 && calendar.MONTH<=11)
		{
			calendar.add(Calendar.YEAR, 1);
		}
		calendar.set(Calendar.MONTH, 2);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date dt = getLastDateOfMonth(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, getDayOfMonth(dt));
		
		
		if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), calendar.getTime()) > 0)
			return calendar.getTime();
		else
		{
			calendar.add(Calendar.MONTH, 12);
			return calendar.getTime();
		}

	}


	public static Date getQuaterlyInterestPayOffDate() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateService.loginDate);
		int quarterNo = DateService.getQuarterNo(calendar.getTime());

		int month = calendar.get(Calendar.MONTH);

		if(month == 2 || month == 5 || month == 8 || month == 11)
			quarterNo = quarterNo;
			
		if (quarterNo == 1) {
			calendar.set(Calendar.MONTH, 2);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			
		}
		if (quarterNo == 2) {
			calendar.set(Calendar.MONTH, 5);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (quarterNo == 3) {
			calendar.set(Calendar.MONTH, 8);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (quarterNo == 4) {
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		Date dt = getLastDateOfMonth(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, getDayOfMonth(dt));
		
		if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), calendar.getTime()) > 0)
			return calendar.getTime();
		else
		{
			calendar.add(Calendar.MONTH, 3);
			return calendar.getTime();
		}

	}

	public static Date getHalfYearlyInterestPayOffDate() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateService.loginDate);
		if(calendar.MONTH >=3 && calendar.MONTH <=8)
		{
			
			calendar.set(Calendar.MONTH, 8);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		else
		{
			if(calendar.MONTH>8)
			{
				calendar.add(Calendar.YEAR, 1);
			}
			
			if(calendar.MONTH==2)
				calendar.add(Calendar.MONTH, 5);
			else
				calendar.set(Calendar.MONTH, 2);
			
			calendar.set(Calendar.DAY_OF_MONTH, 1);
	
		}
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		Date dt = getLastDateOfMonth(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, getDayOfMonth(dt));
		
		if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), calendar.getTime()) > 0)
			return calendar.getTime();
		else
		{
			calendar.add(Calendar.MONTH, 6);
			return calendar.getTime();
		}

	}
    
	public static Date getAnnualInterestPayOffDate() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateService.loginDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);		
		
		if(calendar.MONTH>=2 && calendar.MONTH<=11)
		{
			calendar.add(Calendar.YEAR, 1);
		}
		calendar.set(Calendar.MONTH, 2);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date dt = getLastDateOfMonth(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, getDayOfMonth(dt));
		
		if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), calendar.getTime()) > 0)
			return calendar.getTime();
		else
		{
			calendar.add(Calendar.MONTH, 12);
			return calendar.getTime();
		}

	}
   
		public static int getMonthDiff(Date fromDate, Date toDate) {
		  int daysDiff = DateService.getMonth(toDate)- DateService.getMonth(fromDate);
		  return daysDiff;
		 }

		 public static int getYearDiff(Date fromDate, Date toDate) {
		  int yearDiff= DateService.getYear(toDate) - DateService.getYear(fromDate);
		  return yearDiff;
		 }
	
		 public static boolean isLastDateOfFinancialYear(Date date)
		 {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(DateService.loginDate);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);	
				calendar.setTime(date);
				
				if(calendar.MONTH==2)
				{
					Date dt = getLastDateOfMonth(calendar.getTime());				
				
					if (DateService.getDaysBetweenTwoDates(dt, calendar.getTime())== 0)
						return true;
					else
					{
						return false;
					}
				}
				else
					return false;
		 }
		 
	public static String getPreviousFinancialYear(String currentFinancialYear) {

		String prevFinancialYear = "";
		String[] years = currentFinancialYear.split("-");
		if (years.length == 2) {
			int year1 = Integer.parseInt(years[0]) -1;
			int year2 = Integer.parseInt(years[1]) -1;

			prevFinancialYear = year1 + "-" + year2;
		}

		return prevFinancialYear;
	}
	
	public static Integer getDayNoFromDate(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar.get(Calendar.DAY_OF_MONTH);
				
	}
	
	// To get the number of days in a years 
	public static Integer getNumOfDaysInYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		int numOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
		System.out.println(numOfDays);
		return numOfDays;
		}
}
