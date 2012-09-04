package com.anthonybennis.runplanner.client.logic;

import com.anthonybennis.runplanner.client.controls.MessageBox;

/**
 * 
 * @author abennis
 *
 */
public class UserSettingsValidator 
{
	/**
	 * 
	 * @param distance
	 * @param date
	 * @param experience
	 * @return
	 */
	public boolean validateUserSettings(String distance, String date, String experience)
	{
		boolean isValid = false;
		
		/*
		 * Make sure user has entered everything
		 * and/or that the Browser has not detroyed user data.
		 */
		boolean distanceValueIsValid = this.validateDistanceSettings(distance);
		boolean dateValueIsValid = this.validateDateSettings(date);
		boolean experienceValueIsValid = this.validateExperienceSettings(experience);
		
		String errorMessage = null;
		if (!distanceValueIsValid)
		{
			errorMessage = "Please set the distance of the run you wish to plan for.";
		}
		else if (!dateValueIsValid)
		{
			errorMessage = "Please specify the date of the race you wish to plan for.";
		}
		else if (!experienceValueIsValid)
		{
			errorMessage = "Please set your level of running experience.";
		}
		
		if (errorMessage != null)
		{
			MessageBox messageBox = new MessageBox();
			messageBox.open(errorMessage);
		}
		else
		{
			isValid = true;
		}
		
		return isValid;
	}
	
	/**
	 * 
	 * @param distance
	 * @return
	 */
	private boolean validateDistanceSettings(String distance)
	{
		boolean isValid = false;
		
		if (distance != null)
		{
			isValid = true;
		}
		
		return isValid;
	}
	
	/**
	 * 
	 * @param raceDate
	 * @return
	 */
	private boolean validateDateSettings(String raceDate)
	{
		boolean isValid = false;
		
		if (raceDate != null)
		{
			isValid = true;
		}
		
		return isValid;
	}
	
	/**
	 * 
	 * @param experience
	 * @return
	 */
	private boolean validateExperienceSettings(String experience)
	{
		boolean isValid = false;
		
		if (experience != null)
		{
			isValid = true;
		}
		
		return isValid;
	}
}
