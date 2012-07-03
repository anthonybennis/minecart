package com.anthonybennis.runplanner.client.handlers;

import com.anthonybennis.runplanner.client.controls.DateChooser;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;

/**
 * 
 * @author abennis
 */
public class DatePickerHandler implements ClickHandler 
{
	private Canvas _panel;
	public DatePickerHandler(Canvas panel)
	{
		_panel = panel;
	}
	
	@Override
	public void onClick(ClickEvent event) 
	{
		/*
		 * Pop up Panel with custom Date Picker
		 */
		DateChooser chooser = new DateChooser(_panel);
		chooser.popup();
	}
}
