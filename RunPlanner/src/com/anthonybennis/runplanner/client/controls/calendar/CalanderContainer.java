package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.anthonybennis.runplanner.client.Resources;
import com.anthonybennis.runplanner.client.handlers.ButtonNavigationHandler;
import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.anthonybennis.runplanner.client.utils.MonthPanelSorter;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author abennis
 */
public class CalanderContainer 
{
	private DeckPanel _deckPanel;
	private List<MonthPanel> _monthPanels = new ArrayList<MonthPanel>();
	private Image _introImage;
	private HorizontalPanel _mainCalanderPanel;
	private PushButton _leftButton;
	private PushButton _rightButton;
	private Label _detailsLabel;

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
		_leftButton = new PushButton(leftbuttonIcon);
		_leftButton.setHeight("500px");
		_leftButton.getElement().getStyle().setOpacity(0.5);
		_leftButton.setVisible(false);
		_mainCalanderPanel.add(_leftButton);
		_deckPanel = new DeckPanel();
		
		/*
		 * Intro Panel
		 * TODO Add Health Warning
		 */
		_introImage = this.createIntroPanel();
		_mainCalanderPanel.add(_introImage);
		
		/*
		 * Calander
		 */
		_deckPanel.setSize("100%", "100%");
		_mainCalanderPanel.add(_deckPanel);
		
		/*
		 * Right Button
		 * TODO Add button handler
		 */
		Image buttonIcon = new Image(Resources.INSTANCE.getRightButtonImage());
		_rightButton = new PushButton(buttonIcon);
		_rightButton.setHeight("100%");
		_rightButton.setHeight("500px");
		_rightButton.getElement().getStyle().setOpacity(0.5);
		_rightButton.setVisible(false);
		_mainCalanderPanel.add(_rightButton);
		
		mainPanel.add(_mainCalanderPanel);
		
		/*
		 * Footer
		 */
		Panel footerPanel = this.createFooterPanel(); 
		mainPanel.add(footerPanel);
		
		/*
		 * Add Listeners, after all widgets have been created.
		 */
	
		 // TODO Do I need to add Touch Listener too?
		_leftButton.addClickHandler(new ButtonNavigationHandler(_deckPanel, false, _leftButton, _rightButton));
		_rightButton.addClickHandler(new ButtonNavigationHandler(_deckPanel, true, _leftButton, _rightButton));
		
		return mainPanel;
	}
	
	/**
	 * 
	 * @return
	 */
	private Image createIntroPanel()
	{
		Image introImage = new Image(Resources.INSTANCE.getIntroImage());
		return introImage;
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
		 * Make Nav buttons visible
		 */
		_leftButton.setVisible(true);
		_rightButton.setVisible(true);
		
		
		/*
		 * Create the month panels.
		 */
		List<MonthPanel> monthPanels = this.createAndPopulateMonthPanels(planItems);
		
		
		/*
		 * Order Month Panels
		 */
		Collections.sort(monthPanels, new MonthPanelSorter());
		
		/*
		 * Add all month panels to sliding panel
		 */
		Panel todaysPanel = null;
		Panel monthPanelWidget;
		for (MonthPanel panel : monthPanels) 
		{
			System.out.println("Creating panel: " + panel.getMonth() + "," + panel.getYear());
			/*
			 * Use insert as panels are added after creation.
			 */
			monthPanelWidget = panel.createPanel();
			_deckPanel.insert(monthPanelWidget, _deckPanel.getWidgetCount());
			
			/*
			 * Get reference to Today's Month.
			 */
			if (panel.doesTodayFallOnThisMonth())
			{
				todaysPanel = monthPanelWidget;
			}
		}
		
		/*
		 * Display today on Calander if today is in Plan
		 * If not, display first month of plan.
		 */
		int indexOfMonthToShowByDefault = 0;
		
		if (_deckPanel.getWidgetCount() > 0)
		{
			if (todaysPanel != null)
			{
				indexOfMonthToShowByDefault = _deckPanel.getWidgetIndex(todaysPanel);
			}
			
			_deckPanel.showWidget(indexOfMonthToShowByDefault);
		}
		
		/*
		 * Footer info/ 
		 */
		_detailsLabel.setText("Training plan starts on the " + planItems.get(planItems.size() - 1).getDate().toString());
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
			System.out.println("Creating panel for " + month + ", " + year);
			
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
		_deckPanel.clear();
		
		/*
		 * Clear (Remove) Intro Panel, if it exists.
		 */
		int introPanelIndex = _mainCalanderPanel.getWidgetIndex(_introImage);
		
		if (introPanelIndex != -1)
		{
			_mainCalanderPanel.remove(_introImage);
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
	
	private Panel createFooterPanel()
	{
		Panel footer = new HorizontalPanel();
		
		_detailsLabel = new Label();
		
		
		return footer;
	}
}