package com.anthonybennis.largebookmarkwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * 
 * @author Anthony
 */
public class LargebookmarkWidget extends AppWidgetProvider 
{
	public static String OPEN_WEBPAGE_ACTION = "OPEN";
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) 
	{
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
		
		for (int id : appWidgetIds) 
		{
			Toast.makeText(context, "Update Widget id "+ id, Toast.LENGTH_LONG).show();
			
			Intent clickIntent = new Intent(context, LargebookmarkWidget.class);
			clickIntent.setAction(OPEN_WEBPAGE_ACTION);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, clickIntent, 0);
			views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
			
			AppWidgetManager.getInstance(context).updateAppWidget(id, views);	
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) 
	{		
		Toast.makeText(context, "OnRecieve called", Toast.LENGTH_LONG).show();
		
		//
		
		if (intent != null)
		{
			 Bundle extras = intent.getExtras();
			 
			 if (extras != null)
			 {
				  String bookmarkUrl = extras.getString(CONSTANTS.URL);
				  Toast.makeText(context, "Bookmark URL is: " + bookmarkUrl, Toast.LENGTH_LONG).show();
			 }
		}
		//
		
		 if (intent.getAction()==null) 
		 {
			 Toast.makeText(context, "Intent Action is not null", Toast.LENGTH_LONG).show();
		        Bundle extras = intent.getExtras();
		        if(extras!=null) 
		        {		        	
		            int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		            // do something for the widget that has appWidgetId = widgetId
		            Toast.makeText(context, "Extras are not null for " + widgetId, Toast.LENGTH_LONG).show();
		            
		            /*
		             * Launch web page.
		             */
		            String bookmarkUrl = extras.getString(CONSTANTS.URL);
		            String bookmarkTitle = extras.getString(CONSTANTS.TITLE);
		            
		            Toast.makeText(context, "bookmarkUrl is: " + bookmarkUrl, Toast.LENGTH_LONG).show();
		            
		            if (bookmarkUrl != null)
		            {
			    		if (!bookmarkUrl.startsWith("https://") && !bookmarkUrl.startsWith("http://"))
			    		{
			    			bookmarkUrl = "http://" + bookmarkUrl;
			    		}
			    		
			    		Toast.makeText(context, "Launching Browser page: " + bookmarkUrl, Toast.LENGTH_LONG).show();
			    		Intent i = new Intent(Intent.ACTION_VIEW);
			    		i.setData(Uri.parse(bookmarkUrl));
			    		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    		context.startActivity(i);
		            }
		            else
		            {
		            	 Toast.makeText(context, "bookmarkUrl is: " + bookmarkUrl, Toast.LENGTH_LONG).show();
		            }
		    	}
		 }
		 else
		 {
			 super.onReceive(context, intent);
		 }
	}
}
