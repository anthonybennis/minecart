package com.anthonybennis.runplanner.client.logic;

import com.anthonybennis.runplanner.client.controls.MessageBox;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

/**
 * 
 * @author abennis
 *
 */
public class UserSettingsValidator 
{
	private final String NO_ERROR = "NO_ERROR";
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
		String distanceValueIsValid = this.validateDistanceSettings(distance);
		String dateValueIsValid = this.validateDateSettings(date);
		String experienceValueIsValid = this.validateExperienceSettings(experience);
		
		String messageBoxMessage = null;
		
		if (!distanceValueIsValid.equals(NO_ERROR))
		{
			messageBoxMessage = distanceValueIsValid;
		}
		else if (!dateValueIsValid.equals(NO_ERROR))
		{
			messageBoxMessage = dateValueIsValid;
		}
		else if (!experienceValueIsValid.equals(NO_ERROR))
		{
			messageBoxMessage = experienceValueIsValid;
		}
		else
		{
			/*
			 * Convert String values to Units and validate toegther.
			 */
			// TODO Validate that race date is acceptable - there's enough time.
//			if (PlanGenerator.isThereSuffecientTimeToPlan(distance, _r, experience))
//			{
//				messageBoxMessage = experienceValueIsValid;
//			}
		}

		/*
		 * Test new Popup
		 */


		
		
//		if (messageBoxMessage != null)
//		{
//			MessageBox messageBox = new MessageBox();
//			messageBox.open(messageBoxMessage);
//		}
//		else
//		{
			isValid = true;
//		}
		
		return isValid;
	}
	
	/**
	 * Return an errorMessage if error. 
	 * @param distance
	 * @return
	 */
	private String validateDistanceSettings(String distance)
	{
		String errorMessage = NO_ERROR;
		
		if (distance == null)
		{
			errorMessage = "Please set the distance of the run you wish to plan for.";
		}
		
		return errorMessage;
	}
	
	/**
	 * 
	 * @param raceDate
	 * @return
	 */
	private String validateDateSettings(String raceDate)
	{
		String errorMessage = NO_ERROR;
		
		if (raceDate == null)
		{
			errorMessage = "Please specify the date of the race you wish to plan for.";
		}
		
		return errorMessage;
	}
	
	/**
	 * 
	 * @param experience
	 * @return
	 */
	private String validateExperienceSettings(String experience)
	{
		String errorMessage = NO_ERROR;
		
		if (experience == null)
		{
			errorMessage =  "Please set your level of running experience.";
		}

		return errorMessage;
	}
}