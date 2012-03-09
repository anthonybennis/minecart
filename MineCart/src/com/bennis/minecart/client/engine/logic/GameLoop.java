package com.bennis.minecart.client.engine.logic;

import com.google.gwt.user.client.Timer;

/**
 * Infinite Game Loop.
 * @author abennis
 */
public class GameLoop 
{
	 //timer refresh rate, in milliseconds
	 private static final int refreshRate = 30;
	 
	 private static AGame _game;
	 //ticker timestamp
     private static long _timeStamp;
     //should we cap the frame rate?
     private static boolean _capFrameRate;
     //frame rate cap amount (ms)
     private static int _capAmount = 42;
     //the game timer
     private static Timer _gameTicker;
	 
	 
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
		_gameTicker = new Timer() {
	      @Override
	      public void run() {
	    	  _game.update();
	      }
	    };
	    _gameTicker.scheduleRepeating(refreshRate);
	}
	
	/**
     * Loops the game based on the keyboardState
     * @param keyState the current state of the keyBoard
     */
    public static void loop()
    {
            //cancel the game ticker
    		_gameTicker.cancel();
            
           /*
            * TODO Pause Game here.
            */
            
            /*
             * Cap the frame rate if we are going too fast
             */
            capFrameRate();
            
            /*
             * Re-schedule the never ending loop
             */
            _gameTicker = new Timer(){
                    @Override
                    public void run() {
                            loop();
                    }
            };
            
            if(_capFrameRate)
            {
            	_gameTicker.schedule(_capAmount);
            }
            else
            {
            	_gameTicker.schedule(1);
            }
    }
	
	  public static void capFrameRate()
	  {
          _capFrameRate = (System.currentTimeMillis() - _timeStamp)<_capAmount;
          _timeStamp = System.currentTimeMillis();
	  }
}
