package com.anthonybennis.runplanner.client.logic;

import com.anthonybennis.runplanner.client.controls.calendar.RunPlannerDate;

/**
 * Bean representing a "day entry" of a training plan
 * @author abennis
 */
public class PlanItem
{
	public static enum PACE{FAST, SLOW, COMFORTABLE, REST, MIX};
	protected final static String SEPERATOR = "/";
	protected final static String DATE_SEPERATOR = "#";
	
	private int _number;
	private RunPlannerDate _date;
	private WalkRunMix _walkRunMix;
	private String _comment;
	private PACE _pace;
	
	/**
	 * Constructor
	 */
	public PlanItem(int number, RunPlannerDate date, WalkRunMix walkRunMix, PACE pace, String comment)
	{
		_number = number;
		_date = date;
		_walkRunMix = walkRunMix;
		_pace = pace;
		_comment = comment;
	}
	
	/**
	 * 
	 * @param stringRepresentation
	 */
	public PlanItem(String stringRepresentation)
	{
		PlanItem.createFromString(stringRepresentation);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNumber()
	{
		return _number;
	}
	
	/**
	 * 
	 * @return
	 */
	public RunPlannerDate getDate()
	{
		return _date;
	}
	
	/**
	 * 
	 * @return
	 */
	public WalkRunMix getWalkRunMix()
	{
		return _walkRunMix;
	}
	
	public PACE getPace()
	{
		return _pace;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getComment()
	{
		return _comment;
	}
	
	/**
	 *  Example: 1/22082012/W1R1W1R1W1/FAST/First day. Take it easy.$
	 */
	public String toString()
	{
		StringBuilder planStringBuilder = new StringBuilder();
		
		if (_comment == "")
		{
			_comment = " ";
		}
		
		planStringBuilder.append(_number);
		planStringBuilder.append(SEPERATOR);
		planStringBuilder.append(this.convertDateToString(_date));
		planStringBuilder.append(SEPERATOR);
		planStringBuilder.append(_walkRunMix.toString());
		planStringBuilder.append(SEPERATOR);
		planStringBuilder.append(_pace.toString());
		planStringBuilder.append(SEPERATOR);
		planStringBuilder.append(_comment);
		planStringBuilder.append(SEPERATOR);
		
		return planStringBuilder.toString(); // Convert Plan Item to String representation. 
	}
	
	/**
	 * 
	 * @param planItemAsString
	 * @return
	 */
	public static PlanItem createFromString(String planItemAsString)
	{
		String[] planItemSplitIntoParts = planItemAsString.split(SEPERATOR);
		
		int number = Integer.parseInt(planItemSplitIntoParts[0]);
		RunPlannerDate date = convertStringToDate(planItemSplitIntoParts[1]);
		WalkRunMix walkRunMix = WalkRunMix.convertStringToWalkRunMix(planItemSplitIntoParts[2]);
		PACE pace = convertStringToPace(planItemSplitIntoParts[3]);
		String comment = planItemSplitIntoParts[4];
		
		return new PlanItem(number, date, walkRunMix, pace, comment);
	}
	
	/*
	 * Test
	 */
	public static void main(String[] args) 
	{
		PlanItem planItem = new PlanItem(1,new RunPlannerDate(),new WalkRunMix(1,2,1),PACE.COMFORTABLE, "");
		String plas = planItem.toString();
		String[] planItemSplitIntoParts = plas.split(SEPERATOR);
		
		for (int i = 0; i < planItemSplitIntoParts.length; i++) 
		{
			System.out.println("Part " + i + ": " + planItemSplitIntoParts[i]);
		}
		
		PlanItem.createFromString(plas);
		
	}
	
	/**
	 * TODO AB Refactor to RunplannerDate (PlanItem.java)
	 * @return
	 */
	private static RunPlannerDate convertStringToDate(String date)
	{
		RunPlannerDate planItemDate = new RunPlannerDate();
		
		String[] dateSplit = date.split(DATE_SEPERATOR);
		
		planItemDate.setDate(Integer.parseInt(dateSplit[0]));
		planItemDate.setMonth(Integer.parseInt(dateSplit[1]));
		planItemDate.setYear(Integer.parseInt(dateSplit[2]));

		return planItemDate;
	}
	
	/**
	 * 
	 * @param paceAsString
	 * @return
	 */
	private static PACE convertStringToPace(String paceAsString)
	{
		PACE pace = null;
		
		if (paceAsString.equals(PACE.COMFORTABLE.toString()))
		{
			pace =  PACE.COMFORTABLE;
		}
		else if (paceAsString.equals(PACE.FAST.toString()))
		{
			pace =  PACE.FAST;
		}
		else if (paceAsString.equals(PACE.MIX.toString()))
		{
			pace =  PACE.MIX;
		}
		else if (paceAsString.equals(PACE.REST.toString()))
		{
			pace =  PACE.REST;
		}
		else if (paceAsString.equals(PACE.SLOW.toString()))
		{
			pace =  PACE.SLOW;
		}
		
		return pace;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	private String convertDateToString(RunPlannerDate date)
	{
		StringBuilder dateAsString = new StringBuilder();
		dateAsString.append(date.getDate());
		dateAsString.append(DATE_SEPERATOR);
		dateAsString.append(date.getMonth());
		dateAsString.append(DATE_SEPERATOR);
		dateAsString.append(date.getYear());
		dateAsString.append(DATE_SEPERATOR);
		
		return dateAsString.toString();
	}
}