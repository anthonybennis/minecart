package com.bennis.minecart.client.engine.logic;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;

/**
 * This class is responsible for playing audio.
 * Uses GWT Voices.
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
		this.play(_playlist.getBackingTrack(0), true);
	}
	
	/**
	 * 
	 * @param audio
	 */
	public void play(String audiotrack, boolean loop)
	{
		SoundController soundController = new SoundController();
	    Sound sound = soundController.createSound(Sound.MIME_TYPE_AUDIO_OGG_VORBIS,
	        audiotrack);
	    sound.setLooping(loop);

	    sound.play();
	}
}
