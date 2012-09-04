package com.anthonybennis.runplanner.client.controls;

import com.google.gwt.user.client.ui.DialogBox;

/**
 * This class launches pop up message dialogs.
 * @author abennis
 */
public class MessageBox 
{
	public MessageBox()
	{
		
	}
	
	public void open(String message)
	{
		DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		dialogBox.setAutoHideEnabled(true);
		dialogBox.setGlassEnabled(true);
		dialogBox.setModal(true);
		dialogBox.setPixelSize(600, 400);
		dialogBox.setTitle("Run Planner");
		dialogBox.setText(message);
		dialogBox.center();
		
		dialogBox.show();
	}
	
}
