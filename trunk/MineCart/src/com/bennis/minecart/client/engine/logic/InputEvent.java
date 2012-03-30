/*******************************************************************************
 * Copyright (c) 2012 Anthony Bennis
 * All rights reserved. This program and the accompanying materials
 * are fully owned by Anthony Bennis and cannot be used for commercial projects without prior permission from same.
 * http://www.anthonybennis.com
 *
 * Contributors:
 * Anthony Bennis.
 *******************************************************************************/
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
