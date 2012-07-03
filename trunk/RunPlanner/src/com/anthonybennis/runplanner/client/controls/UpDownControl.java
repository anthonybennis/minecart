package com.anthonybennis.runplanner.client.controls;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UpDownControl 
{
	private String[] _contents;
	private TextBox _value;
	
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
		Button upButton = new Button("+");
		upButton.setWidth("50px");
		upButton.addClickHandler(new UpButtonClickHandler());
		verticalPanel.add(upButton);
		
		/*
		 * Value Text
		 */
		_value = new TextBox();
		_value.setWidth("40px");
		_value.setReadOnly(true);
		_value.setAlignment(TextAlignment.CENTER);
		verticalPanel.add(_value);
		
		if (contents != null && contents.length > 0)
		{
			_value.setText(contents[0]);// Default to first value
		}
		
		/*
		 * Create Down button
		 */
		Button downButton = new Button("-");
		downButton.setWidth("50px");
		downButton.addClickHandler(new DownButtonClickHandler());
		verticalPanel.add(downButton);
		
		return verticalPanel;
	}
	
	public void select(String content)
	{
		
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
