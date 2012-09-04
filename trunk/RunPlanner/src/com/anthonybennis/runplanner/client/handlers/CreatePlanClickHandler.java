package com.anthonybennis.runplanner.client.handlers;

import java.util.List;

import com.anthonybennis.runplanner.client.logic.PlanGenerator;
import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.anthonybennis.runplanner.client.logic.UserSettingsValidator;
import com.anthonybennis.runplanner.client.storage.Persistance;
import com.anthonybennis.runplanner.client.utils.RunPlannerDate;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;

/**
 * Creates Plan from user selected options
 * @author Anthony
 *
 */
public class CreatePlanClickHandler implements TouchEndHandler 
{
	@Override
	public void onTouchEnd(TouchEndEvent event) 
	{
		this.createPlan();
	}

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
			RunPlannerDate runPlannerDate = new RunPlannerDate();
			runPlannerDate.setDay(1);
			runPlannerDate.setMonth(9);
			runPlannerDate.setYear(2012);
			
			PlanGenerator gen = new PlanGenerator(runPlannerDate);
			List<PlanItem> plan = gen.generatePlan(5, 0);
			gen.savePlan(plan);
			
			/*
			 * TODO Create Calender!
			 */
		}
	}
	
	
}
