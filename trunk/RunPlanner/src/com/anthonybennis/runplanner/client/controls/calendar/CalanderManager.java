package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.List;

import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Creates calender control and manges it.
 * @author Anthony
 *
 */
public class CalanderManager 
{
	/**
	 * 
	 * @return
	 */
	public Panel createCalenderContainer()
	{
		VerticalPanel mainContainer = new VerticalPanel();
		mainContainer.setWidth("100%");
		mainContainer.setHeight("100%");
		
		return mainContainer;
	}
	
	/**
	 * 
	 * @param planItems
	 */
	public void update(List<PlanItem> planItems)
	{
		
	}
}
