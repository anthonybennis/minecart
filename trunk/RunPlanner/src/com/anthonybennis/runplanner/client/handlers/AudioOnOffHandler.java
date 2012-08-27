package com.anthonybennis.runplanner.client.handlers;

import com.anthonybennis.runplanner.client.storage.Persistance;

/**
 * Toggles Audio On/Off
 * @author Anthony
 */
public class AudioOnOffHandler 
{
	private final static String ON ="on";
	private final static String OFF ="off";
	
	public static void toggleAudio(boolean toggleOff)
	{
		if (toggleOff)
		{
			/*
			 * Toggle Off
			 */
			Persistance.store(Persistance.AUDIO, OFF);
		}
		else
		{
			/*
			 * Toggle On
			 */
			Persistance.store(Persistance.AUDIO, ON);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static boolean isAudioOn()
	{
		boolean audioOn = true;
		
		String currentAudioSetting = Persistance.get(Persistance.AUDIO);
		
		if (currentAudioSetting != null)
		{
			audioOn = (currentAudioSetting.equals(ON));
		}
			
		
		return audioOn;
	}
}
