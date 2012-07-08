package com.anthonybennis.runplanner.client.handlers;

import com.anthonybennis.runplanner.client.IDateReciever;
import com.anthonybennis.runplanner.client.controls.DateChooser;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * 
 * @author abennis
 */
public class DatePickerHandler implements ClickHandler
{
	private IDateReciever _dateReciever;
	
	public DatePickerHandler(IDateReciever dateReciever)
	{
		_dateReciever = dateReciever;
	}
	
	@Override
	public void onClick(ClickEvent event) 
	{
		/*
		 * Pop up Panel with custom Date Picker
		 */
		DateChooser chooser = new DateChooser();
		chooser.popup(_dateReciever);
	}
}
