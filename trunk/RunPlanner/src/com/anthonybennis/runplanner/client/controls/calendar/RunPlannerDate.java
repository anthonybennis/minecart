package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.Date;

import com.anthonybennis.runplanner.client.utils.SuperDateUtil;

/**
 * A class to represent dates in RunPlanner.
 * GWT Date implementation does not work in all Browsers.
 * @author abennis
 */
public class RunPlannerDate 
{
	private int _date;
	private int _month;
	private int _year;
	
	/**
	 * Constructor.
	 * 
	 * @param date 1 -> 31
	 * @param month 0 -> 11
	 * @param year Note, this is in Java Date form (e.g. 2012 is represented as 112).
	 */
	public RunPlannerDate(int date, int month, int year)
	{
		_date = date;
		_month = month;
		_year = year;
	}

	/**
	 * Basic Constructor.
	 */
	public RunPlannerDate()
	{
		
	}
	
	/**
	 * Returns the date.
	 * @return
	 */
	public int getDate() 
	{
		return _date;
	}
	
	/**
	 * 
	 * @param date
	 */
	public void setDate(int date) 
	{
		this._date = date;
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
	 * Creates new RunPlannerDate object, one day later than the current one.
	 * @param date
	 * @return
	 */
	public static RunPlannerDate advanceOneDay(RunPlannerDate date)
	{
		RunPlannerDate newDate = new RunPlannerDate(date.getDate(), date.getMonth(), date.getYear());

		int daysInMonth = SuperDateUtil.daysInMonth(newDate.getMonth(), newDate.getYear());
			
		if (newDate.getDate() < daysInMonth) 
		{
			newDate.setDate(newDate.getDate() + 1);
		}
		else // Last Day of current month. Start new month
		{
			newDate.setDate(1); // First day of new month
			
			if (newDate.getMonth() < 11)
			{
				newDate.setMonth(newDate.getMonth() + 1);
			}
			else // December, moving to new year!
			{
				newDate.setMonth(0); // Start new month
				newDate.setYear(newDate.getYear() + 1); // Start new month
			}
		}
		
		return newDate;
	}

	
	@SuppressWarnings("deprecation")
	public static Date convert(RunPlannerDate runplannerDate)
	{
		Date date = new Date();
		date.setDate(runplannerDate.getDate());
		date.setMonth(runplannerDate.getMonth());
		date.setYear(runplannerDate.getYear());
		
		return date;
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
	 * 
	 * @param year
	 */
	public void setYear(String year)
	{
		this._year = Integer.parseInt(year);
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
	 * @return
	 */
	public String toString()
	{
		return this.getDate() + "," + this.getMonth() + "," + this.getYear();
	}
}