package com.bennis.minecart.client.engine.logic;


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
	private boolean _consumed = false;
	
	public InputEvent(String eventID)
	{
		_eventID = eventID;
	}
	
	public String getEventID()
	{
		return _eventID;
	}
	
	public void setEventID(String eventID)
	{
		_eventID = eventID;
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
