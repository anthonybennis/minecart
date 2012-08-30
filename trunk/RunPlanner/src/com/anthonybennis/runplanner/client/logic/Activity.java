package com.anthonybennis.runplanner.client.logic;

/**
 * 
 * @author abennis
 */
public class Activity 
{
	private final static String ACTIVITY_SEPERATOR = "@";
	private String _name;
	private String _value;
	private int _order;
	
	/*
	 * Known activities
	 */
	protected static final  String RUN = "Run";
	protected static final String WALK = "Walk";
	
	/**
	 * 
	 * @param name
	 * @param value
	 */
	public Activity(int numberOrder, String name, String value)
	{
		_order = numberOrder;
		_name = name;
		_value = value;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getValue()
	{
		return _value;
	}
	
	public int getNumber()
	{
		return _order;
	}
	
	/**
	 * ACTIVITY_SEPERATOR + _name + ACTIVITY_SEPERATOR + _value; 
	 */
	public String toString()
	{
		return _order + ACTIVITY_SEPERATOR + _name + ACTIVITY_SEPERATOR + _value + ACTIVITY_SEPERATOR; 
	}
	
	/**
	 * 
	 */
	public static Activity createActivityFromString(String activity)
	{
		String[] activitySplit = activity.split(Activity.ACTIVITY_SEPERATOR);
		Activity activityFromString = new Activity(Integer.parseInt(activitySplit[0]), activitySplit[1], activitySplit[2]);
		
		return activityFromString;
	}
}