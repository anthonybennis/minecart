package com.anthonybennis.runplanner.client.controls;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UpDownControl 
{
	private static final String BLACK = "black";
	private static final String TEXT_WIDTH = "75px";
	private static final String BUTTON_WIDTH = "85px";
	private static final String CONTROL_HEIGHT = "70px";
	private String[] _contents;
	private TextBox _value;
	private Image _plusImage;
	private Image _minusImage;
	
	public UpDownControl(Image plusImage, Image minusImage)
	{
		_plusImage = plusImage; 
		_minusImage = minusImage;
	}
	
	/*
	 * TODO AB - Round Buttons.
	 */
	public Panel createUpDownControl(String[] contents)
	{
		/*
		 * Create container Panel
		 */
		VerticalPanel verticalPanel = new VerticalPanel();
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
		_value = new TextBox();
		_value.setWidth(TEXT_WIDTH);
		_value.setHeight(CONTROL_HEIGHT);
		_value.setReadOnly(true);
		_value.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		_value.setAlignment(TextAlignment.CENTER);
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
