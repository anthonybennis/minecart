package com.anthonybennis.runplanner.client.controls;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * A date picker for use in Touch screen devices
 * (Standard GWT DatePicker widget is too cumbersome)
 * 
 * @author abennis
 *
 */
public class DateChooser 
{
	private Canvas _parentPanel;
	
	public DateChooser(Canvas parentPanel)
	{
		_parentPanel = parentPanel;
	}
	
	public void popup()
	{
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Select Race Date");
		dialogBox.setAutoHideEnabled(true);
		final HorizontalPanel panel = this.createDatePicker();
		dialogBox.add(panel);
		
		/*
		 * Position Dialog
		 */
		dialogBox.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
	          public void setPosition(int offsetWidth, int offsetHeight) {
	            int left = _parentPanel.getAbsoluteLeft();
	            int top = _parentPanel.getAbsoluteTop();
	            dialogBox.setPopupPosition(left, top);
	            
	          }
		});
		
		/*
		 * Create Close Button
		 */
		   // Add a close button at the bottom of the dialog
	    Button closeButton = new Button("Close");
	    closeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				 dialogBox.hide();
			}
		});

        panel.add(closeButton);
        panel.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
	    
		dialogBox.show();
	}
	
	public HorizontalPanel createDatePicker()
	{
		HorizontalPanel datePickerPanel = new HorizontalPanel();
		datePickerPanel.setSize("150px", "115px");
		
		/*
		 * Add Day selector
		 */
		UpDownControl dateControl = new UpDownControl();
		String[] dates = new String[31];
		
		for (int i = 0; i < dates.length; i++) 
		{
			dates[i] = "" + (i + 1);
		}
		
		datePickerPanel.add(dateControl.createUpDownControl(dates));
		
		/*
		 * Add Month Selector
		 */
		UpDownControl monthControl = new UpDownControl();
		String[] months = LocaleInfo.getCurrentLocale().getDateTimeFormatInfo().monthsFull();
		datePickerPanel.add(monthControl.createUpDownControl(months));
		
		/*
		 * Add Year selector
		 */
		UpDownControl yearsControl = new UpDownControl();
		String[] years = new String[100];
		
		for (int i = 0; i < years.length; i++) 
		{
			years[i] = "" +  (i + 2012);
		}
		
		datePickerPanel.add(yearsControl.createUpDownControl(years));
		
		
		return datePickerPanel;
	}
}