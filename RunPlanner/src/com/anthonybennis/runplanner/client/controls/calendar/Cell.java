package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.Date;
import java.util.List;

import com.anthonybennis.runplanner.client.logic.Activity;
import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.anthonybennis.runplanner.client.logic.PlanItem.PACE;
import com.anthonybennis.runplanner.client.logic.WalkRunMix;
import com.anthonybennis.runplanner.client.utils.SuperDateUtil;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author abennis
 */
public class Cell
{
	private PlanItem _planItem;
	private Date _date;
	private boolean  _greyBorder;
	
	/**
	 * 
	 * @param date
	 */
	public Cell(Date date, boolean greyBorder)
	{
		_date = date;
		_greyBorder = greyBorder;
	}
	
	/**
	 * 
	 * @return
	 */
	private Panel createHeaderPanel()
	{
		Panel headerPanel = new HorizontalPanel();
		
		/*
		 * Date
		 */
		Label dateLabel = this.createDateLabel();
		headerPanel.add(dateLabel);
		
		/*
		 * Image
		 */
		Image image = this.createHeaderIcon();
		
		if (image != null)
		{
			image.setStylePrimaryName("cellIconImage");
			headerPanel.add(image); 
		}
		
		return headerPanel;
	}
	
	@SuppressWarnings("deprecation")
	private Label createDateLabel()
	{
		Label dateLabel = new Label();
		dateLabel.setStylePrimaryName("smallWhiteText");
		int date = _date.getDate();
		dateLabel.setText("" + date);
		
		return dateLabel;
	}
	
	/**
	 * 
	 * @return
	 */
	private Image createHeaderIcon()
	{
		Image image = null;
		
		PACE pace = _planItem.getPace();
		
		switch (pace) 
		{
			case COMFORTABLE:
			{	
				image = CalendarImage.createComfortablePACEImage();
				break;
			}
			case FAST:
			{	
				image = CalendarImage.createFastPACEImage();
				break;
			}
			case MIX:
			{	
				image = CalendarImage.createMixPACEImage();
				break;
			}
			case REST:
			{	
				image = CalendarImage.createRestPACEImage();
				break;
			}
			case SLOW:
			{	
				image = CalendarImage.createSlowPACEImage();
				break;
			}
			default:
			{
				break;
			}
		}
		
		return image;
	}
	
	/**
	 * 
	 * @return
	 */
	private Panel createWalkRunMixPanel()
	{
		Panel walkRunMixDetailsPanel = new VerticalPanel(); // TODO Consider FlowPanel?
		walkRunMixDetailsPanel.setSize("100%", "100%");
		
		WalkRunMix walkRunMix = _planItem.getWalkRunMix();
		
		List<Activity> activitiesList = walkRunMix.getActivityList();
		
		/*
		 * TODO Make this appear nice if there's only one activity.
		 */
		Label activityLabel;
		for (Activity activity : activitiesList) 
		{
			activityLabel = new Label();
			activityLabel.setText(" " + activity.getName() + " " + activity.getNumber() + " km");
			activityLabel.setStylePrimaryName("cellWalkRunMixLabel");
			walkRunMixDetailsPanel.add(activityLabel);
		}
		
		return walkRunMixDetailsPanel;
	}
	
	/**
	 * 
	 * @param planItem
	 */
	@SuppressWarnings("deprecation")
	protected void setPlanItem(PlanItem planItem)
	{
		_planItem = planItem;
		
		if (_planItem != null && _planItem.getDate() != null)
		{
			_date.setDate(_planItem.getDate().getDate());
			_date.setMonth(_planItem.getDate().getMonth());
			_date.setYear(_planItem.getDate().getYear());
		}
	}
	
	protected boolean showPlanItemDetails()
	{
		return !_greyBorder;
	}
	
	protected PlanItem getPlanItem()
	{
		return _planItem;
	}
	
	/**
	 * 
	 * @param planItem
	 * @return
	 */
	protected boolean doDatesMatch(PlanItem planItem)
	{
		boolean foundAMatch = false;
		
		RunPlannerDate planItemDate = planItem.getDate();
		foundAMatch = SuperDateUtil.isSameDate(_date, planItemDate); 
		
		return foundAMatch;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Panel createPanel()
	{
		Panel mainCellPanel = new VerticalPanel();
		mainCellPanel.setSize("105px", "90px"); // TODO AB Hard coded so all cells are the same. Can we do this more dynamically?
		mainCellPanel.setStylePrimaryName("cellPanel");
		
		if (_planItem != null)
		{
			/*
			 * Create Cell with Plan Item details
			 */
			this.createCell(mainCellPanel, _planItem);
		}
		else
		{
			/*
			 * Create Cell with no details.
			 */
			this.createCell(mainCellPanel);
		}
	
		
		return mainCellPanel;
	}
	
	/**
	 * 
	 * @param parentPanel
	 * @param planItem
	 */
	private void createCell(Panel parentPanel, PlanItem planItem)
	{
		if (_planItem != null) // Should never be null!
		{
			Panel headerPanel = this.createHeaderPanel();
			Panel walkRunMixPanel = this.createWalkRunMixPanel();
			
			parentPanel.add(headerPanel);
			parentPanel.add(walkRunMixPanel);
		}
		
		this.setCellStyle(parentPanel);
	}
	
	/**
	 * 
	 * @param parentPanel
	 */
	private void createCell(Panel parentPanel)
	{
		Label dateLabel = this.createDateLabel();
		parentPanel.add(dateLabel);
		this.setCellStyle(parentPanel);
	}
	
	private void setCellStyle(Panel parentPanel)
	{
		/*
		 * Highlight Cell if today is = _date
		 */
		if (this.isToday())
		{
			parentPanel.setStylePrimaryName("todaysCellPanel");
		}
		/*
		 * The Cell is in a month panel of a different month?
		 */
		else if (_greyBorder)
		{
			parentPanel.setStylePrimaryName("greyedOutCellPanel");
		}
		
		/*
		 * TODO Highlight Cell if today is start day
		 */
		
		/*
		 * TODO Highlight Cell if today is Race day
		 */
		
	}
	

	
	private boolean isToday()
	{
		boolean isCellsDateTodaysDate = false;
		Date date = new Date();		
		isCellsDateTodaysDate = SuperDateUtil.isSameDate(_date, date);
		
		return isCellsDateTodaysDate;
	}
}