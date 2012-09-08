package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.List;

import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.google.gwt.user.client.ui.Panel;

/**
 * Creates calender control and manges it.
 * @author Anthony
 *
 */
public class CalanderManager 
{
	private CalanderContainer _mainContainer;

	/**
	 * 
	 * @return
	 */
	public Panel createCalenderContainer()
	{
		_mainContainer = new CalanderContainer();
		return _mainContainer.create();
	}
	
	/**
	 * 
	 * @param planItems
	 */
	public void update(List<PlanItem> planItems)
	{
		_mainContainer.update(planItems);
	}
}
