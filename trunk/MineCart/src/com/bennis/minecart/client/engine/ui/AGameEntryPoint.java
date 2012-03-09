package com.bennis.minecart.client.engine.ui;

import com.bennis.minecart.client.engine.logic.AGame;
import com.bennis.minecart.client.engine.logic.GameLoop;
import com.bennis.minecart.client.engine.model.Scene;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Generic Entry Point for all Games.
 * Extend to use for a specific Game.
 * 
 * @author abennis
 */
abstract public class AGameEntryPoint implements EntryPoint 
{
	private final String WIDTH = "" + this.getCanvasSize()[0];
	private final String HEIGHT = "" + this.getCanvasSize()[1];
	final CssColor redrawColor = CssColor.make("rgba(255,255,255,0.6)");
	private Canvas _canvas;
	private Canvas _bufferCanvas;
	
	@Override
	public void onModuleLoad() 
	{
		try
		{
			/*
			 * Remove Splash Screen
			 */
			// DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("splashScreen"));
			
			/*
			 * Create Game
			 */
			Scene scene = new Scene();
			Canvas[] canvases = this.createCanves();
			AGame game = this.createGame(canvases[0], canvases[1],scene);
			this.createPanels(game);
			this.removeSplashScreen(getSplashScreenContainerName());
			GameLoop gameLoop = new GameLoop(game);
			gameLoop.startLoop();
		}
		catch (Exception e)
		{
			/*
			 * If there is no support for Canvas, it is handled within the HTML.
			 */
			e.printStackTrace();
		}
	}
	
	private Canvas[] createCanves() throws Exception
	{
		Canvas[] canveses = new Canvas[2];
		/*
		 * Set up Root
		 */
		String container = this.getHTMLContainerName();		
		
		/*
		 * Create Canvas
		 */
		_canvas = Canvas.createIfSupported();
		_bufferCanvas = Canvas.createIfSupported();
		if (_canvas != null)
		{
			_canvas.setWidth("100%");
			_canvas.setHeight("100%");
			_canvas.setSize(WIDTH + "px",HEIGHT + "px");
			_canvas.setCoordinateSpaceWidth(this.getCanvasSize()[0]);
			_canvas.setCoordinateSpaceHeight(this.getCanvasSize()[1]);
			_bufferCanvas.setCoordinateSpaceWidth(this.getCanvasSize()[0]);
			_bufferCanvas.setCoordinateSpaceHeight(this.getCanvasSize()[1]);
			RootPanel.get(container).add(_canvas);
			_canvas.getContext2d().save();
		}
		else
		{
			throw new Exception("Canvas element not supported.");
		}
		
		canveses[0] = _bufferCanvas;
		canveses[1] = _canvas;
		
		return canveses;
	}
	
	/**
	 * The Game is where you're implementation does all the unique stuff to your game.
	 * For example:
	 * <code>
	 * AGame game = new MineCartGame(this.getBufferContext(),this.getBufferContext();
	 * </code>
	 * @return
	 */
	public abstract AGame createGame(Canvas canvas, Canvas bufferCanvas, Scene scene);
	
	/**
	 * The name of the <div> container from the HTML index page that
	 * launches this game in your Browser. For example, in the <body>
	 * of a HTML page, a container would be "canvasContainer":
	 * <code>
	 *   <table>
	 *     <tr>
	 *       <td id="canvasContainer"></td>
	 *     </tr>
	 *   </table>
	 * </code>
	 * 
	 * The Canvas created by this class will then appear in the HTML
	 * page in that container.
	 * @return
	 */
	public abstract String getHTMLContainerName();
	
	/**
	 * Splash screen div is removed when module is loaded.
	 * @return id of div. For example, in our HTML there could be:
	 * 
	 *  <!--  Splash screen -->
	 *  <div id=”loading”><img src=”loading.gif”>Loading...</div>
	 *  
	 *  ”loading” is the Container Name in this case.
	 * 
	 */
	public abstract String getSplashScreenContainerName();
	
	/**
	 * Allows any sub class the opportunity to 
	 * create and add panels to the Root, once
	 * the Canvas has been created.
	 */
	public abstract void createPanels(AGame game);
	
	public abstract int[] getCanvasSize();
	
	protected void removeSplashScreen(String divID)
	{
		/*
		 * TODO AB
		 */
//		@SuppressWarnings("static-access")
//		Element bodyElement = RootPanel.getBodyElement();
//		DOM.removeChild(bodyElement, DOM.getElementById(divID));
	}
}
