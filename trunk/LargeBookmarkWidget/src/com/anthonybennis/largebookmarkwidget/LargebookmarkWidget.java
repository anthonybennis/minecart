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
	private static final int MAX_URL_LENGTH = 25;// actually used for title
	private static final int MAX_TITLE_LENGTH = 40; // actually used for url
	private static final String ACTION =  "OPEN_WEB_PAGE";
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
	{			
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);

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
		LargebookmarkWidget.addClickListenerToWidget(context, views, id);
		LargebookmarkWidget.setWidgetTextFromPreferences(views, context, id);
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
		clickIntent.setAction(ACTION);
		clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
	}

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Bundle extras = intent.getExtras();
		if (extras != null) 
		{
			int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			
			if (intent.getAction().equals(ACTION)) 
			{
				/*
				 * Launch web page.
				 */
				String[] urlAndTitle = LargebookmarkWidget.getBookmarkURLAndTitleForWidget(context, widgetId);
				String bookmarkUrl = urlAndTitle[0];

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
				LargebookmarkWidget.setWidgetTextFromPreferences(null, context, widgetId);
				super.onReceive(context, intent);
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
	private static void setWidgetTextFromPreferences(RemoteViews views, Context context, int widgetID)
	{
		String[] bookmarkAndTitle = LargebookmarkWidget.getBookmarkURLAndTitleForWidget(context, widgetID);
		
		if (views == null)
		{
			views = new RemoteViews(context.getPackageName(), R.layout.main);
		}
		
		String titleText = bookmarkAndTitle[0];
		String urlText = bookmarkAndTitle[1];
		
		
		Toast.makeText(context, "Title: " + titleText, Toast.LENGTH_LONG);
		Toast.makeText(context, "Url: " + urlText, Toast.LENGTH_LONG);
		titleText = makeTitleTextPretty(titleText);
		urlText = makeURLTextPretty(urlText, titleText);
		
		views.setTextViewText(R.id.widget_textview, urlText);
		views.setTextViewText(R.id.url_textview, titleText);
		
		AppWidgetManager.getInstance(context).updateAppWidget(widgetID, views);
	}
	
	/**
	 * 
	 * @param titleText
	 * @return
	 */
	private static String makeTitleTextPretty(String titleText)
	{
		String prettyText = titleText;
		
		if (titleText.length() > MAX_TITLE_LENGTH)
		{
			/*
			 * Trim off www and .com, if it has any.
			 */
			prettyText = trimText(titleText, MAX_TITLE_LENGTH);
		}
		
		return prettyText;
	}
	
	/**
	 * 
	 * @param urlText
	 * @return
	 */
	private static String makeURLTextPretty(String urlText, String titleText)
	{
		String prettyText = urlText;
		
		if (urlText.length() > MAX_URL_LENGTH)
		{
			// Trim off www and .com if needed.
			prettyText = trimText(urlText, MAX_URL_LENGTH);
		}
		
		if (titleText.equals(urlText))
		{
			prettyText = ""; // No need to show duplicate information.
		}
		
		return prettyText;
	}
	
	/**
	 * Removes http://www. and .com (if removeSuffix is true)
	 * @param originalText
	 * @param removeSuffix - If true, .com will be removed. If false, we'll try keep it if maxLenth can be reached.
	 */
	private static String trimText(String originalText,int maxLength)
	{
		String trimmedText = originalText;
		
		String longPrefix = "http://www.";
		String longSafePrefix = "https://www.";
		String shortPrefix = "www.";
		
		trimmedText = originalText.replaceAll(longSafePrefix, "");
		trimmedText = trimmedText.replaceAll(longPrefix, "");
		trimmedText = trimmedText.replaceAll(shortPrefix, "");

			
		if (trimmedText.length() > maxLength)
		{
			trimmedText = trimmedText.substring(0,(maxLength - 3));
			trimmedText = trimmedText + "...";
		}
		
		
		return trimmedText;
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
		 bookmarkAndTitle[0] = settings.getString(CONSTANTS.TITLE + widgetID, "Google");
		 bookmarkAndTitle[1] = settings.getString(CONSTANTS.URL + widgetID, "http://www.google.com");
		 
		 
		 return bookmarkAndTitle;
	}
}