package com.anthonybennis.runplanner.client;

import com.anthonybennis.runplanner.client.controls.calendar.Calander;
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
		FlowPanel mainPanel = new FlowPanel();
		mainPanel.setSize("100%", "100%");
		mainPanel.getElement().getStyle().setBackgroundImage("images/BlackWoodTexture.jpg");
		/*
		 * Header Panel
		 */
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.setSpacing(10);
		headerPanel.setWidth("98%");
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		/*
		 * Audio toggle button.
		 * TODO Consider making this "Beginner/Intermediate" option.
		 */
		Image audio = new Image(Resources.INSTANCE.getAudioButton());
		Image noAudio = new Image(Resources.INSTANCE.getNoAudioButton());
		
		final ToggleButton audioToggleButton = new ToggleButton(audio,noAudio);
		audioToggleButton.setStylePrimaryName("audioButton");
		audioToggleButton.setSize("64px", "64px");
		audioToggleButton.setTitle("Audio");

		audioToggleButton.addTouchEndHandler(new TouchEndHandler() {
			
			@Override
			public void onTouchEnd(TouchEndEvent arg0) {
				AudioOnOffHandler.toggleAudio(audioToggleButton.getValue());
				Audio.playButtonClick();
			}
		});
		
		audioToggleButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) 
			{
				PlanGenerator.main(null);
				AudioOnOffHandler.toggleAudio(audioToggleButton.getValue());
				Audio.playButtonClick();
			}
		});
		
		if (!AudioOnOffHandler.isAudioOn())
		{
			audioToggleButton.setValue(true);
		}
		
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
		
		
		headerPanel.add(audioToggleButton);
		headerPanel.setCellVerticalAlignment(audioToggleButton, HasVerticalAlignment.ALIGN_MIDDLE);
		headerPanel.add(runPlannerImage);
		headerPanel.add(applyChangesPanel);
		headerPanel.setCellVerticalAlignment(applyChangesPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		
		headerPanel.setCellHorizontalAlignment(audioToggleButton, HasHorizontalAlignment.ALIGN_LEFT);
		headerPanel.setCellHorizontalAlignment(applyChangesPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.setCellHorizontalAlignment(runPlannerImage, HasHorizontalAlignment.ALIGN_CENTER);
		
		/*
		 * Date/Disatnce and Calander container
		 */
		HorizontalPanel dateDistanceCalanderPanel = new HorizontalPanel();
		
		/*
		 * Date and Distance container
		 */
		VerticalPanel distanceAndDateContainer = new VerticalPanel();
		distanceAndDateContainer.setSpacing(3);
		distanceAndDateContainer.setHeight("100%");
		
		
		/*
		 * Target Distance Panel
		 */
		DistancePanelManager distancePanelManager = new DistancePanelManager();
		Panel distanceButtonPanel = distancePanelManager.createDistancePanel();
		
		/*
		 * Target Date Panel
		 */
		DatePanelManager manager = new DatePanelManager();
		Canvas datePanel = manager.createCanvas();
		
		Panel calanderPanel = _calanderManager.createCalenderContainer();
		calanderPanel.getElement().setAttribute("align", "center");
		
		distanceAndDateContainer.add(distanceButtonPanel);
		distanceAndDateContainer.add(datePanel);
		dateDistanceCalanderPanel.add(distanceAndDateContainer);
		dateDistanceCalanderPanel.add(calanderPanel);
		
		mainPanel.add(headerPanel);
		mainPanel.add(dateDistanceCalanderPanel);
		
		headerElement.add(mainPanel, 10, 10);
	}
}

