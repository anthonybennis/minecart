package com.anthonybennis.runplanner.client;



import com.anthonybennis.runplanner.client.handlers.DatePickerHandler;
import com.anthonybennis.runplanner.client.storage.Persistance;
import com.anthonybennis.runplanner.client.utils.Date;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

/**
 * 
 * @author abennis
 */
public class DatePanelManager implements IDateReciever
{
	private Canvas _canvas;
	private Date _date = new Date();

	public DatePanelManager()
	{
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
		Image image = new Image(Resources.INSTANCE.getDatePanelBackgroundImage());
		ImageElement backgroundImage = ImageElement.as(image.getElement());
		
		if (_canvas != null && backgroundImage != null)
		{			
			Context2d context2d = _canvas.getContext2d();
			context2d.restore();
			/*
			 * Draw Background Image
			 */
			context2d.clearRect(0, 0,205, 213); // Clear
			context2d.setGlobalAlpha(.6);
			context2d.drawImage(backgroundImage, 0, 0, 205,213);
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
		String month = "" + _date.getYear();
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
			_date.setDay(1);
			_date.setMonth(1);
			_date.setYear(2012);
		}
		else
		{
			_date = Date.convertStringToDate(userpersistedTargetDate);
		}
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
