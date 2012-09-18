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
		Grid dateCellsGrid = new Grid(7, 7);
		
		
		/*
		 * Create week Day Name Cells
		 */
		this.createWeekDayCells(dateCellsGrid);
		
		/*
		 * Add Cells to grid Panel
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
		_monthNameLabel.setText(monthName + " " + (this.getYear() + 1900));
	}
	
	/**
	 * 
	 * @param planItem
	 */
	protected void addPlanItem(PlanItem planItem)
	{
		for (Cell cell: _dayCells) 
		{
			if (cell.doDatesMatch(planItem) && cell.showPlanItemDetails())
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
		int weekDayIndex = SuperDateUtil.getFirstDayOfTheMonth(month, year);
		
		
		/*
		 * Create empty cells for previous month
		 * (Every month does not start on a Monday, so we need
		 * to create the cells for the last month
		 */
		Date lastMonthsDate;
		
		int specialPreviousMonthIndex = (month == 0)?11:(month - 1);
		System.out.println("Month is: " + specialPreviousMonthIndex);
		int numberOfDaysInPreviousMonth = SuperDateUtil.daysInMonth(specialPreviousMonthIndex, year);
		int lastDayDate = numberOfDaysInPreviousMonth;
		
		
		/*
		 * Debug
		 */
		System.err.println("The previous month has " + numberOfDaysInPreviousMonth + " days.");
		System.err.println("Last months start date is: " + lastDayDate);
		System.err.println("Goin to create " + lastDayDate + " days from the last month...");
		
		weekDayIndex = (weekDayIndex >= 7)?(weekDayIndex-7):weekDayIndex; // No point shown a full empty week from last month.
		lastDayDate = lastDayDate - (weekDayIndex - 1); 
		Cell cell;
		
		
		for (int i = 0; i < weekDayIndex; i++) // Last months days added in wrong order?
		{
			// Set date to this month panels date, so we have the right year
			lastMonthsDate = new Date();
			int previousMonthYear = year;
			if (month == 0)
			{
				previousMonthYear = previousMonthYear -1;
			}
			
			lastMonthsDate.setYear(previousMonthYear);
			lastMonthsDate.setMonth(lastMonthsDate.getMonth() - 1);
			lastMonthsDate.setDate(lastDayDate);
			
			System.err.println("Creating disabled Cell: " + lastMonthsDate.toString());
			cell = new Cell(lastMonthsDate, true);
			
			_dayCells.add(cell);
			
			lastDayDate = lastDayDate+1;
		}
		
		
		Date date;
		for (int i = 0; i < numberOfDaysInMonth; i++) 
		{
			date = new Date();
			date.setDate(i + 1);
			date.setMonth(month);
			date.setYear(year);
			
			cell = new Cell(date, false);
			
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