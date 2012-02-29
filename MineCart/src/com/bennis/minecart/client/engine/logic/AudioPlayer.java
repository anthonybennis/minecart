package com.bennis.minecart.client.engine.logic;

/**
 * Plays MP3s.
 * @author abennis
 */
public class AudioPlayer 
{
	/**
	 * Play audio using JSNI
	 * TODO AB Make this Generic for framework.
	 */
	public native void playAudioTag() /*-{
	$doc.getElementById('theme').play();
	//$doc and $wnd are JSNI-speak for document and window
	}-*/;
}
