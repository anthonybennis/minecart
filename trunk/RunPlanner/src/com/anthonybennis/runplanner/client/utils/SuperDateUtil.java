package com.anthonybennis.runplanner.client.utils;

import java.util.Date;

import com.anthonybennis.runplanner.client.controls.calendar.RunPlannerDate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;

/**
 * A collection of Utilities for dealing with Dates in GWT.
 * @author abennis
 */
public class SuperDateUtil 
{
	public static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthName(int month)
	{
		String monthName = "?";
		String[] monthNames = SuperDateUtil.getMonthNames();
		
		for (int i = 0; i < monthNames.length; i++) 
		{
			if (i == month)
			{
				monthName = monthNames[i];
				break;
			}	
		}
		
		return monthName;
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
			
		String dateString = dateInString[0];
		String monthString = dateInString[1];
		String yearString = dateInString[2]; 
				
		date.setDate(Integer.parseInt(dateString));
		date.setMonth(Integer.parseInt(monthString));
		date.setYear(Integer.parseInt(yearString));
		
		return date;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String[] getMonthNames()
	{
		return LocaleInfo.getCurrentLocale().getDateTimeFormatInfo().monthsFull();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String[] getWeekDayNames()
	{
		return LocaleInfo.getCurrentLocale().getDateTimeFormatInfo().weekdaysFull();
	}
	
	/**
	 * 
	 * @param startWithMonday
	 * @return
	 */
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
	public static int getFirstDayOfTheMonth(int month, int year)
	{
		int weekDayIndex = -1;

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
				weekDayIndex = i;
				break;
			}
		}
		
		if (GWT.isProdMode()) // Difference between Dev and script mode for Date.
		{
			weekDayIndex = weekDayIndex +  1; 
		}
		else
		{
			weekDayIndex = weekDayIndex +  2;
		}
		
		return weekDayIndex;
	}
	
	/**
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
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
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int calculateNumberOfDaysFromDate(RunPlannerDate date)
	{
		int daysToGo = 0;
		
		java.util.Date raceDate = new java.util.Date();
		raceDate.setDate(date.getDate());
		raceDate.setMonth(date.getMonth()); // Java base is 0 for month
		raceDate.setYear(date.getYear() - 1900);
		
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
	
	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDate(RunPlannerDate date1, RunPlannerDate date2)
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
	
	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isSameDate(Date date1, RunPlannerDate date2)
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