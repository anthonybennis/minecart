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
	  
	  @Source("images/Run.png")
	  ImageResource getRunImage();
	  
	  @Source("images/FastRun.png")
	  ImageResource getFastRunImage();
	  
	  @Source("images/Walk.png")
	  ImageResource getWalkImage();
	  
	  @Source("images/CycleSwim.png")
	  ImageResource getMixImage();
	  
	  @Source("images/Sleep32x21.png")
	  ImageResource getSmallSleepImage();
	  
	  @Source("images/LeftChevron.png")
	  ImageResource getLeftButtonImage();
	  
	  @Source("images/RightChevron.png")
	  ImageResource getRightButtonImage();
	  
	  @Source("images/Intro.png")
	  ImageResource getIntroImage();
	  
	  @Source("images/5km.png")
	  ImageResource get5kmEnabledButtonImage();
	  
	  @Source("images/5kmGrey.png")
	  ImageResource get5kmDisabledButtonImage();
	  
	  @Source("images/10km.png")
	  ImageResource get10kmEnabledButtonImage();
	  
	  @Source("images/10kmGrey.png")
	  ImageResource get10kmDisabledButtonImage();
	  
	  @Source("images/21km.png")
	  ImageResource get21kmEnabledButtonImage();
	  
	  @Source("images/21kmGrey.png")
	  ImageResource get2kmDisabledButtonImage();
	  
	  @Source("images/42km.png")
	  ImageResource get42kmEnabledButtonImage();
	  
	  @Source("images/42kmGrey.png")
	  ImageResource get42kmDisabledButtonImage();
}