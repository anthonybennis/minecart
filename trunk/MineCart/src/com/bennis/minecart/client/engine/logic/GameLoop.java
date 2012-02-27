package com.bennis.minecart.client.engine.logic;

import com.google.gwt.user.client.Timer;

/**
 * Infinite Game Loop.
 * @author abennis
 */
public class GameLoop 
{
	 //timer refresh rate, in milliseconds
	 private static final int refreshRate = 25;
	 
	 private static AGame _game;
	 
	 
	 public GameLoop(AGame game)
	 {
		 _game = game;
	 }
	  
	/**
	 * Kicks off the loop.
	 * Does nothing else. The AGame object does everything else.
	 */
	public void startLoop()
	{
	    /*
	     * TODO Cap speed so loop isn't too fast.
	     */
	    final Timer timer = new Timer() {
	      @Override
	      public void run() {
	    	  _game.update();
	      }
	    };
	    timer.scheduleRepeating(refreshRate);
	}
}
