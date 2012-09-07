package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.ArrayList;
import java.util.List;

import com.anthonybennis.runplanner.client.Resources;
import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author abennis
 */
public class CalanderContainer 
{
	private DeckPanel _deckLayoutPanel;
	private List<MonthPanel> _monthPanels = new ArrayList<MonthPanel>();
	private Panel _introPanel;
	private HorizontalPanel _mainCalanderPanel;

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
		
		_mainCalanderPanel = new HorizontalPanel();
		_mainCalanderPanel.setSize("100%", "100%");
		
		/*
		 * Left Button
		 * TODO Add button handler
		 */
		Image leftbuttonIcon = new Image(Resources.INSTANCE.getLeftButtonImage());
		PushButton leftButtonPanel = new PushButton(leftbuttonIcon);
		_mainCalanderPanel.add(leftButtonPanel);
		leftbuttonIcon.setHeight("100%");
		_deckLayoutPanel = new DeckPanel();
		
		/*
		 * Intro Panel
		 */
		_introPanel = this.createIntroPanel();
		_mainCalanderPanel.add(_deckLayoutPanel);
		
		/*
		 * Calander
		 */
		_deckLayoutPanel.setSize("100%", "100%");
		_mainCalanderPanel.add(_deckLayoutPanel);
		
		/*
		 * Right Button
		 * TODO Add button handler
		 */
		Image buttonIcon = new Image(Resources.INSTANCE.getRightButtonImage());
		PushButton rightButtonPanel = new PushButton(buttonIcon);
		rightButtonPanel.setHeight("100%");
		_mainCalanderPanel.add(rightButtonPanel);
		
		mainPanel.add(_mainCalanderPanel);
		
		/*
		 * Footer
		 * TODO ENHANCEMENT Legend for Plan
		 */
		HorizontalPanel footerPanel = new HorizontalPanel(); 
		mainPanel.add(footerPanel);
		
		return mainPanel;
	}
	
	/**
	 * 
	 * @return
	 */
	private Panel createIntroPanel()
	{
		VerticalPanel introPanel = new VerticalPanel();
		
		/*
		 * TODO Add Intro Image
		 */
		
		return introPanel;
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
			/*
			 * Use insert as panels are added after creation.
			 */
			_deckLayoutPanel.insert(panel.createPanel(), 0); // TODO Check order is correct.
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
	@SuppressWarnings("deprecation")
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
		/*
		 * Clear DeckPanel (Calender Months)
		 */
		_deckLayoutPanel.clear();
		
		/*
		 * Clear (Remove) Intro Panel, if it exists.
		 */
		int introPanelIndex = _mainCalanderPanel.getWidgetIndex(_introPanel);
		
		if (introPanelIndex != -1)
		{
			_mainCalanderPanel.remove(_introPanel);
		}
		
		/*
		 * Clear ArrayList of Month Panels.
		 */
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