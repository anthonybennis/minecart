package com.anthonybennis.runplanner.client.controls.calendar;

import com.anthonybennis.runplanner.client.utils.RunPlannerDate;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Calander widget
 * @author Anthony
 *
 */
public class Calander 
{
	private static final int NUMBER_OF_COLUMS = 7;
	private static final int NUMBER_OF_ROWS = 6;
	
	public Panel createCalander()
	{
		VerticalPanel mainContainer = new VerticalPanel();
		mainContainer.setWidth("100%");
		mainContainer.add(this.createMonthControlPanel());
		mainContainer.add(this.createDayTitlePanel());
		mainContainer.add(this.createDateCellPanel());
		
		return mainContainer;
	}
	
	private Panel createMonthControlPanel()
	{
		HorizontalPanel monthControlPanel = new HorizontalPanel();
		monthControlPanel.setWidth("100%");
		
		PushButton leftButton = new PushButton();
		leftButton.setText("<<");
		monthControlPanel.add(leftButton);
		
		Label monthNameLabel = new Label();
		monthNameLabel.setText("July 2012"); // TODO AB Set Month Name
		monthNameLabel.setStylePrimaryName("monthlabel");
		monthControlPanel.add(monthNameLabel);
		
		PushButton rightButton = new PushButton();
		rightButton.setText(">>");
		monthControlPanel.add(rightButton);
		
		return monthControlPanel;
	}
	
	private HorizontalPanel createDayTitlePanel()
	{
		HorizontalPanel dayGrid = new HorizontalPanel();
		
		String[] weekDays = RunPlannerDate.geDayNames(true);
		
		for (String weekDay : weekDays) 
		{
			Label dayLabel = new Label();
			dayLabel.setText(weekDay);	
			dayLabel.setStylePrimaryName("daylabel");
			dayGrid.add(dayLabel);
		}
		
		
		return dayGrid;
	}
	
	private Grid createDateCellPanel()
	{
		Grid dateCellsGrid = new Grid(NUMBER_OF_COLUMS, NUMBER_OF_ROWS);
		
		/*
		 * How many days in the month?
		 */
		int numberOfDaysInMonth = RunPlannerDate.daysInMonth(1, 2012);
		System.out.println("Number of days in month: " + numberOfDaysInMonth);
		
		/*
		 * Create all cells
		 */
		int columnCounter = 0;
		int rowCounter = 0;
		
		for (int i = 0; i < numberOfDaysInMonth; i++) 
		{
			Panel cell = this.createCell(i + 1);
			dateCellsGrid.setWidget(rowCounter, columnCounter, cell);
			dateCellsGrid.getCellFormatter().setWidth(rowCounter, columnCounter, "125px");
			
			columnCounter++;
			
			if (columnCounter == (NUMBER_OF_COLUMS - 1))
			{
				columnCounter = 0;
				rowCounter++;
			}
			
		}
		
		/*
		 * Find what day the 1st starts on
		 */
		String day = RunPlannerDate.getFirstDayOfTheMonth(7, 2012);
		System.out.println("Day: " + day);
		
		
		/*
		 * Highlight today
		 */
		
		/*
		 * Highlight race date
		 */
		
		/*
		 * Fill dates with run plan.
		 */
		
		
		return dateCellsGrid;
	}
	
	private Panel createCell(int date)
	{
		Panel cell = new VerticalPanel();
		
		/*
		 * Date Label
		 */
		Label dateLabel = new Label();
		dateLabel.setText("" + date);
		dateLabel.setStylePrimaryName("daylabel");
		/*
		 * Plan Label
		 */
		Label planLabel = new Label();
		planLabel.setText("Rest");
		dateLabel.setStylePrimaryName("daylabel");
		
		cell.add(dateLabel);
		
		return cell;
	}

}
