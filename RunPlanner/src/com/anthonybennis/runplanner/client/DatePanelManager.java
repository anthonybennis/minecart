package com.anthonybennis.runplanner.client;

import com.anthonybennis.runplanner.client.handlers.DatePickerHandler;
import com.anthonybennis.runplanner.client.storage.Persistance;
import com.anthonybennis.runplanner.client.utils.RunPlannerDate;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Timer;

/**
 * 
 * @author abennis
 */
public class DatePanelManager implements IDateReciever
{
	private ImageElement _imageElement;
	private Canvas _canvas;
	private RunPlannerDate _raceDate = new RunPlannerDate();

	public DatePanelManager()
	{
		_imageElement = this.loadImage();
		this.loadUserTargetDateSettings();
	}
	
	public Canvas createCanvas()
	{
		_canvas = Canvas.createIfSupported(); // We only release on Platforms that support this.
		_canvas.addClickHandler(new DatePickerHandler(this));
		_canvas.getCanvasElement().setWidth(205);
		_canvas.getCanvasElement().setHeight(213);

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
			context2d.clearRect(0, 0,205, 213); // Clear
			context2d.setGlobalAlpha(.6);
			context2d.drawImage(_imageElement, 0, 0, 205,213);
			/*
			 * Draw text
			 */
			context2d.setFont("bold 22px sans-serif");
			context2d.setGlobalAlpha(1.0);
			context2d.setFillStyle("white");
			context2d.fillText(getDaysRemaining(),30, 40);
			/*
			 * Draw date
			 */
			context2d.setFont("bold 62px sans-serif");
			String day = this.getTargetDay();
			int xPos = (day.length() == 1)?85:65;
			
			context2d.fillText(day,xPos, 120);
			
			/*
			 * Draw Month
			 * TODO AB - Draw year?
			 */
			context2d.setFont("bold 22px sans-serif");
			String monthAndYear = this.getMonthAndYear(); 
			xPos = (monthAndYear.length() >11)?20:45;
			context2d.fillText(monthAndYear,xPos, 160);
			
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
		ImageElement imageElement = ImageLoader.getInstance().getImageElement("images/DateViewBackground.png");
		return imageElement;
	}
	
	private String getDaysRemaining()
	{
		int numberOfDays = _raceDate.calculateNumberOfDaysFromToday(); 
		String daysToGo = numberOfDays + " days to go";
		
		return daysToGo;
	}
	
	private String getTargetMonth()
	{
		return _raceDate.getMonthName();
	}
	
	private String getTargetDay()
	{
		return "" + _raceDate.getDay();
	}
	
	private String getTargetYear()
	{
		String month = "" + _raceDate.getYear();
		return month;
	}
	
	private String getMonthAndYear()
	{
		return this.getTargetMonth() + ", " + this.getTargetYear();
	}

	private void loadUserTargetDateSettings()
	{
		/*
		 * Get user settings
		 */
		String userpersistedTargetDate = Persistance.get(Persistance.TARGET_DATE);
		
		if (userpersistedTargetDate == null)
		{
			/*
			 * TOD AB Calculate a date 6 months from now...
			 */
			_raceDate.setDay(1);
			_raceDate.setMonth(1);
			_raceDate.setYear(2013);
		}
		else
		{
			_raceDate = RunPlannerDate.convertStringToDate(userpersistedTargetDate);
		}
	}

	@Override
	public void setDate(RunPlannerDate date) 
	{
		_raceDate = date;
		/*
		 * Persist Target Date
		 */
		String key = Persistance.TARGET_DATE;
		Persistance.store(key, date.convertToString());
		/*
		 * Update UI.
		 *
		 */
		this.update();
	}
}
