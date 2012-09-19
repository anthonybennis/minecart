package com.anthonybennis.runplanner.client;

import com.anthonybennis.runplanner.client.controls.AboutBox;
import com.anthonybennis.runplanner.client.controls.calendar.CalanderManager;
import com.anthonybennis.runplanner.client.handlers.CreatePlanClickHandler;
import com.anthonybennis.runplanner.client.handlers.ExperienceButtonHandler;
import com.anthonybennis.runplanner.client.storage.Persistance;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DecoratorPanel;
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
		ImageLoader.loadAllResourceImages(); // Load all images, to avoid dynamic load "flicker"
		
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
		headerPanel.setWidth("784px");
		headerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
		/*
		 * Advanced/Beginner Toggle Buttons.
		 */
		Label experienceLabel = new Label("Experience:");
		experienceLabel.setStylePrimaryName("smallWhiteText");
		Image beginnerImage = new Image(Resources.INSTANCE.getBeginnerButtonImage());
		Image beginnergGreyImage = new Image(Resources.INSTANCE.getBeginnerGreyButtonImage());
		Image intermediateImage = new Image(Resources.INSTANCE.getIntermediateButtonImage());
		Image intermediateGreyImage = new Image(Resources.INSTANCE.getIntermediateGreyButtonImage());
		
		ToggleButton beginnerButton = new ToggleButton(beginnergGreyImage, beginnerImage);
		beginnerButton.setStylePrimaryName("experienceButton");
		
		ToggleButton intermediateButton = new ToggleButton(intermediateGreyImage, intermediateImage);
		intermediateButton.setStylePrimaryName("experienceButton");
		
		beginnerButton.addClickHandler(new ExperienceButtonHandler(beginnerButton,intermediateButton));
		intermediateButton.addClickHandler(new ExperienceButtonHandler(beginnerButton,intermediateButton));
		
		String experienceLevel = Persistance.get(Persistance.EXPERIENCE_LEVEL);
		
		if (experienceLevel == null) // Set Default Experience level.
		{
			experienceLevel = "0";
			Persistance.store(Persistance.EXPERIENCE_LEVEL, experienceLevel); 
		}
		
		if (experienceLevel.equals("0"))
		{
			beginnerButton.setValue(true); 
			intermediateButton.setValue(false);
			
		}
		else
		{
			beginnerButton.setValue(false); 
			intermediateButton.setValue(true);
		}
		
		/*
		 * Header Logo
		 */
		Image runPlannerImage = new Image(Resources.INSTANCE.getTitleLogoImage());
		Image runPlannerDownImage = new Image(Resources.INSTANCE.getTitleLogoDownImage());
		PushButton headerButton = new PushButton(runPlannerImage,runPlannerDownImage);
		headerButton.setStylePrimaryName("headerLogoButton");
		headerButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Audio.playButtonClick();
				AboutBox aboutBox = new AboutBox();
				aboutBox.show();
			}
		});
		
		
		
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

		headerPanel.add(headerButton);
		headerPanel.setCellHorizontalAlignment(headerButton, HasHorizontalAlignment.ALIGN_CENTER);
		headerPanel.setCellVerticalAlignment(headerButton, HasVerticalAlignment.ALIGN_TOP);
		
		headerPanel.add(applyChangesButton);
		headerPanel.setCellHorizontalAlignment(applyChangesButton, HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.setCellVerticalAlignment(applyChangesButton, HasVerticalAlignment.ALIGN_TOP);
		
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
		 * Summary Button
		 */
		Image summaryImage = new Image(Resources.INSTANCE.getSummaryImage());
		PushButton pushButton = new PushButton(summaryImage);
		pushButton.setStylePrimaryName("summaryButton");
		pushButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			/*
			 * TODO Launch Summary Dialog
			 */
				Audio.playButtonClick();
//				DecoratorPanel planSummaryPanel = new DecoratorPanel();
//				planSummaryPanel.setTitle("Plan summary");
//				planSummaryPanel.setStylePrimaryName("smallWhiteText");
//				
//				Label startDateLabel = new Label("Start Date:");
//				startDateLabel.setStylePrimaryName("smallWhiteText");
//				Label raceDateLabel = new Label("End Date:");
//				raceDateLabel.setStylePrimaryName("smallWhiteText");
//				Label durationLabel = new Label("Plan duration Date:");
//				durationLabel.setStylePrimaryName("smallWhiteText");
//				
//				VerticalPanel summaryContentsPanel = new VerticalPanel();
//				summaryContentsPanel.add(startDateLabel);
//				summaryContentsPanel.add(raceDateLabel);
//				summaryContentsPanel.add(durationLabel);
//				planSummaryPanel.add(summaryContentsPanel);
//				mainPanel.setCellHorizontalAlignment(planSummaryPanel, HasHorizontalAlignment.ALIGN_CENTER);
//				mainPanel.add(planSummaryPanel);
				
			}
		});
		
		
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

