package com.anthonybennis.runplanner.client.logic;

/**
 * TODO Add JavaDoc
 * 
 * TODO Need object for Walk, Run, SWIM/CYCLE Mix
 * 
 * @author abennis
 */
public class WalkRunMix
{
	/**
	 * TODO AB - Implement WalkRunMix
	 * Empty contructor, nothing to do.
	 */
	public WalkRunMix()
	{
		
	}
	
	public WalkRunMix(String walkRunAsString)
	{
		/*
		 * TODO Complete this.
		 */
		WalkRunMix.convertStringToWalkRunMix(walkRunAsString);
	}
	
	/**
	 * 
	 * @param run
	 */
	public WalkRunMix(double run)
	{
		
	}
	
	/**
	 * 
	 * @param walk
	 * @param run
	 * @param walk2
	 */
	public WalkRunMix(double walk, double run, double walk2)
	{
		
	}
	
	/**
	 * 
	 * @param walk
	 * @param run
	 * @param walk2
	 * @param run2
	 */
	public WalkRunMix(double walk, double run, double walk2, double run2)
	{
		
	}
	
	/**
	 * 
	 * @param walkRunMixAsString
	 * @return
	 */
	public static WalkRunMix convertStringToWalkRunMix(String walkRunMixAsString)
	{
		WalkRunMix mix = new WalkRunMix();
		return mix;
	}
	
	/**
	 * 
	 * @param walkRunMix
	 * @return
	 */
	public static String convertWalkRunMixToString(WalkRunMix walkRunMix)
	{
		String mixAsAString = "";
		return mixAsAString;
	}
	
	public String toString()
	{
		return WalkRunMix.convertWalkRunMixToString(this);
	}
}