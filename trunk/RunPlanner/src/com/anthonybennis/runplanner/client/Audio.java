/*******************************************************************************
 * Copyright (c) 2012 Anthony Bennis
 * All rights reserved. This program and the accompanying materials
 * are fully owned by Anthony Bennis and cannot be used for commercial projects without prior permission from same.
 * http://www.anthonybennis.com
 *
 * Contributors:
 * Anthony Bennis.
 *******************************************************************************/
package com.anthonybennis.runplanner.client;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import com.anthonybennis.runplanner.client.handlers.AudioOnOffHandler;

/**
 * This class is responsible for playing audio.
 * Uses GWT Voices.
 * 
 * We use GWT Voices for more control over our audio, such as looping.
 * 
 * @author abennis
 */
public class Audio 
{
	private static SoundController _soundController = new SoundController();
	private static Sound _buttonSound = _soundController.createSound(Sound.MIME_TYPE_AUDIO_OGG_VORBIS,
	        "audio/ButtonClick1.ogg");

	/**
	 * Constructor
	 * @param playlist
	 */
	public Audio()
	{
	}
	
	public static void playButtonClick()
	{
		if (AudioOnOffHandler.isAudioOn())
		{
//			_buttonSound.play();
			Audio.play(_buttonSound, false, 8);
		}
	}
	
	public static void playButtonClickSilently()
	{
		Audio.play(_buttonSound, false, 0);
	}
	
	/**
	 * 
	 * @param audio
	 */
	public static void play(final Sound sound, final boolean loop, int volume)
	{
		if (AudioOnOffHandler.isAudioOn())
		{
	//	    sound.setLooping(loop); // Currently does not work on Firefox.
			sound.setVolume(volume);
		    
		    /*
		     * Manually enable looping...
		     */
			if (loop)
			{
				sound.addEventHandler(new SoundHandler() {
					
				@Override
				public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
					// Nothing to do here.
				}
				
				@Override
				public void onPlaybackComplete(PlaybackCompleteEvent event) {
					if (loop)
					{
						sound.play();
					}
				}
				});
			}
			sound.play();
		}
	}
}
