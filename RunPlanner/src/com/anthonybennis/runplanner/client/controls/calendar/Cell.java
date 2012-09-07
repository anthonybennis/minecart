package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.Date;
import java.util.List;

import com.anthonybennis.runplanner.client.Resources;
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
	
	public Cell(Date date)
	{
		_date = date;
	
	}
	
	private Panel createHeaderPanel()
	{
		Panel headerPanel = new HorizontalPanel(); // TODO Add Border to Cells.
		
		/*
		 * Date
		 */
		Label dateLabel = new Label();
		dateLabel.setStylePrimaryName("smallWhiteText");
		int date = _planItem.getDate().getDate();
		dateLabel.setText("" + date);
		headerPanel.add(dateLabel);
		
		/*
		 * Image
		 */
		Image image = this.createHeaderIcon();
		
		if (image != null)
		{
			headerPanel.add(image); //TODO Align Right
		}
		
		return headerPanel;
	}
	
	private Image createHeaderIcon()
	{
		Image image = null;
		
		PACE pace = _planItem.getPace();
		
		switch (pace) 
		{
			case COMFORTABLE:
			{	
				image = this.createComfortablePACEImage();
				break;
			}
			case FAST:
			{	
				image = this.createFastPACEImage();
				break;
			}
			case MIX:
			{	
				image = this.createMixPACEImage();
				break;
			}
			case REST:
			{	
				image = this.createRestPACEImage();
				break;
			}
			case SLOW:
			{	
				image = this.createSlowPACEImage();
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
			activityLabel.setText(activity.getName() + " " + activity.getNumber() + " km");
			walkRunMixDetailsPanel.add(activityLabel);
		}
		
		
		return walkRunMixDetailsPanel;
	}
	
	/**
	 * 
	 * @param planItem
	 */
	protected void setPlanItem(PlanItem planItem)
	{
		_planItem = planItem;
		// TODO AB - Populate Cell with PlanItem details
	}
	
	protected PlanItem getPlanItem()
	{
		return _planItem;
	}
	
	protected boolean doDatesMatch(PlanItem planItem)
	{
		boolean foundAMatch = false;
		
		Date planItemDate = planItem.getDate();
		foundAMatch = SuperDateUtil.isSameDate(_date, planItemDate); 
		
		return foundAMatch;
	}
	
	protected Panel createPanel()
	{
		Panel mainCellPanel = new VerticalPanel();
		mainCellPanel.setStylePrimaryName("cellPanel");
		
		if (_planItem != null) // Should never be null!
		{
			Panel headerPanel = this.createHeaderPanel();
			Panel walkRunMixPanel = this.createWalkRunMixPanel();
			
			mainCellPanel.add(headerPanel);
			mainCellPanel.add(walkRunMixPanel);
			
			/*
			 * TODO Highlight Cell if today is = _date
			 */
			
			/*
			 * TODO Highlight Cell if today is start day
			 */
			
			/*
			 * TODO Highlight Cell if today is Race day
			 */
		}
		
		return mainCellPanel;
	}
	
	private Image createFastPACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
	
	private Image createSlowPACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
	
	private Image createComfortablePACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
	
	private Image createMixPACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
	
	private Image createRestPACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
}