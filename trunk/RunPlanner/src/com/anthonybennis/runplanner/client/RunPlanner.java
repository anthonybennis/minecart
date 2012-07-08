package com.anthonybennis.runplanner.client;

import com.anthonybennis.runplanner.client.handlers.CloseHandler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RunPlanner implements EntryPoint 
{
	public final static ImageLoader IMAGELOADER = new ImageLoader();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		IMAGELOADER.loadAllImages();
		Audio.playButtonClick(); // Load into memory.
		
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
		
		Image settingsButton = new Image("images/Settings.png");
		settingsButton.setTitle("Settings");
		
		Image runPlannerImage = new Image("images/RunPlannerGold.png");
		
		Image closeButton = new Image("images/Close.png");
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
		HorizontalPanel calanderPanel = new HorizontalPanel();
		Button button = new Button("Calander goes here...");
		calanderPanel.add(button);
		
		distanceAndDateContainer.add(distanceButtonPanel);
		distanceAndDateContainer.add(datePanel);
		
		dateDistanceCalanderPanel.add(distanceAndDateContainer);
		dateDistanceCalanderPanel.add(calanderPanel);
		
		mainPanel.add(headerPanel);
		mainPanel.add(dateDistanceCalanderPanel);
		
		headerElement.add(mainPanel, 10, 10);
	}
}

