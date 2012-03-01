package com.bennis.minecart.client.engine.logic;

/**
 * Plays MP3s using HTML5 audio tag
 * Note: This requires a HTML5 audio tag to work. For example:
 * 
 * <!-- Audio -->
 * <audio id="theme" loop src="audio/Tylerhouse-320.ogg" autobuffer="autobuffer"> </audio>
 * 
 *  
 * @author abennis
 */
public class AudioPlayer 
{
	/**
	 * Play audio using JSNI
	 */
	public native void playAudioTag() /*-{
	$doc.getElementById('theme').play();
	//$doc and $wnd are JSNI-speak for document and window
	}-*/;
}
