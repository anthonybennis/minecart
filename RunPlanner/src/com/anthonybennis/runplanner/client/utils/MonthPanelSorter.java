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
