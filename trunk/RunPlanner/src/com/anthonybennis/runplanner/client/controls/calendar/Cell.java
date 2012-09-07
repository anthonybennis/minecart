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
	
	/**
	 * 
	 * @param date
	 */
	public Cell(Date date)
	{
		_date = date;
	
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
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
			activityLabel.setStylePrimaryName("largeWhiteText");
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
		
		Date planItemDate = planItem.getDate();
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
		mainCellPanel.setSize("120px", "100px"); // TODO AB Hard coded so all cells are the same. Can we do this more dynamically?
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
	
	private void createCell(Panel parentPanel, PlanItem planItem)
	{
		if (_planItem != null) // Should never be null!
		{
			Panel headerPanel = this.createHeaderPanel();
			Panel walkRunMixPanel = this.createWalkRunMixPanel();
			
			parentPanel.add(headerPanel);
			parentPanel.add(walkRunMixPanel);
			
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
	}
	
	/**
	 * 
	 * @param parentPanel
	 */
	private void createCell(Panel parentPanel)
	{
		/*
		 * TODO Highlight Cell if today is = _date
		 */
		Label dateLabel = this.createDateLabel();
		parentPanel.add(dateLabel);
	}
	
	
	/**
	 * 
	 * @return
	 */
	private Image createFastPACEImage()
	{
		// TODO Create icon
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
	
	/**
	 * 
	 * @return
	 */
	private Image createSlowPACEImage()
	{
		// TODO Create icon
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
	
	/**
	 * 
	 * @return
	 */
	private Image createComfortablePACEImage()
	{
		// TODO Create icon
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
	
	/**
	 * 
	 * @return
	 */
	private Image createMixPACEImage()
	{
		// TODO Create icon
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
	
	/**
	 * 
	 * @return
	 */
	private Image createRestPACEImage()
	{
		Image image = new Image(Resources.INSTANCE.getSmallSleepImage());		
		return image;
	}
}