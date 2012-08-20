package com.anthonybennis.runplanner.client.handlers;

import com.anthonybennis.runplanner.client.Resources;
import com.anthonybennis.runplanner.client.storage.Persistance;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

/**
 * Toggles Audio On/Off
 * @author Anthony
 */
public class AudioOnOffHandler 
{
	private Image _audioButton;
	private final String ON ="on";
	private final String OFF ="off";
	
	public AudioOnOffHandler(Image audioButton)
	{
		_audioButton = audioButton;
	}
	
	
	public void toggleAudio()
	{
		String currentAudioSetting = Persistance.get(Persistance.AUDIO);
		
		if (currentAudioSetting == null)
		{
			currentAudioSetting = ON;
		}
	
		if (currentAudioSetting.equals(ON))
		{
			/*
			 * Toggle Off
			 */
			Persistance.store(Persistance.AUDIO, OFF);
			_audioButton.setUrl(Resources.INSTANCE.getNoAudioButton().getURL());
		}
		else
		{
			/*
			 * Toggle On
			 */
			Persistance.store(Persistance.AUDIO, ON);
			_audioButton.setUrl(Resources.INSTANCE.getAudioButton().getURL());
		}
	}

}
