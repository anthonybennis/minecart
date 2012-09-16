package com.anthonybennis.runplanner.client.utils;

import java.util.Comparator;

import com.anthonybennis.runplanner.client.controls.calendar.MonthPanel;

/**
 * 
 * @author Anthony
 *
 */
public class MonthPanelSorter implements Comparator<MonthPanel> 
{
	/**
	 * Compares its two arguments for order. 
	 * Returns a negative integer, zero, or a positive integer 
	 * as the first argument is less than, equal to, or greater than the second.
	 * Negative = monthPanel1 is less than monthPanel2
	 * 0 = monthPanel1 is equal to monthPanel2
	 * Positive = monthPanel1 is greater to monthPanel2
	 */
	@Override
	public int compare(MonthPanel monthPanel1, MonthPanel monthPanel2) 
	{
		int year1 = monthPanel1.getYear();
		int year2 = monthPanel2.getYear();
		
		int compare = year1 - year2;
		
		if (compare == 0)
		{
			int month1 = monthPanel1.getMonth();
			int month2 = monthPanel2.getMonth();
			
			compare = month1 - month2;
		}
		
		return compare;
	}
}
