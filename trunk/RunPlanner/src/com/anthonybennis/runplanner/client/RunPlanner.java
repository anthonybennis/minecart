package com.anthonybennis.runplanner.client;

import com.anthonybennis.runplanner.client.controls.calendar.Calander;
import com.anthonybennis.runplanner.client.handlers.CloseHandler;
import com.anthonybennis.runplanner.client.handlers.CreatePlanClickHandler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RunPlanner implements EntryPoint 
{
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		Audio.playButtonClick(); // Load into memory.
		
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
		 * TODO AB - Put in own class.
		 */
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.setSpacing(10);
		headerPanel.setWidth("98%");
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		Image settingsButton = new Image(Resources.INSTANCE.getSettingsImage());
		settingsButton.setTitle("Settings");
		
		Image runPlannerImage = new Image(Resources.INSTANCE.getTitleLogoImage());
		
		Image closeButton = new Image(Resources.INSTANCE.getCloseAppButtonImage());
		closeButton.setTitle("Close app");
		closeButton.addClickHandler(new CloseHandler());
		
		headerPanel.add(settingsButton);
		headerPanel.setCellVerticalAlignment(settingsButton, HasVerticalAlignment.ALIGN_MIDDLE);
		headerPanel.add(runPlannerImage);
		headerPanel.add(closeButton);
		headerPanel.setCellVerticalAlignment(closeButton, HasVerticalAlignment.ALIGN_MIDDLE);
		
		headerPanel.setCellHorizontalAlignment(settingsButton, HasHorizontalAlignment.ALIGN_LEFT);
		headerPanel.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.setCellHorizontalAlignment(runPlannerImage, HasHorizontalAlignment.ALIGN_CENTER);
		
		/*
		 * Date/Disatnce and Calander container
		 */
		HorizontalPanel dateDistanceCalanderPanel = new HorizontalPanel();
		
		/*
		 * Date and Distance container
		 */
		VerticalPanel distanceAndDateContainer = new VerticalPanel();
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
		
		/*
		 * Calander Panel
		 */
		Calander calander = new Calander();
		Panel calanderPanel = calander.createCalander();
		
		/*
		 * Apply Changes Button
		 */
		Image buttonIcon = new Image(Resources.INSTANCE.getCreatePlanButtonImage());
		Image buttonDownIcon = new Image(Resources.INSTANCE.getCreatePlanDownButtonImage());
		PushButton applyChangesButton = new PushButton(buttonIcon,buttonDownIcon);
		applyChangesButton.setWidth("128");
		applyChangesButton.setHeight("153");
		applyChangesButton.setStylePrimaryName("largeTextButton");
		applyChangesButton.addTouchEndHandler(new CreatePlanClickHandler());
		applyChangesButton.getElement().setAttribute("align", "center");
		
		distanceAndDateContainer.add(distanceButtonPanel);
		distanceAndDateContainer.add(datePanel);
		distanceAndDateContainer.add(applyChangesButton);
		
		dateDistanceCalanderPanel.add(distanceAndDateContainer);
		dateDistanceCalanderPanel.add(calanderPanel);
		
		mainPanel.add(headerPanel);
		mainPanel.add(dateDistanceCalanderPanel);
		
		headerElement.add(mainPanel, 10, 10);
	}
}

