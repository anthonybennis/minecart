package com.anthonybennis.runplanner.client.utils;

import com.google.gwt.i18n.client.LocaleInfo;

public class Date 
{
	public Date()
	{
		
	}
	
	public Date(String stringformat)
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
		String[] monthNames = Date.getMonthNames();
		
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
		String[] monthNames = Date.getMonthNames();
		
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
	public static Date convertStringToDate(String dateInStringFormat)
	{
		Date date = new Date();
		String[] dateInString;
		
		if (dateInStringFormat != null)
		{
			dateInString = dateInStringFormat.split(",");
		}
		else
		{
			dateInString = new String[]{"1","January","2013"};
		}
			
		date.setDay(dateInString[0]);
		date.setMonth(dateInString[1]);
		date.setYear(dateInString[2]);
		
		
		return date;
	}
	
	public static String[] getMonthNames()
	{
		return LocaleInfo.getCurrentLocale().getDateTimeFormatInfo().monthsFull();
	}
	
	public int calculateNumberOfDaysFromToday()
	{
		/*
		 * TODO AB
		 */
		return 18;
	}
	
	
}
