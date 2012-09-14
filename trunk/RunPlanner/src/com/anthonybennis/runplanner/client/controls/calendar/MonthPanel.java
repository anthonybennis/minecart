package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.anthonybennis.runplanner.client.utils.SuperDateUtil;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author abennis
 */
public class MonthPanel 
{
	private List<Cell> _dayCells = new ArrayList<Cell>();
	private int _month;
	private int _year;
	private Label _monthNameLabel;
	
	public MonthPanel(int month, int year)
	{
		_month = month;
		_year = year;
		
		this.createDays(month, year);		
	}
	
	/**
	 * 
	 * @return
	 */
	protected Panel createPanel(Label monthNameLabel)
	{
		Panel panel = new VerticalPanel();
		
		/*
		 * Create Month Name Header
		 * This is not set now, but after all panels are created, and when the
		 * user clicks Left or Right.
		 */
		_monthNameLabel = monthNameLabel;
		
		/*
		 * Create GridPanel for cells.
		 */
		Grid dateCellsGrid = new Grid(7, 7); // TODO We should dynamically calculate number of rows.
		
		
		/*
		 * Create week Day Name Cells
		 */
		this.createWeekDayCells(dateCellsGrid);
		
		/*
		 * Add Cells to grid Panel
		 * TODO Make sure we start the cells on the right
		 * column for the right first day of the month
		 */
		int columnCounter = 0;
		int rowCounter = 1;
		
		for (Cell cell:_dayCells) 
		{
			Panel cellPanel = cell.createPanel();
			dateCellsGrid.setWidget(rowCounter, columnCounter, cellPanel);
			columnCounter++;
			
			if (columnCounter == (7))
			{
				columnCounter = 0;
				rowCounter++;
			}
		}
		
		panel.add(dateCellsGrid);
		
		return panel;
	}
	
	/**
	 * 
	 */
	private void createWeekDayCells(Grid dateCellsGrid)
	{
		String[] weekDayNames = SuperDateUtil.getWeekDayNames();
		
		/*
		 * Create Labels
		 */
		Label mondayLabel = new Label(weekDayNames[1]);
		mondayLabel.getElement().setAttribute("align", "center");
		Label tuesdayLabel = new Label(weekDayNames[2]);
		tuesdayLabel.getElement().setAttribute("align", "center");
		Label wednesdayLabel = new Label(weekDayNames[3]);
		wednesdayLabel.getElement().setAttribute("align", "center");
		Label thursdayLabel = new Label(weekDayNames[4]);
		thursdayLabel.getElement().setAttribute("align", "center");
		Label fridayLabel = new Label(weekDayNames[5]);
		fridayLabel.getElement().setAttribute("align", "center");
		Label saturdayLabel = new Label(weekDayNames[6]);
		saturdayLabel.getElement().setAttribute("align", "center");
		Label sundayLabel = new Label(weekDayNames[0]);
		sundayLabel.getElement().setAttribute("align", "center");
		
		/*
		 * Set style
		 */
		mondayLabel.setStylePrimaryName("smallWhiteText");
		tuesdayLabel.setStylePrimaryName("smallWhiteText");
		wednesdayLabel.setStylePrimaryName("smallWhiteText");
		thursdayLabel.setStylePrimaryName("smallWhiteText");
		fridayLabel.setStylePrimaryName("smallWhiteText");
		saturdayLabel.setStylePrimaryName("smallWhiteText");
		sundayLabel.setStylePrimaryName("smallWhiteText");
		
		/*
		 * Add to Grid
		 */
		dateCellsGrid.setWidget(0, 0, mondayLabel);
		dateCellsGrid.setWidget(0, 1, tuesdayLabel);
		dateCellsGrid.setWidget(0, 2, wednesdayLabel);
		dateCellsGrid.setWidget(0, 3, thursdayLabel);
		dateCellsGrid.setWidget(0, 4, fridayLabel);
		dateCellsGrid.setWidget(0, 5, saturdayLabel);
		dateCellsGrid.setWidget(0, 6, sundayLabel);
	}
	
	/**
	 * 
	 * @return
	 */
	public void updateMonthNameLabel()
	{
		String monthName = SuperDateUtil.getMonthName(_month);
		_monthNameLabel.setText(monthName + " " + this.getYear());
	}
	
	/**
	 * 
	 * @param planItem
	 */
	protected void addPlanItem(PlanItem planItem)
	{
		for (Cell cell: _dayCells) 
		{
			if (cell.doDatesMatch(planItem))
			{
				cell.setPlanItem(planItem);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @param month
	 * @param year
	 */
	@SuppressWarnings("deprecation")
	private void createDays(int month, int year)
	{
		int numberOfDaysInMonth = SuperDateUtil.daysInMonth(month, year);
		String day = SuperDateUtil.getFirstDayOfTheMonth(month, year);
		
		Cell cell;
		Date date;
		for (int i = 0; i < numberOfDaysInMonth; i++) 
		{
			date = new Date();
			date.setDate(i + 1);
			date.setMonth(month);
			date.setYear(year);
			
			cell = new Cell(date);
			
			_dayCells.add(cell);
		}
	}
	
	/**
	 * 
	 * @param planItem
	 * @return
	 */
	protected boolean contains(PlanItem planItem)
	{
		boolean containsPlanItem = false;
		
		for (Cell cell: _dayCells) 
		{
			if (cell.getPlanItem() == planItem)
			{
				containsPlanItem = true;
				break;
			}
		}
		
		return containsPlanItem;
	}
	
	protected boolean match(int month, int year)
	{
		boolean foundAMatch = false;
		
		if (_month == month && _year == year)
		{
			foundAMatch = true;
		}
		
		return foundAMatch;
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected boolean doesTodayFallOnThisMonth()
	{
		boolean todayFallsOnThisMonth = false;
		
		Date date = new Date();
		todayFallsOnThisMonth = (date.getMonth() == _month);
		
		return todayFallsOnThisMonth;
	}
	
	public int getMonth()
	{
		return _month;
	}
	
	public int getYear()
	{
		return _year;
	}
}