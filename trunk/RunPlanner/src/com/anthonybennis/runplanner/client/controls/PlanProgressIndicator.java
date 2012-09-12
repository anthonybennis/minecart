package com.anthonybennis.runplanner.client.controls;

import java.util.Date;

import com.google.gwt.canvas.client.Canvas;

/**
 * This widget shows a visual summary of the plan,
 * showing start, end and current progress.
 * 
 * 
 * @author abennis
 */
public class PlanProgressIndicator 
{
	/**
	 * TODO ENHANCEMENT 
	 * 
	 * @param startDate
	 * @param raceDate
	 */
	public PlanProgressIndicator(Date startDate, Date raceDate)
	{
		
	}
	
	/**
	 * Creates the GWT Panel container and contents, in this case a Canvas.
	 * @return
	 */
	public Canvas createCanvas()
	{
		Canvas canvas = Canvas.createIfSupported();
		
		/*
		 * TODO ENHANCEMENT Draw "cool" shiny background.
		 */
		
		/*
		 * TODO ENHANCEMENT  Get the number of days in this plan.
		 */
		
		/*
		 * TODO ENHANCEMENT  Draw line
		 */
		
		/*
		 * TODO ENHANCEMENT  Draw circle for start and for end point
		 */
		
		/*
		 * TODO ENHANCEMENT  Draw line representing today, as a percentage of progress.
		 */
		
		return canvas;
	}
}