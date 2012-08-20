package com.anthonybennis.runplanner.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * API for all images used by Mine Cart.
 * @author Anthony
 *
 */
public interface Resources extends ClientBundle
{
	public static final Resources INSTANCE =  GWT.create(Resources.class);
	
	  @Source("images/Plus.png")
	  ImageResource getPlusImage();
	  
	  @Source("images/Minus.png")
	  ImageResource getMinusImage();
	  
	  @Source("images/CreatePlan.png")
	  ImageResource getCreatePlanButtonImage();
	  
	  @Source("images/CreatePlanDown.png")
	  ImageResource getCreatePlanDownButtonImage();

	  @Source("images/DateViewBackground.png")
	  ImageResource getDatePanelBackgroundImage();	  
	  
	  @Source("images/AudioButton.png")
	  ImageResource getAudioButton();
	  
	  @Source("images/NoAudioButton.png")
	  ImageResource getNoAudioButton();
	  
	  @Source("images/RunPlannerGold.png")
	  ImageResource getTitleLogoImage();	 
	  
	  @Source("images/Close.png")
	  ImageResource getCloseAppButtonImage();
}