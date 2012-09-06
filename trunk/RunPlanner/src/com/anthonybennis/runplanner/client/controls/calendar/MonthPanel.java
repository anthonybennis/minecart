package com.anthonybennis.runplanner.client.controls.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.anthonybennis.runplanner.client.logic.PlanItem;
import com.anthonybennis.runplanner.client.utils.SuperDateUtil;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	protected Panel createPanel()
	{
		Panel panel = new VerticalPanel();
		
		/*
		 * Create Month Name Header
		 */
		Label headerLabel = this.createHeaderPanel();
		panel.add(headerLabel);
		
		/*
		 * Create GridPanel for cells.
		 */
		Grid dateCellsGrid = new Grid(7, 7); // TODO We should dynamically calculate number of rows.
		panel.add(dateCellsGrid);
		
		/*
		 * Create week Day Name Cells
		 */
		
		/*
		 * Add Cells to grid Panel
		 * TODO Make sure we start the cells on the right
		 * column for the right first day of the month
		 */
		int columnCounter = 0;
		int rowCounter = 0;
		
		for (Cell cell:_dayCells) 
		{
			Panel cellPanel = cell.createPanel();
			dateCellsGrid.setWidget(rowCounter, columnCounter, cellPanel);
			dateCellsGrid.getCellFormatter().setWidth(rowCounter, columnCounter, "125px"); // TODO Do we need this?
			
			columnCounter++;
			
			if (columnCounter == (7 - 1))
			{
				columnCounter = 0;
				rowCounter++;
			}
		}
		
		return panel;
	}
	
	private Label createHeaderPanel()
	{
		Label headerPanel = new Label();
		headerPanel.setText(SuperDateUtil.getMonthName(_month)); // TODO Make Text Large
		return headerPanel;
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
}