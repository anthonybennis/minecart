package com.anthonybennis.runplanner.client.utils;

import com.google.gwt.i18n.client.LocaleInfo;

public class RunPlannerDate 
{
	public static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

	
	public RunPlannerDate()
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
		String[] monthNames = RunPlannerDate.getMonthNames();
		
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
		String monthName = "?";
		String[] monthNames = RunPlannerDate.getMonthNames();
		
		for (int i = 0; i < monthNames.length; i++) 
		{
			if ((i+1) == _month)
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
	public static RunPlannerDate convertStringToDate(String dateInStringFormat)
	{
		RunPlannerDate date = new RunPlannerDate();
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
				String[] weekDaysLong = RunPlannerDate.geDayNames(false);
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
	
	
}
