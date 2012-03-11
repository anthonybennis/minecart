package com.bennis.minecart.client.engine.logic;

import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Generic class for wrapping user input events
 * @author abennis
 *
 */
public class InputEvent 
{
	/*
	 * Use enum names for event IDs.
	 */
	private String _eventID;
	private ClickEvent _event;
	private boolean _consumed = false;
	
	public InputEvent(ClickEvent event, String eventID)
	{
		_eventID = eventID;
		_event = event; 
	}
	
	public String getEventID()
	{
		return _eventID;
	}
	
	public void setEventID(String eventID)
	{
		_eventID = eventID;
	}
	
	public ClickEvent getClickEvent()
	{
		return _event;
	}
	
	public void consume()
	{
		_consumed = true;
	}
	
	public boolean isConsumed()
	{
		return _consumed;
	}
}
