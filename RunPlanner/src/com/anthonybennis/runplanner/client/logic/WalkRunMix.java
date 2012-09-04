package com.anthonybennis.runplanner.client.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author abennis
 */
public class WalkRunMix
{
	private final static String WALK_RUN_SEPERATOR = "%";
	private List<Activity> _activeityList = new ArrayList<Activity>();
	
	/**
	 * Empty contructor, nothing to do.
	 */
	public WalkRunMix()
	{
		
	}
	
	/**
	 * 
	 * @param run
	 */
	public WalkRunMix(double run)
	{
		Activity runActivity = new Activity(1, Activity.RUN, String.valueOf(run));
		_activeityList.add(runActivity);
	}
	
	/**
	 * 
	 * @param walk
	 * @param run
	 * @param walk2
	 */
	public WalkRunMix(double walk, double run, double walk2)
	{
		Activity walk1Activity = new Activity(1,Activity.WALK, String.valueOf(walk));
		Activity runActivity = new Activity(2,Activity.RUN, String.valueOf(run));
		Activity walk2Activity = new Activity(3,Activity.WALK, String.valueOf(walk2));
		
		_activeityList.add(walk1Activity);
		_activeityList.add(runActivity);
		_activeityList.add(walk2Activity);
	}
	
	/**
	 * 
	 * @param walk
	 * @param run
	 * @param walk2
	 * @param run2
	 */
	public WalkRunMix(double walk, double run, double walk2, double run2, double walk3)
	{
		Activity walkActivity = new Activity(1,Activity.WALK, String.valueOf(walk));
		Activity runActivity = new Activity(2,Activity.RUN, String.valueOf(run));
		Activity walk2Activity = new Activity(3,Activity.WALK, String.valueOf(walk2));
		Activity run2Activity = new Activity(4,Activity.RUN, String.valueOf(run2));
		Activity walk3Activity = new Activity(5,Activity.WALK, String.valueOf(walk3));
		
		_activeityList.add(walkActivity);
		_activeityList.add(runActivity);
		_activeityList.add(walk2Activity);
		_activeityList.add(run2Activity);
		_activeityList.add(walk3Activity);
	}
	
	public void addActivity(Activity activity)
	{
		_activeityList.add(activity);
	}
	
	/**
	 * 
	 * @param walkRunMixAsString
	 * @return
	 */
	public static WalkRunMix convertStringToWalkRunMix(String walkRunMixAsString)
	{
		WalkRunMix mix = new WalkRunMix();
		
		String[] walkRunSplit = walkRunMixAsString.split(WALK_RUN_SEPERATOR);
		Activity activity;
		for (String string : walkRunSplit) 
		{
			if (!string.equals(""))
			{
				System.out.println("Split: " + string);
				activity = Activity.createActivityFromString(string);
				mix.addActivity(activity);
			}
		}
		
		
		return mix;
	}
	
	/**
	 * 
	 */
	public String toString()
	{
		StringBuilder walkRunAsString = new StringBuilder();
		
		for (Activity activity: _activeityList) 
		{
			walkRunAsString.append(activity.toString());
			walkRunAsString.append(WALK_RUN_SEPERATOR);
		}
		
		return walkRunAsString.toString();
	}
}