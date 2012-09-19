package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.anthonybennis.runplanner.client.Resources;
import com.anthonybennis.runplanner.client.handlers.ButtonNavigationHandler;
import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.anthonybennis.runplanner.client.utils.MonthPanelSorter;
import com.anthonybennis.runplanner.client.utils.SuperDateUtil;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
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
	private Label _monthNameLabel;

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
		
		/*
		 * Header Panel
		 */
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.setWidth("100%");
		/*
		 * Left Button
		 */
		Image leftbuttonIcon = new Image(Resources.INSTANCE.getLeftButtonImage());
		Image leftbuttonDownIcon = new Image(Resources.INSTANCE.getLeftDownButtonImage());
		_leftButton = new PushButton(leftbuttonIcon, leftbuttonDownIcon);
		_leftButton.getElement().getStyle().setOpacity(0.5);
		_leftButton.setVisible(false);
		_leftButton.setStylePrimaryName("monthNavButton");
		headerPanel.add(_leftButton);
		headerPanel.setCellHorizontalAlignment(_leftButton, HasHorizontalAlignment.ALIGN_LEFT);
		/*
		 * Month name panel
		 */
		
		_monthNameLabel = this.createMonthNameLabel();
		headerPanel.setCellVerticalAlignment(_monthNameLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		headerPanel.add(_monthNameLabel);
		
		/*
		 * Right Button
		 */
		Image buttonIcon = new Image(Resources.INSTANCE.getRightButtonImage());
		Image rightbuttonDownIcon = new Image(Resources.INSTANCE.getRightDownButtonImage());
		_rightButton = new PushButton(buttonIcon,rightbuttonDownIcon);
		_rightButton.getElement().getStyle().setOpacity(0.5);
		_rightButton.setVisible(false);
		_rightButton.setStylePrimaryName("monthNavButton");
		headerPanel.add(_rightButton);
		headerPanel.setCellHorizontalAlignment(_rightButton, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		/*
		 * Deck panel (Month Panel container)
		 */
		_deckPanel = new DeckPanel();
		_deckPanel.setAnimationEnabled(false);
		
		/*
		 * Intro Panel
		 */
		_introImage = this.createIntroPanel();
		_mainCalanderPanel.add(_introImage);
		
		/*
		 * Calander
		 */
		_mainCalanderPanel.add(_deckPanel);
		
		/*
		 * Footer
		 */
		Panel footerPanel = this.createFooterPanel();
		
		mainPanel.add(headerPanel);
		mainPanel.add(_mainCalanderPanel);
		mainPanel.add(footerPanel);
		
		/*
		 * Add Listeners, after all widgets have been created.
		 */
	
		 // TODO Do I need to add Touch Listener too?
		_leftButton.addClickHandler(new ButtonNavigationHandler(_deckPanel, _monthPanels,false));
		_rightButton.addClickHandler(new ButtonNavigationHandler(_deckPanel, _monthPanels,true));
		
		return mainPanel;
	}
	
	private Label createMonthNameLabel()
	{
		_monthNameLabel = new Label("                ");
		_monthNameLabel.setStylePrimaryName("monthPanelHeader");
		_monthNameLabel.getElement().setAttribute("align", "center");
		
		return _monthNameLabel;
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
			/*
			 * Use insert as panels are added after creation.
			 */
			monthPanelWidget = panel.createPanel(_monthNameLabel);
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
		 * Update Month Panel Name with first month
		 */
		if (monthPanels != null && monthPanels.size() > 0)
		{
			monthPanels.get(0).updateMonthNameLabel();
		}
		
		/*
		 * Display today on Calendar if today is in Plan
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
		 * Footer info
		 */
		if (planItems != null && planItems.size() > 0)
		{
			RunPlannerDate startDate = planItems.get(0).getDate();
			String startMonthName = SuperDateUtil.getMonthName(startDate.getMonth());
			int userReadableYear = startDate.getYear() + 1900;
			String footerDescription = "Plan starts on " + startDate.getDate() + " " + startMonthName + ", " + userReadableYear;
			_detailsLabel.setText(footerDescription);
		}
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
			
			/*
			 * TODO ENHANCEMENT: Show PlanItem in greyed out Cells too.
			 */
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
		System.err.println("Create month panel for: " + month + "," + year);
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
		VerticalPanel footer = new VerticalPanel();
		footer.setWidth("100%");
		
		/*
		 * TODO ENHANCEMENT Add progress line, showing start date and end date and current date, if on line
		 * See: PlanProgressIndicator
		 * 
		 * TODO Move Plan details to "About Box"
		 */
		_detailsLabel = new Label();
		_detailsLabel.setStylePrimaryName("smallWhiteText");
		/*
		 * Add icons legend
		 */
		HorizontalPanel iconLegends = new HorizontalPanel();
		
		Label comfortableLabel = new Label("Comfortable pace:");
		comfortableLabel.setStylePrimaryName("smallWhiteText");
		Image confortableImage = CalendarImage.createComfortablePACEImage();
		Label fastLabel = new Label("Fast pace:");
		fastLabel.setStylePrimaryName("smallWhiteText");
		Image fast = CalendarImage.createFastPACEImage();
		Label mixLabel = new Label("Mixed pace:");
		mixLabel.setStylePrimaryName("smallWhiteText");
		Image mix = CalendarImage.createMixPACEImage();
		Label restLabel = new Label("Rest day:");
		restLabel.setStylePrimaryName("smallWhiteText");
		Image rest = CalendarImage.createRestPACEImage();
		Label slowLabel = new Label("slow pace:");
		slowLabel.setStylePrimaryName("smallWhiteText");
		Image slow = CalendarImage.createSlowPACEImage();
		
		iconLegends.add(slowLabel);
		iconLegends.add(slow);
		iconLegends.add(new Label("    "));
		iconLegends.add(comfortableLabel);
		iconLegends.add(confortableImage);
		iconLegends.add(new Label("    "));
		iconLegends.add(fastLabel);
		iconLegends.add(fast);
		iconLegends.add(new Label("    "));
		iconLegends.add(mixLabel);
		iconLegends.add(mix);
		iconLegends.add(new Label("    "));
		iconLegends.add(restLabel);
		iconLegends.add(rest);
		
		
		
		footer.add(iconLegends);
		
		/*
		 * TODO ENHANCEMENT Add webOS Calender format buttons (List, or calender).
		 */
		footer.setCellHorizontalAlignment(iconLegends, HasHorizontalAlignment.ALIGN_CENTER);
//		footer.setCellHorizontalAlignment(_detailsLabel, HasHorizontalAlignment.ALIGN_CENTER);
		
		return footer;
	}
}