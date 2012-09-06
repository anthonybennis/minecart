package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.ArrayList;
import java.util.List;

import com.anthonybennis.runplanner.client.controls.SlidingPanel;
import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author abennis
 */
public class CalanderContainer 
{
	private DeckLayoutPanel _deckLayoutPanel;
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
		
		_deckLayoutPanel = new DeckLayoutPanel();
		
		
		/*
		 * TODO We need an intro panel, when there's no Plan to show.
		 */
		Panel introPanel = new HorizontalPanel();
		_deckLayoutPanel.add(introPanel);

		mainCalanderPanel.add(_deckLayoutPanel);
		
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
			_deckLayoutPanel.add(panel.createPanel()); // TODO Ensure this is done in right order.
		}
		
		/*
		 * TODO Display today on Calander if today is in Plan
		 * TODO If not, display first month of plan.
		 */
		_deckLayoutPanel.showWidget(0);
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
		MonthPanel monthPanel;
		int month;
		int year;
		for (PlanItem planItem : planItems) 
		{
			month = planItem.getDate().getMonth();
			year = planItem.getDate().getYear();
			
			monthPanel = this.getMonthPanel(month, year);
			
			if (monthPanel == null)
			{
				monthPanel = this.createMonthPanel(month, year);
				_monthPanels.add(monthPanel);
			}
			
			monthPanel.addPlanItem(planItem);
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
		MonthPanel monthPanel = new MonthPanel(month, year);
		
		return monthPanel;
	}
	
	/**
	 * 
	 */
	private void clear()
	{
		// TODO Remove all widgets from _deckLayoutPanel
		_monthPanels.clear();
	}
	
	/**
	 * 
	 * @param planItem
	 * @return
	 */
	private MonthPanel getMonthPanel(int month, int year)
	{
		MonthPanel monthPanel = null;
		
		for (MonthPanel amonthPanel : _monthPanels) 
		{
			if (amonthPanel.match(month, year))
			{
				monthPanel = amonthPanel;
				break;
			}
		}
		
		return monthPanel;
	}
}