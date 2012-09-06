package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.ArrayList;
import java.util.List;

import com.anthonybennis.runplanner.client.controls.SlidingPanel;
import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author abennis
 */
public class CalanderContainer 
{
	private SlidingPanel _slidingPanel;
	private List<MonthPanel> _monthPanels = new ArrayList<MonthPanel>();

	/**
	 * Creates the main containers for the Calander widget.
	 * Does not contain any information, or calander entries.
	 * Call calanderContainer.update for that.
	 * 
	 * @return
	 */
	public Panel create()
	{
		/*
		 * Main Panel
		 */
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setWidth("100%");
		mainPanel.setHeight("100%");
		
		/*
		 * Calander
		 */
		HorizontalPanel mainCalanderPanel = new HorizontalPanel();
		mainPanel.add(mainCalanderPanel);
		
		/*
		 * Left Button
		 */
		VerticalPanel leftButtonPanel = new VerticalPanel();
		mainCalanderPanel.add(leftButtonPanel);
		
		_slidingPanel = new SlidingPanel();
		_slidingPanel.setSize("100%", "100%");

		mainCalanderPanel.add(_slidingPanel);
		
		/*
		 * Right Button
		 */
		VerticalPanel rightButtonPanel = new VerticalPanel();
		mainCalanderPanel.add(rightButtonPanel);
		
		/*
		 * Footer
		 * TODO ENHANCEMENT Legend for Plan
		 */
		HorizontalPanel footerPanel = new HorizontalPanel(); 
		mainPanel.add(footerPanel);
		
		return mainPanel;
	}
	
	/**
	 * Deletes any existing calender and creates a new one, with
	 * the PlanItems parameter.
	 * 
	 * @param planItems
	 */
	protected void update(List<PlanItem> planItems)
	{
		/*
		 * Delete any Existing plan.
		 */
		this.clear();
		
		/*
		 * Create the month panels.
		 */
		List<MonthPanel> monthPanels = this.createAndPopulateMonthPanels(planItems);
		
		/*
		 * Add all month panels to sliding panel
		 */
		for (MonthPanel panel : monthPanels) 
		{
			_slidingPanel.add(panel.createPanel());
		}
		
		/*
		 * TODO Display today on Calander if today is in Plan
		 * TODO If not, display first month of plan.
		 */
		_slidingPanel.show(0);
	}
	
	/**
	 * Creates MonthPanels and adds PlanItems to appropriate one.
	 * 
	 * TODO ENHANCEMENT Should we create PlanItems from TODAY to the START DATE?
	 * 
	 * @param planItems
	 * @return Panel[] all the Month Panels.
	 */
	private List<MonthPanel> createAndPopulateMonthPanels(List<PlanItem> planItems)
	{
		
		
		/*
		 * Create a cell for every Plan Item, and
		 * add cell to month panel.
		 * If no Month Panel, create one.
		 */
		for (PlanItem planItem : planItems) 
		{
			
		}
		
		return _monthPanels;
	}
	
	/**
	 * 
	 * @param planItem
	 * @return
	 */
	private MonthPanel addPlanItemToMonthPanel(PlanItem planItem)
	{
		MonthPanel monthPanel = null;
	
		/*
		 * Get Month Panel from the list of MonthPanels
		 */
		
		/*
		 * If none found for a given month, create one.
		 */
		
		/*
		 * Add PlanItem to month Panel.
		 */
		
		
		return monthPanel;
	}
	
	/**
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	private MonthPanel createMonthPanel(int month, int year)
	{
		MonthPanel monthPanel = new MonthPanel();
		
		// TODO Create Month Panel
		
		return monthPanel;
	}
	
	/**
	 * 
	 */
	private void clear()
	{
		_slidingPanel.clear();
		_monthPanels.clear();
	}
}