package com.anthonybennis.runplanner.client.handlers;

import com.anthonybennis.runplanner.client.storage.Persistance;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ToggleButton;

/**
 * Experience button handler.
 * @author abennis
 */
public class ExperienceButtonHandler implements ClickHandler 
{
	ToggleButton _beginnerToggleButton;
	ToggleButton _intermediateToggleButton;
	
	public ExperienceButtonHandler(ToggleButton beginnerButton, ToggleButton intermediateButton)
	{
		_beginnerToggleButton = beginnerButton;
		_intermediateToggleButton = intermediateButton;
	}
	
	@Override
	public void onClick(ClickEvent event) 
	{
		
		
		if (_beginnerToggleButton == event.getSource())
		{
			_intermediateToggleButton.setValue(!_beginnerToggleButton.getValue());
		}
		else
		{
			_beginnerToggleButton.setValue(!_intermediateToggleButton.getValue());
		}
		
		String experienceLevel = null;
		
		if (_beginnerToggleButton.getValue())
		{
			experienceLevel = "0";
		}
		else
		{
			experienceLevel = "1";
		}
		
		Persistance.store(Persistance.EXPERIENCE_LEVEL, experienceLevel);
	}
}
