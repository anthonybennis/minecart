package com.anthonybennis.runplanner.client.controls;



import com.anthonybennis.runplanner.client.IDateReciever;
import com.anthonybennis.runplanner.client.Resources;
import com.anthonybennis.runplanner.client.storage.Persistance;
import com.anthonybennis.runplanner.client.utils.SuperDateUtil;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * A date picker for use in Touch screen devices
 * (Standard GWT DatePicker widget is too cumbersome)
 * 
 * @author abennis
 *
 */
public class DateChooser 
{
	private UpDownControl _dateControl;
	private UpDownControl _monthControl;
	private UpDownControl _yearsControl;
	private SuperDateUtil _date = new SuperDateUtil();
	
	public DateChooser()
	{
	}
	
	public void popup(final IDateReciever dateReciever)
	{
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Select Race Date");
		dialogBox.setAutoHideEnabled(true);
		dialogBox.setAnimationEnabled(true);
		dialogBox.setGlassEnabled(true);
		
		dialogBox.center();
		
		VerticalPanel mainDialogContainer = new VerticalPanel();
		Panel panel = this.createDatePicker();
		this.loadLastSavedDate();
		mainDialogContainer.add(panel);
		
		Button closeButton = this.createCloseButton(dialogBox);
		closeButton.setStylePrimaryName("largeTextButton");
		mainDialogContainer.add(closeButton);
	    
		FocusPanel focusPanel = new FocusPanel(mainDialogContainer);
        dialogBox.add(focusPanel);
        dialogBox.getElement().getStyle().setWidth(800, Unit.PX);
		dialogBox.show();
		dialogBox.addCloseHandler(new CloseHandler<PopupPanel>() {
			
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				setSelectedDate();
				dateReciever.setDate(_date);
			}
		});
	}
	
	private HorizontalPanel createDatePicker()
	{
		HorizontalPanel datePickerPanel = new HorizontalPanel();
		
		Image plusImage = new Image (Resources.INSTANCE.getPlusImage());
		Image minusImage = new Image (Resources.INSTANCE.getMinusImage());
		Panel dayPanel = this.createDateControl(plusImage,minusImage);
		datePickerPanel.add(dayPanel);
		
		plusImage = new Image (Resources.INSTANCE.getPlusImage());
		minusImage = new Image (Resources.INSTANCE.getMinusImage());
		Panel monthPanel = this.createMonthControl(plusImage,minusImage);
		datePickerPanel.add(monthPanel);

		plusImage = new Image (Resources.INSTANCE.getPlusImage());
		minusImage = new Image (Resources.INSTANCE.getMinusImage());
		Panel yearPanel = this.createYearPanel(plusImage,minusImage);
		datePickerPanel.add(yearPanel);
		
		return datePickerPanel;
	}
	
	private Panel createDateControl(Image plusImage, Image minusImage)
	{
		_dateControl = new UpDownControl(plusImage, minusImage);
		String[] dates = new String[31];
		
		for (int i = 0; i < dates.length; i++) 
		{
			dates[i] = "" + (i + 1);
		}
		
		return _dateControl.createUpDownControl(dates);
	}
	
	private Panel createMonthControl(Image plusImage, Image minusImage)
	{
		_monthControl = new UpDownControl(plusImage, minusImage);
		String[] months = SuperDateUtil.getMonthNames();
		return _monthControl.createUpDownControl(months);
	}
	
	private Panel createYearPanel(Image plusImage, Image minusImage)
	{
		_yearsControl = new UpDownControl(plusImage, minusImage);
		String[] years = new String[100];
		
		for (int i = 0; i < years.length; i++) 
		{
			years[i] = "" +  (i + 2012);
		}
		
		return _yearsControl.createUpDownControl(years);
	}
	
	private Button createCloseButton(final DialogBox box)
	{
		 Button closeButton = new Button("Close");
		 closeButton.setWidth("100%");
		 closeButton.setHeight("60px");
		    closeButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					 box.hide();
				}
			});
		    
		    return closeButton;
	}
	
	private void loadLastSavedDate()
	{
		String stringDate = Persistance.get(Persistance.TARGET_DATE);
		
		if (stringDate != null)
		{
			SuperDateUtil date = SuperDateUtil.convertStringToDate(stringDate);
			_dateControl.setValue("" + date.getDay());
			_monthControl.setValue("" + date.getMonthName());
			_yearsControl.setValue("" + date.getYear());
		}
		
	}
	
	private void setSelectedDate()
	{
		_date.setDay(_dateControl.getValue());
		_date.setMonth(_monthControl.getValue());
		_date.setYear(_yearsControl.getValue());
	}
}