package com.anthonybennis.runplanner.client.handlers;

import java.util.List;

import com.anthonybennis.runplanner.client.DistancePanelManager;
import com.anthonybennis.runplanner.client.controls.calendar.CalanderManager;
import com.anthonybennis.runplanner.client.logic.PlanGenerator;
import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.anthonybennis.runplanner.client.logic.UserSettingsValidator;
import com.anthonybennis.runplanner.client.storage.Persistance;
import com.anthonybennis.runplanner.client.utils.SuperDateUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;

/**
 * Creates Plan from user selected options
 * @author Anthony
 */
public class CreatePlanClickHandler implements TouchEndHandler, ClickHandler
{
	private CalanderManager _calanderManager;
	
	/**
	 * 
	 */
	public CreatePlanClickHandler(CalanderManager calanderManager)
	{
		_calanderManager = calanderManager;
	}
	
	@Override
	public void onTouchEnd(TouchEndEvent event) 
	{
		this.createPlan();
	}
	
	

	@Override
	public void onClick(ClickEvent event) 
	{
		this.createPlan();	
	}

	/**
	 * 
	 */
	private void createPlan()
	{
		String distance = Persistance.get(Persistance.TARGET_DISTANCE);
		String date = Persistance.get(Persistance.TARGET_DATE);
		String experience = Persistance.get(Persistance.EXPERIENCE_LEVEL);
		
		UserSettingsValidator validator = new UserSettingsValidator(); 
		boolean isValid = validator.validateUserSettings(distance, date, experience);
		
		/*
		 * Create Plan
		 */
		if (isValid)
		{
			DistancePanelManager.DISTANCE convertedDistance = DistancePanelManager.convertPreferenceStringToDistance(distance);
			SuperDateUtil runPlannerDate = SuperDateUtil.convertStringToDate(date);
			PlanGenerator gen = new PlanGenerator(convertedDistance, Integer.parseInt(experience),runPlannerDate);
			List<PlanItem> plan = gen.generatePlan();
			gen.savePlan(plan);
			_calanderManager.update(plan);
		}
	}
}