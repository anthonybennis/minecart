package com.anthonybennis.runplanner.client;

import java.util.Date;

import com.anthonybennis.runplanner.client.handlers.DatePickerHandler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * 
 * @author abennis
 */
public class DatePanelManager 
{

	private ImageElement _imageElement;
	private ImageLoader _imageLoader;
	private Canvas _canvas;

	public DatePanelManager()
	{
		_imageLoader = new ImageLoader();
		_imageElement = this.loadImage();
	}
	
	public Canvas createCanvas()
	{
		_canvas = Canvas.createIfSupported();
		_canvas.addClickHandler(new DatePickerHandler(_canvas));

		this.update();
	
		return _canvas;
	}
	
	protected void update()
	{
		if (_canvas != null && _imageElement != null)
		{			
			Context2d context2d = _canvas.getContext2d();
			context2d.restore();
			/*
			 * Draw Background Image
			 */
//			context2d.setGlobalAlpha(.6);
			context2d.drawImage(_imageElement, 0, 0);
			/*
			 * Draw text
			 */
			context2d.setFont("bold 22px sans-serif");
			context2d.setGlobalAlpha(1.0);
			context2d.setFillStyle("white");
			context2d.fillText("July",20, 30);
			/*
			 * Draw date
			 * TODO AB Load all images on start up (1-31 etc)
			 */
			
			
			/*
			 * Draw Day
			 */
			context2d.fillText(getDaysRemaining(),20, 140);

			context2d.save();
			context2d.restore();
		}
		else if ((_canvas != null && _imageElement == null)) // Image has not loaded yet.
		{
			// Create a new timer that calls Window.alert().
		    Timer t = new Timer() {
		      public void run() {
		    	  _imageElement = loadImage();
		    	  update(); // RECURSIVE CALL UNTIL IMAGE IS LOADED!!!
		      }
		    };

		    // Schedule the timer to run in 100 milliseconds
		    t.schedule(150);
		  }
	}
	

	private ImageElement loadImage()
	{
		ImageElement imageElement = _imageLoader.getImage("images/DateViewBackground.png");
		return imageElement;
	}
	
	private String getDaysRemaining()
	{
		String daysToGo = "";
		
		return daysToGo;
	}
}
