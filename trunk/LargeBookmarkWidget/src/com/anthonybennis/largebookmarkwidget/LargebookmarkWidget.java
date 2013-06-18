package com.anthonybennis.largebookmarkwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * LargebookmarkWidget
 * @author Anthony
 */
public class LargebookmarkWidget extends AppWidgetProvider 
{
	public static String OPEN_WEBPAGE_ACTION = "OPEN";
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
	{
		Toast.makeText(context, "onUpdate: "+ appWidgetIds, Toast.LENGTH_SHORT).show();
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
		Toast.makeText(context, "There are " + appWidgetIds.length + " widgets to update.", Toast.LENGTH_SHORT).show();
		for (int id : appWidgetIds) 
		{
			LargebookmarkWidget.addClickListenerAndUpdateWidget(views, context, appWidgetManager, id);
		}
	}
	
	/**
	 * adds ClickListener And Updates Widget text from SharedPreferences.
	 * 
	 * @param context
	 * @param appWidgetManager
	 * @param id
	 */
	public static void addClickListenerAndUpdateWidget(RemoteViews views, Context context, AppWidgetManager appWidgetManager, int id)
	{
		Toast.makeText(context, "addClickListenerAndUpdateWidget: "+ id, Toast.LENGTH_SHORT).show();
		
		LargebookmarkWidget.addClickListenerToWidget(context, views, id);
		LargebookmarkWidget.setWidgetTextFromPreferences(context, id);
			
	}
	
	/**
	 * Adds a Click listener to the widgets layout.
	 * 
	 * @param context
	 * @param views
	 * @param id - ID of widget you want to add click listener to.
	 */
	private static void addClickListenerToWidget(Context context, RemoteViews views, int id)
	{
		Intent clickIntent = new Intent(context, LargebookmarkWidget.class);
		clickIntent.setAction(OPEN_WEBPAGE_ACTION);
		clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, clickIntent, 0);
		views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
		
		Toast.makeText(context, "Click Listener added to " + id, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Toast.makeText(context, "OnRecieve called.", Toast.LENGTH_SHORT).show();
		super.onReceive(context, intent);
		
		Bundle extras = intent.getExtras();
		if (extras != null) 
		{
			int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			
			if (intent.getAction().equals(OPEN_WEBPAGE_ACTION)) 
			{
				/*
				 * Launch web page.
				 */
				String[] urlAndTitle = LargebookmarkWidget.getBookmarkURLAndTitleForWidget(context, widgetId);
				String bookmarkUrl = urlAndTitle[CONSTANTS.BOOKMARK_URL_INDEX];

				if (bookmarkUrl != null) 
				{
					if (!bookmarkUrl.startsWith("https://")	&& !bookmarkUrl.startsWith("http://")) 
					{
						bookmarkUrl = "http://" + bookmarkUrl;
					}

					this.openWebPage(context, bookmarkUrl);
				}
			} 
			else 
			{
				LargebookmarkWidget.setWidgetTextFromPreferences(context, widgetId);
			}
		}
	}
	
	/**
	 * Opens a web page on Androids current default Browser.
	 * Note, this starts a new Activity from the context.
	 * 
	 * @param context
	 * @param url
	 */
	private void openWebPage(Context context, String url)
	{
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
	
	/**
	 * Sets the Title and URL text of the widget from the SharedPreferences.
	 * 
	 * @param context
	 * @param widgetID
	 */
	private static void setWidgetTextFromPreferences(Context context, int widgetID)
	{
		String[] bookmarkAndTitle = LargebookmarkWidget.getBookmarkURLAndTitleForWidget(context, widgetID);
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
		
		/*
		 * TODO AB - Truncate the text so it fits nicely on the widget.
		 */
		views.setTextViewText(R.id.url_textview, bookmarkAndTitle[0]);
		views.setTextViewText(R.id.widget_textview, bookmarkAndTitle[1]);
		
		AppWidgetManager.getInstance(context).updateAppWidget(widgetID, views);
	}
	
	/**
	 * Reads the Bookmark URL and Title from the SharedPreferences.
	 * 
	 * @param context - Widget Context.
	 * @param widgetID - ID of widget you want to get URL and title for.
	 * @return String[] - String[[CONSTANTS.BOOKMARK_URL_INDEX] = url, String[[CONSTANTS.BOOKMARK_TITLE_INDEX] = title
	 */
	private static String[] getBookmarkURLAndTitleForWidget(Context context, int widgetID)
	{
		 String[] bookmarkAndTitle = new String[2];
		 
		 SharedPreferences settings = context.getSharedPreferences(CONSTANTS.SHARED_PREFERENCE, 0);
		 bookmarkAndTitle[CONSTANTS.BOOKMARK_URL_INDEX] = settings.getString(CONSTANTS.URL + widgetID, "http://www.google.com");
		 bookmarkAndTitle[CONSTANTS.BOOKMARK_TITLE_INDEX] = settings.getString(CONSTANTS.TITLE + widgetID, "Google");
		 
		 return bookmarkAndTitle;
	}
}