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

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;

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
	private Playlist _playlist;
	/**
	 * Constructor
	 * @param playlist
	 */
	public Audio(Playlist playlist)
	{
		_playlist = playlist;
	}
	
	public void playBackingTrack()
	{
		this.play(_playlist.getBackingTrack(0), true, 15);
	}
	
	/**
	 * 
	 * @param audio
	 */
	public void play(String audiotrack, final boolean loop, int volume)
	{
		SoundController soundController = new SoundController();
	    final Sound sound = soundController.createSound(Sound.MIME_TYPE_AUDIO_OGG_VORBIS,
	        audiotrack);
	    
//	    sound.setLooping(loop); // Currently does not work on Firefox.
	    sound.setVolume(volume);
	    
	    /*
	     * Manually enable looping...
	     */
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

	    sound.play();
	}
}
