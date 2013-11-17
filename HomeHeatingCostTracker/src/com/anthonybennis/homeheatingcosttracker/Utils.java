package com.anthonybennis.homeheatingcosttracker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;

/**
 * 
 * @author abennis
 */
public class Utils 
{
	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenOrientation(Context context)
	{
		WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
	    Display display = windowManager.getDefaultDisplay();
	    
	    int orientation = Configuration.ORIENTATION_UNDEFINED;
	    if(display.getWidth()==display.getHeight()){
	        orientation = Configuration.ORIENTATION_SQUARE;
	    } else{ 
	        if(display.getWidth() < display.getHeight()){
	            orientation = Configuration.ORIENTATION_PORTRAIT;
	        }else { 
	             orientation = Configuration.ORIENTATION_LANDSCAPE;
	        }
	    }
	    
	    return orientation;
	}
	
	/**
	 * 
	 * @param r
	 * @param imageID
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap loadAndScaleImage(Resources r, int imageID, int width, int height)
	{
		Bitmap image = BitmapFactory.decodeResource(r, imageID);
		Bitmap scaledImage = Bitmap.createScaledBitmap(image, width, height, true);
		image.recycle();
		
		return scaledImage;
	}
}
