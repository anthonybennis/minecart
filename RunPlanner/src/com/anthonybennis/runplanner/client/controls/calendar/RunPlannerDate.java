package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.Date;

import com.anthonybennis.runplanner.client.utils.SuperDateUtil;

public class RunPlannerDate 
{
	private int _date;
	private int _month;
	private int _year;
	
	public RunPlannerDate(int date, int month, int year)
	{
		_date = date;
		_month = month;
		_year = year;
	}
	
	public RunPlannerDate()
	{
		
	}
	
	public int getDate() 
	{
		return _date;
	}
	
	public void setDate(int date) 
	{
		this._date = date;
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
	
	/**
	 * Creates new RunPlannerDate object, one day later than the current one.
	 * @param date
	 * @return
	 */
	public static RunPlannerDate advanceOneDay(RunPlannerDate date)
	{
		RunPlannerDate newDate = new RunPlannerDate(date.getDate(), date.getMonth(), date.getYear());
		/*
		 * Now progress month...
		 */
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
}
