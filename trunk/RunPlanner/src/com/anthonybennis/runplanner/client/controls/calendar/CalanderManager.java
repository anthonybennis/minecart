package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.Date;
import java.util.List;

import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.anthonybennis.runplanner.client.utils.SuperDateUtil;
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
	
	/**
	 * TODO Delete this method if not needed.
	 * 
	 * @param planItems
	 * @param cellDate
	 * @return
	 */
	private PlanItem getPlanItem(List<PlanItem> planItems, Date cellDate)
	{
		PlanItem planItemMatch = null;
		for (PlanItem planItem : planItems) 
		{
			boolean sameDate = SuperDateUtil.isSameDate(planItem.getDate(), cellDate);
			
			if (sameDate)
			{
				planItemMatch = planItem;
				break;
			}
		}
		
		return planItemMatch;
	}
}
