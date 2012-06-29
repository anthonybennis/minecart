package com.anthonybennis.runplanner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;

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
		MGWT.applySettings(MGWTSettings.getAppSetting());
		
		RootPanel rootPanel = RootPanel.get();
		RootPanel headerElement = RootPanel.get("Header");
		headerElement.getElement().getStyle().setPosition(Position.RELATIVE);
		/*
		 * Header
		 */
		FlowPanel mainPanel = new FlowPanel();
		mainPanel.getElement().getStyle().setBackgroundImage("BlackWoodTexture.jpg");
		/*
		 * Target Distance Panel
		 */
		DistancePanelManager distancePanelManager = new DistancePanelManager();
		Panel distanceButtonPanel = distancePanelManager.createDistancePanel();
		/*
		 * TODO AB Distance Unit (Imperial/Metric) Panel
		 */
		
		/*
		 * Target Date Panel
		 */
		DatePanelManager manager = new DatePanelManager();
		
		
		
		mainPanel.add(distanceButtonPanel);
		headerElement.add(mainPanel, 10, 10);
		mainPanel.setSize("649px", "513px");
	}
}
