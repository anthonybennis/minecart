package com.anthonybennis.runplanner.client.controls;

import com.anthonybennis.runplanner.client.storage.Persistance;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This class is responsible for creating and showing the About box
 * Dialog.
 * 
 * TODO ENHANCEMENT: Ideally we would not use a Dialog, but show
 * this Panel full screen, with a back button. Maybe we should look into doing this?
 * DialogBox is quickest and easiest solution for now.
 * 
 * @author abennis
 */
public class AboutBox 
{
	private DialogBox _dialogBox;

	public void show()
	{
		DialogBox aboutBox = this.createDialogBox();
		aboutBox.show();
	}
	
	/**
	 * 
	 * @return
	 */
	private DialogBox createDialogBox()
	{
		_dialogBox = new DialogBox();
		_dialogBox.setModal(true);
		_dialogBox.setAnimationEnabled(true);
		_dialogBox.setAutoHideEnabled(true);
		_dialogBox.setGlassEnabled(true);
		_dialogBox.setTitle("RunPlanner");
		_dialogBox.center();
		
		
		
		Panel mainContentsPanel = this.createDialogBoxContents();
		
		FocusPanel focusPanel = new FocusPanel(mainContentsPanel);
//		focusPanel.setSize("100%", "100%");
		_dialogBox.add(focusPanel);
        
		_dialogBox.setWidget(mainContentsPanel);
		return _dialogBox;
	}
	
	/**
	 * 
	 * @return
	 */
	private Panel createDialogBoxContents()
	{
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.getElement().getStyle().setBackgroundColor("black");
//		mainPanel.setSize("100%", "100%");
		
		/*
		 * TODO ENHANCEMENT Show "About Label" or RunPlanner Logo?
		 */
		Label aboutLabel = new Label("About Run Planner");
		aboutLabel.setStylePrimaryName("largeWhiteText");
		mainPanel.add(aboutLabel);
		mainPanel.setCellHorizontalAlignment(aboutLabel, HasHorizontalAlignment.ALIGN_CENTER);
		
		/*
		 * Show current Plan information
		 * TODO Move to Summary dialog
		 */

		
		
		/*
		 * Copy Right Notice
		 */
		HTML copyRightNoticeHTML = new HTML("<p> &copy; 2012 Anthony Bennis</p>");
		copyRightNoticeHTML.setStylePrimaryName("smallWhiteText");
		mainPanel.add(copyRightNoticeHTML);
		mainPanel.setCellHorizontalAlignment(copyRightNoticeHTML, HasHorizontalAlignment.ALIGN_CENTER);
		
		/*
		 * Button Panel
		 * TODO Make buttons & button text bigger
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();
		mainPanel.add(buttonPanel);
		mainPanel.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		
		/*
		 * Clear all Button
		 */
		final Button deletePlanButton = new Button("Clear Plan");
		deletePlanButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Persistance.clearAll();
				deletePlanButton.setEnabled(false);
			}
		});
		
		
		buttonPanel.add(deletePlanButton);
		/*
		 * Close Button
		 */
		Button closeButton = new Button("Close"); // or "back"?
		closeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				_dialogBox.hide();
			}
		});
		buttonPanel.add(closeButton);
		
		return mainPanel;
	}
}