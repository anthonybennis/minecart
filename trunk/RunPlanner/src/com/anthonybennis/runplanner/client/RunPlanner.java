package com.anthonybennis.runplanner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

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
		RootPanel rootPanel = RootPanel.get();
		RootPanel headerElement = RootPanel.get("Header");	
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
		 * TODO AB Target Date Panel
		 * 
		 */
		mainPanel.add(distanceButtonPanel);
		headerElement.add(mainPanel, 10, 10);
		mainPanel.setSize("649px", "513px");
	}
}
