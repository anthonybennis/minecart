package com.anthonybennis.runplanner.client;



import com.anthonybennis.runplanner.client.handlers.DatePickerHandler;
import com.anthonybennis.runplanner.client.storage.Persistance;
import com.anthonybennis.runplanner.client.utils.Date;
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
	private final String VALUE_NOT_SET = "-1";
	private String targetMonth = VALUE_NOT_SET;
	private String targetYear = VALUE_NOT_SET;
	private Date _date = new Date();

	public DatePanelManager()
	{
		_imageElement = this.loadImage();
		this.loadUserTargetDateSettings();
	}
	
	public Canvas createCanvas()
	{
		_canvas = Canvas.createIfSupported(); // We only release on Platforms that support this.
		_canvas.addClickHandler(new DatePickerHandler(this));
		_canvas.setWidth("205px");
		_canvas.setWidth("213px");

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
			context2d.clearRect(0, 0, _canvas.getCoordinateSpaceWidth(), _canvas.getCoordinateSpaceHeight()); // Clear
			context2d.setGlobalAlpha(.6);
			context2d.drawImage(_imageElement, 0, 0);
			/*
			 * Draw text
			 */
			context2d.setFont("bold 22px sans-serif");
			context2d.setGlobalAlpha(1.0);
			context2d.setFillStyle("white");
			context2d.fillText(getDaysRemaining(),30, 40);
			/*
			 * Draw date
			 * TODO AB Load all images on start up (1-31 etc)
			 */
			context2d.setFont("bold 62px sans-serif");
			context2d.fillText(this.getTargetDay(),85, 120);
			
			/*
			 * Draw Month
			 * TODO AB - Draw year?
			 */
			context2d.setFont("bold 22px sans-serif");
			context2d.fillText(this.getMonthAndYear(),20, 160);

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
		ImageElement imageElement = RunPlanner.IMAGELOADER.getImageElement("images/DateViewBackground.png");
		return imageElement;
	}
	
	private String getDaysRemaining()
	{
		int numberOfDays = _date.calculateNumberOfDaysFromToday(); 
		String daysToGo = numberOfDays + " days to go";
		
		return daysToGo;
	}
	
	private String getTargetMonth()
	{
		return _date.getMonthName();
	}
	
	private String getTargetDay()
	{
		return "" + _date.getDay();
	}
	
	private String getTargetYear()
	{
		String month = "2012";
		return month;
	}
	
	private String getMonthAndYear()
	{
		return this.getTargetMonth() + ", " + this.getTargetYear();
	}
	
	/**
	 * If the user hasn't set a target, than we
	 * default to 6 months from today.
	 */
	private void createDefaultValues()
	{
		
	}
	
	private void loadUserTargetDateSettings()
	{
		/*
		 * Get user settings
		 */
		String userpersistedTargetDate = Persistance.get(Persistance.TARGET_DATE);
		_date = Date.convertStringToDate(userpersistedTargetDate);
	}

	@Override
	public void setDate(Date date) 
	{
		_date = date;
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
