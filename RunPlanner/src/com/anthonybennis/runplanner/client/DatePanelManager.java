package com.anthonybennis.runplanner.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author abennis
 */
public class DatePanelManager 
{
	
	private Panel _dateViewPanel;
	private Panel _datePickerPanel;

	public DatePanelManager()
	{
		VerticalPanel containerPanel = this.createDatePanel();
//		 _dateViewPanel = this.createDateViewerPanel();
//		 _datePickerPanel = this.createDatePickerPanel();
//		 
//		 containerPanel.add(_dateViewPanel);
	}
	
	public VerticalPanel createDatePanel()
	{
		VerticalPanel datePanelContainer = new VerticalPanel();
		Button testButton = new Button("Date stuff goes here");
		datePanelContainer.add(testButton);
//		datePanelContainer.getElement().getStyle().setBackgroundImage("BlackWoodTexture.jpg");
		
		return datePanelContainer;
	}
	
	private Panel createDateViewerPanel()
	{
		return null; // TODO AB
	}
	
	private Panel createDatePickerPanel()
	{
		return null; // TODO AB
	}
}
