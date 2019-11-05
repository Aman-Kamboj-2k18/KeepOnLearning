package annona.services;

	import java.util.Calendar;




	public class FiscalDate {

	    private static final int    FIRST_FISCAL_MONTH  = Calendar.MARCH;

	 
	    public int getFiscalMonth(Calendar calendarDate) {
	        int month = calendarDate.get(Calendar.MONTH);
	        System.out.println("month.."+month);
	        int result = ((month - FIRST_FISCAL_MONTH - 1) % 12) + 1;
	        System.out.println("result.."+result);
	        if (result < 0) {
	            result += 12;
	        }
	        return result;
	    }

	    public int getFiscalYear(Calendar calendarDate) {
	        int month = calendarDate.get(Calendar.MONTH);
	        int year = calendarDate.get(Calendar.YEAR);
	        return (month >= FIRST_FISCAL_MONTH) ? year : year - 1;
	    }

	    public int getCalendarMonth(Calendar calendarDate) {
	        return calendarDate.get(Calendar.MONTH);
	    }

	    public int getCalendarYear(Calendar calendarDate) {
	        return calendarDate.get(Calendar.YEAR);
	    }

	   
	    private Calendar displayFinancialDate(Calendar calendar) {
	      //  FiscalDate fiscalDate = new FiscalDate(calendar);
	        int year = this.getFiscalYear(calendar);
	        int month=this.getFiscalMonth(calendar);
	        System.out.println("Current Date : " + calendar.getTime().toString());
	        System.out.println("Fiscal Years : " + year + "-" + (year + 1));
	      
	        if(month>calendar.get(Calendar.MONTH));
	        year=year+1;
	        
	        calendar.set(Calendar.YEAR, year);
	        calendar.set(Calendar.MONTH, month);
	      
	        return calendar;
	        
	    }
	 

	}
	

