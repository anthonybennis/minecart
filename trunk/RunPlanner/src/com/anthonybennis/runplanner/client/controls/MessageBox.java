package com.anthonybennis.runplanner.client.controls;

import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * This class launches pop up message dialogs.
 * @author abennis
 */
public class MessageBox 
{
	public MessageBox()
	{
		
	}
	
	/*
	 * TODO This message box does not have a back ground!
	 */
	public void open(String message)
	{
		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
	    simplePopup.setWidth("150px");
	    simplePopup.setWidget(new Label(message));
	    simplePopup.center();
	    simplePopup.show();
	}
	
}
