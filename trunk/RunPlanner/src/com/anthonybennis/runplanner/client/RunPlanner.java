package com.anthonybennis.runplanner.client;

import com.anthonybennis.runplanner.client.controls.calendar.CalanderManager;
import com.anthonybennis.runplanner.client.handlers.AudioOnOffHandler;
import com.anthonybennis.runplanner.client.handlers.CreatePlanClickHandler;
import com.anthonybennis.runplanner.client.logic.PlanGenerator;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RunPlanner implements EntryPoint 
{
	private CalanderManager _calanderManager;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		Audio.playButtonClickSilently(); // Load into memory.
		
		/*
		 * ROOT
		 */
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");
		RootPanel headerElement = RootPanel.get("Header");
		headerElement.getElement().getStyle().setPosition(Position.RELATIVE);
		
		/*
		 * Main container
		 */
		Panel mainPanel = new HorizontalPanel();
		mainPanel.setSize("100%", "100%");

		/*
		 * Header Panel
		 */
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.setSpacing(10);
		headerPanel.setWidth("98%");
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		/*
		 * Advanced/Beginner Toggle Buttons.
		 */
		Label experienceLabel = new Label("Experience:");
		experienceLabel.setStylePrimaryName("smallWhiteText");
		Image beginnerImage = new Image(Resources.INSTANCE.getBeginnerButtonImage());
		Image beginnergGreyImage = new Image(Resources.INSTANCE.getBeginnerGreyButtonImage());
		Image intermediateImage = new Image(Resources.INSTANCE.getIntermediateButtonImage());
		Image intermediateGreyImage = new Image(Resources.INSTANCE.getIntermediateGreyButtonImage());
		
		ToggleButton beginnerButton = new ToggleButton(beginnerImage, beginnergGreyImage);
		beginnerButton.setStylePrimaryName("experienceButton");
		beginnerButton.setValue(true); // TODO Load experience value from store.
		
		ToggleButton intermediateButton = new ToggleButton(intermediateImage, intermediateGreyImage);
		intermediateButton.setStylePrimaryName("experienceButton");
		
		/*
		 * Heaer Logo
		 */
		Image runPlannerImage = new Image(Resources.INSTANCE.getTitleLogoImage());
		_calanderManager = new CalanderManager();
		/*
		 * Apply Changes Button
		 */
		Image buttonIcon = new Image(Resources.INSTANCE.getCreatePlanButtonImage());
		Image buttonDownIcon = new Image(Resources.INSTANCE.getCreatePlanDownButtonImage());
		PushButton applyChangesButton = new PushButton(buttonIcon,buttonDownIcon);
		applyChangesButton.setWidth("100");
		applyChangesButton.setHeight("102");
		applyChangesButton.setStylePrimaryName("largeTextButton");
		applyChangesButton.addTouchEndHandler(new CreatePlanClickHandler(_calanderManager));
		applyChangesButton.addClickHandler(new CreatePlanClickHandler(_calanderManager));
		applyChangesButton.getElement().setAttribute("align", "center");
		Label createPlanLabel = new Label("Click here to create Plan");
		createPlanLabel.setStylePrimaryName("smallWhiteText");
		VerticalPanel applyChangesPanel = new VerticalPanel();
		applyChangesPanel.add(applyChangesButton);
		applyChangesPanel.add(createPlanLabel);
		
		headerPanel.add(runPlannerImage);
		headerPanel.add(applyChangesPanel);
		headerPanel.setCellVerticalAlignment(applyChangesPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		headerPanel.setCellHorizontalAlignment(applyChangesPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.setCellHorizontalAlignment(runPlannerImage, HasHorizontalAlignment.ALIGN_CENTER);
		
		/*
		 * Date and Distance container
		 */
		VerticalPanel distanceAndDateContainer = new VerticalPanel();
		
		/*
		 * Target Distance Panel
		 */
		Label distanceLabel = new Label("Distance:");
		distanceLabel.setStylePrimaryName("smallWhiteText");
		DistancePanelManager distancePanelManager = new DistancePanelManager();
		Panel distanceButtonPanel = distancePanelManager.createDistancePanel();
		
		/*
		 * Target Date Panel
		 */
		DatePanelManager manager = new DatePanelManager();
		Canvas datePanel = manager.createCanvas();
		Label dateLabel = new Label("Race Date:");
		dateLabel.setStylePrimaryName("smallWhiteText");
		
		/*
		 * Calendar Panel
		 */
		Panel calanderPanel = _calanderManager.createCalenderContainer();
		
		/*
		 * Add Header and Calendar panels to Vertical Panel
		 */
		Panel headerAndCalanderPanel = new VerticalPanel();
		headerAndCalanderPanel.add(headerPanel);
		headerAndCalanderPanel.add(calanderPanel);
		
		/*
		 * Experience, Distance Buttons and Date Buttons
		 * in Vertical Panel.
		 */
		distanceAndDateContainer.add(experienceLabel);
		distanceAndDateContainer.add(beginnerButton);
		distanceAndDateContainer.add(intermediateButton);
		distanceAndDateContainer.add(distanceLabel);
		distanceAndDateContainer.add(distanceButtonPanel);
		distanceAndDateContainer.add(dateLabel);
		distanceAndDateContainer.add(datePanel);
		distanceAndDateContainer.setCellVerticalAlignment(datePanel, HasVerticalAlignment.ALIGN_TOP);
		
		
		mainPanel.add(distanceAndDateContainer);
		mainPanel.add(headerAndCalanderPanel);
		
		headerElement.add(mainPanel, 10, 10);
	}
}

