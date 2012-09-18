package com.anthonybennis.runplanner.client.utils;

import java.util.Date;

import com.anthonybennis.runplanner.client.controls.calendar.RunPlannerDate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;

public class SuperDateUtil 
{
	public static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
	private int _date = -1;
	private int _month = -1;
	private int _year = -1;
	
	/**
	 * 
	 */
	public SuperDateUtil()
	{
		
	}

	/**
	 * 
	 * @return
	 */
	public int getDate() 
	{
		return _date;
	}
	
	/**
	 * 
	 * @param day
	 */
	public void setDate(int day) 
	{
		this._date = day;
	}
	
	/**
	 * 
	 * @param day
	 */
	public void setDate(String day)
	{
		this._date = Integer.parseInt(day);
	}
	
	/**
	 * 
	 * @param monthName
	 */
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
	
	/**
	 * @return
	 */
//	public static Date advanceDateOneDay(Date date)
//	{
//		CalendarUtil.addDaysToDate(date, 1);
//		
//		/*
//		 * BUG!!!! - Only seems to occur in colmpiled, not dev mode. 
//		 */
////		if (day > date.getDate())
////		{
////			new MessageBox().open("Attempted to fix BUG");
////			CalendarUtil.addDaysToDate(date, -2);	
////		}
//		
//		/*
//		 * Now progress month...
//		 */
////		Date advancedDate = new Date();
////		int daysInMonth = SuperDateUtil.daysInMonth(date.getMonth(), date.getYear());
////		advancedDate.setYear(date.getYear());
////		advancedDate.setDate(date.getDate());
////			
////		if (date.getMonth() != 11)
////		{
////			advancedDate.setMonth(date.getMonth() + 1);
////		}
////		else
////		{
////			advancedDate.setMonth(0);
////			advancedDate.setYear(date.getYear() + 1);
////		}
//		
//		
////		// Bug in Firefox. Month does not advance! Don't ever use: 
////		// date.setDate(date.getDate() + 1);
////		
////		new MessageBox().open("** Current Date is : " + date.getDate() + ":" + date.getMonth() + ":" +  (date.getYear() + 1900));
////		int monthBefore = date.getMonth();
////		CalendarUtil.addDaysToDate(date, 1); // Recommended GWT approach.
////		int monthAfter = date.getMonth();
////		date.setDate(date.getDate() + 1);
////		new MessageBox().open("** Advanced one day. Now it's: " + date.getDate() + ":" + date.getMonth() + ":" +  (date.getYear() + 1900));
////		/*
////		 * Bug fix for Firefox.
////		 * Month does not advance, so we force it to advance if we're starting a new month....
////		 */
////		
////		if (date.getDate() == 1 &&
////				monthBefore == monthAfter)
////		{
////			CalendarUtil.addMonthsToDate(date, 1);
////			new MessageBox().open("** Error detected. So we add one month : " + date.getDate() + ":" + date.getMonth() + ":" +  (date.getYear() + 1900));
////			/*
////			 * Advance year
////			 */
////		}
//		
////		new MessageBox().open("Advanced one day: " + date.getDate() + ":" + date.getMonth() + ":" +  (date.getYear() + 1900));
//		
//		return date;
//	}
	
	/**
	 * 
	 * @param year
	 */
	public void setYear(String year)
	{
		this._year = Integer.parseInt(year);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMonthName() 
	{
		return SuperDateUtil.getMonthName(_month);
	}
	
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
	 * @return
	 */
	public int getMonth() 
	{
		return _month;
	}
	
	/**
	 * 
	 * @param month
	 */
	public void setMonth(int month) 
	{
		this._month = month;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getYear() 
	{
		return _year;
	}
	
	/**
	 * 
	 * @param year
	 */
	public void setYear(int year) 
	{
		this._year = year;
	}
	
	/**
	 * 
	 * @return
	 */
	public String convertToString()
	{
		return this.getDate() + "," + this.getMonth() + "," + this.getYear();
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
			
		date.setDate(dateInString[0]);
		date.setMonth(Integer.parseInt(dateInString[1]));
		date.setYear(dateInString[2]);
		
		
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
	public int calculateNumberOfDaysFromToday()
	{
		int daysToGo = 0;
		
		java.util.Date raceDate = new java.util.Date();
		raceDate.setDate(_date);
		raceDate.setMonth(_month); // Java base is 0 for month
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
		int desiredDay = 0; // Monday
		
		if (!GWT.isProdMode()) // Difference between Dev and script mode for Date.
		{
			/*
			 * In dev mode, Monday index is 1.
			 */
			desiredDay = 1;
		}
		
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
		date.setDate(this.getDate());
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