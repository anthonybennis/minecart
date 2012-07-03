package com.anthonybennis.runplanner.client.handlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * This handler cloese the app (Browser Window).
 * @author abennis
 */
public class CloseHandler implements ClickHandler
{
	 public static native void closeBrowser()
	    /*-{
	        $wnd.close();
	    }-*/;

	@Override
	public void onClick(ClickEvent event) 
	{
			CloseHandler.closeBrowser();
	} 
}
