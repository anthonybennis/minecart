package com.anthonybennis.runplanner.client.controls;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UpDownControl 
{
	private static final String BLACK = "black";	
	private static final String BUTTON_WIDTH = "110px";
	private static final String CONTROL_HEIGHT = "60px";
	private String[] _contents;
	private Label _value;
	private Image _plusImage;
	private Image _minusImage;
	
	public UpDownControl(Image plusImage, Image minusImage)
	{
		_plusImage = plusImage; 
		_minusImage = minusImage;
	}
	
	public Panel createUpDownControl(String[] contents)
	{
		/*
		 * Create container Panel
		 */
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setWidth("100%");
		_contents = contents;
		
		/*
		 * Create up button
		 */
		PushButton upButton = new PushButton(_plusImage);
		upButton.getElement().getStyle().setBackgroundColor(BLACK);
		upButton.setWidth(BUTTON_WIDTH);
		upButton.setHeight(CONTROL_HEIGHT);
		upButton.addClickHandler(new UpButtonClickHandler());
		verticalPanel.add(upButton);
		
		/*
		 * Value Text
		 */
		_value = new Label();
		_value.setWidth(BUTTON_WIDTH);
		_value.setHeight("40px");
		_value.setAutoHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		
		_value.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		verticalPanel.add(_value);
		
		if (contents != null && contents.length > 0)
		{
			_value.setText(contents[0]);// Default to first value
		}
		
		/*
		 * Create Down button
		 */
		PushButton downButton = new PushButton(_minusImage);
		downButton.getElement().getStyle().setBackgroundColor(BLACK);
		downButton.setWidth(BUTTON_WIDTH);
		downButton.setHeight(CONTROL_HEIGHT);
		downButton.addClickHandler(new DownButtonClickHandler());
		verticalPanel.add(downButton);
		
		return verticalPanel;
	}
	
	public String getValue()
	{
		return _value.getText();
	}
	
	public boolean setValue(String value)
	{
		boolean valueFoundAndSet = false;
		
		for (String textValue: _contents) 
		{
			if (value.equals(textValue))
			{
				_value.setText(textValue);
				valueFoundAndSet = true;
				break;
			}
		}
		
		return valueFoundAndSet;
	}
	
	class UpButtonClickHandler implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) 
		{
			
			for (int i = 0; i < _contents.length; i++) 
			{
				if (_value.getText().equals(_contents[i]))
				{
					if ((_contents.length - 1) > i)
					{
						_value.setText(_contents[i+1]);
						break;
					}
				}	
			}
		}
	}
	
	class DownButtonClickHandler implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) 
		{
			for (int i = 0; i < _contents.length; i++) 
			{
				if (_contents[i].equals(_value.getText()))
				{
					if (i > 0)
					{
						_value.setText(_contents[i-1]);
						break;
					}
				}	
			}
		}
	}
}
