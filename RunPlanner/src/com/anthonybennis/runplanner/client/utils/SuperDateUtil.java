package com.anthonybennis.runplanner.client.utils;

import java.util.Date;

import com.google.gwt.i18n.client.LocaleInfo;

public class SuperDateUtil 
{
	public static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

	
	public SuperDateUtil()
	{
		
	}
	
	private int _day = -1;
	private int _month = -1;
	private int _year = -1;
	
	public int getDay() 
	{
		return _day;
	}
	
	public void setDay(int day) 
	{
		this._day = day;
	}
	
	public void setDay(String day)
	{
		this._day = Integer.parseInt(day);
	}
	
	public void setMonth(String monthName)
	{
		String[] monthNames = SuperDateUtil.getMonthNames();
		
		for (int i = 0; i < monthNames.length; i++) 
		{
			if (monthNames[i].equals(monthName))
			{
				_month = i +1; // Base 1 for month, so Jan is 1, Feb is 2 etc...
				break;
			}	
		}
	}
	
	public void setYear(String year)
	{
		this._year = Integer.parseInt(year);
	}
	
	public String getMonthName() 
	{
		return SuperDateUtil.getMonthName(_month);
	}
	
	public static String getMonthName(int month)
	{
		String monthName = "?";
		String[] monthNames = SuperDateUtil.getMonthNames();
		
		for (int i = 0; i < monthNames.length; i++) 
		{
			if ((i+1) == month)
			{
				monthName = monthNames[i];
				break;
			}	
		}
		
		return monthName;
	}
	
	public int getMonth() 
	{
		return _month;
	}
	
	public void setMonth(int month) 
	{
		this._month = month;
	}
	
	public int getYear() 
	{
		return _year;
	}
	
	public void setYear(int year) 
	{
		this._year = year;
	}
	
	public String convertToString()
	{
		return this.getDay() + "," + this.getMonth() + "," + this.getYear();
	}
	
	/**
	 * 
	 */
	public static SuperDateUtil convertStringToDate(String dateInStringFormat)
	{
		SuperDateUtil date = new SuperDateUtil();
		String[] dateInString;
		
		if (dateInStringFormat != null)
		{
			dateInString = dateInStringFormat.split(",");
		}
		else
		{
			dateInString = new String[]{"1","1","2013"};
		}
			
		date.setDay(dateInString[0]);
		date.setMonth(Integer.parseInt(dateInString[1]));
		date.setYear(dateInString[2]);
		
		
		return date;
	}
	
	public static String[] getMonthNames()
	{
		return LocaleInfo.getCurrentLocale().getDateTimeFormatInfo().monthsFull();
	}
	
	public static String[] geDayNames(boolean startWithMonday)
	{
		String[] dayNames = LocaleInfo.getCurrentLocale().getDateTimeFormatInfo().weekdaysFull();
		
		if (startWithMonday)
		{
			String[] startWithMondayNames = new String[dayNames.length];
			
			startWithMondayNames[0] = dayNames[1];
			startWithMondayNames[1] = dayNames[2];
			startWithMondayNames[2] = dayNames[3];
			startWithMondayNames[3] = dayNames[4];
			startWithMondayNames[4] = dayNames[5];
			startWithMondayNames[5] = dayNames[6];
			startWithMondayNames[6] = dayNames[0];
			
			dayNames = startWithMondayNames;
		}
		
		return dayNames;
	}
	
	/**
	 * Month is base 0
	 */
	@SuppressWarnings("deprecation")
	public static String getFirstDayOfTheMonth(int month, int year)
	{
		String firstDayOfMonth = "";
		java.util.Date date = new java.util.Date();
		date.setDate(1);
		date.setMonth(month);
		date.setYear(year - 1900);
		
		String day = date.toString();
		String[] dateSplit = day.split(" ");
		String dayShortFormat = dateSplit[0];
		
		String[] weekDaysShort = LocaleInfo.getCurrentLocale().getDateTimeFormatInfo().weekdaysShort();
		
		for (int i = 0; i < weekDaysShort.length; i++) 
		{
			if (weekDaysShort[i].equals(dayShortFormat))
			{
				String[] weekDaysLong = SuperDateUtil.geDayNames(false);
				firstDayOfMonth = weekDaysLong[i];
				break;
			}
		}
		
		
		return firstDayOfMonth;
	}
	
	public static int daysInMonth(int month, int year) 
	{
	  final int daysPerMonth[] = {31,28,31,30,31,30,31,31,30,31,30,31};
	  int days = daysPerMonth[month];

	  //Check for leap days in Feb.
	  if (month == 1) 
	  {
	    if ((((year % 4) == 0) && ((year % 100) != 0)) || ((year % 400) == 0)) 
	    {
	      days++; //add a leap day
	    }
	  }
	  return days;
	}
	
	
	@SuppressWarnings("deprecation")
	public int calculateNumberOfDaysFromToday()
	{
		int daysToGo = 0;
		
		java.util.Date raceDate = new java.util.Date();
		raceDate.setDate(_day);
		raceDate.setMonth(_month - 1); // Java base is 0 for month
		raceDate.setYear(_year - 1900);
		
		java.util.Date todaysDate = new java.util.Date();
	    
		if (todaysDate.compareTo(raceDate) <0)
		{
			daysToGo = (int)Math.ceil(((double)raceDate.getTime() - todaysDate.getTime()) / MILLIS_IN_A_DAY);
		}
		
		return daysToGo;
	}
	
	/**
	 * Gets the next Monday from a given date.
	 * 
	 * @param startDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getNextMonday(Date startDate)
	{
		int desiredDay = 1; // Monday
		
		int currDay = startDate.getDay(); 
		int daysToJump = (7+desiredDay-currDay)%7;
		
		Date nextMonday = new Date(startDate.getTime() + daysToJump * 24 * 60 
		* 60 * 1000); 
		
		return nextMonday;
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Date toDate()
	{
		Date date = new Date();
		date.setDate(this.getDay());
		date.setMonth(this.getMonth());
		date.setYear(this.getYear());
		
		return date;
	}
	
	/**
	 * Compares a date by day/month/year, ignoring
	 * minutes and seconds.
	 * 
	 * @param date
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isSameDate(Date date1, Date date2)
	{
		boolean datesMatch = false;
		
		if(date1 != null && date2 != null
				&& date1.getDate() == date2.getDate()
				&& date1.getMonth() == date2.getMonth()
				&& date1.getYear() == date2.getYear())
		{
			datesMatch = true;
		}
		
		
		return datesMatch;
	}
}