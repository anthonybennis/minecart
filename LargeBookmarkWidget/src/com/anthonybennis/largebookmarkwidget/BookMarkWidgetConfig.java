package com.anthonybennis.largebookmarkwidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;

/**
 * Configuration Activity for LargeBookmarkWidget
 * @author Anthony
 */
public class BookMarkWidgetConfig extends Activity 
{
	private Map<String, String> _bookmarksList = new HashMap<String, String>();
	private int _appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	protected static final String BOOKMARK_TITLE_ID = "BOOKMARK_TITLE";
	protected static final String BOOKMARK_URL_ID = "BOOKMARK_URL";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.setResult(RESULT_CANCELED);
	    this.setContentView(R.layout.config);
	  
	    /*
	     * Populate ListView with Bookmark URLs 
	     */
	    final ListView browserListView = (ListView)findViewById(R.id.browser_list_view);
	    String[] bookmarkURLs = this.getAllBookmarks();
	    ArrayAdapter<String> bookmarkAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bookmarkURLs);
	    
	    browserListView.setAdapter(bookmarkAdapter);
	    browserListView.setOnItemClickListener(new OnItemClickListener()
	    {
	        @Override
	        public void onItemClick(AdapterView<?> a, View v,int position, long id) 
	        {
	             Intent resultValue = new Intent();
				 resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, _appWidgetId);
				 Object selectedBookmark = browserListView.getItemAtPosition(position);
				 Context context = getApplicationContext();
				 
				 if (selectedBookmark instanceof String)
				 {
					 String selectedURL = (String)selectedBookmark;
					 String bookmarkTitle = getBookmarkTitle(selectedURL);

					 /*
					  * Persist user settings
					  */
					 SharedPreferences preferences = getSharedPreferences(CONSTANTS.SHARED_PREFERENCE, 0);
					 SharedPreferences.Editor editor = preferences.edit();
					 editor.putString(CONSTANTS.URL + _appWidgetId, selectedURL);
					 editor.putString(CONSTANTS.TITLE + _appWidgetId, bookmarkTitle);
					 editor.commit();
					 
					 setResult(RESULT_OK, resultValue);
					 
					 /*
			          * Update widget
			          */
					 RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
					 LargebookmarkWidget.addClickListenerAndUpdateWidget(views, context, AppWidgetManager.getInstance(context), _appWidgetId);
				 }
				 
				 finish();
	         }
	   });
	  
	     Intent intent = getIntent(); 
	     Bundle extras = intent.getExtras();
	     
	     if (extras != null) 
	     {
	         _appWidgetId = extras.getInt(
	                 AppWidgetManager.EXTRA_APPWIDGET_ID,
	                 AppWidgetManager.INVALID_APPWIDGET_ID);
	     }
	  
	     // If they gave us an intent without the widget id, just exit.
	     if (_appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) 
	     {
	         finish();
	     }
	}
	
	/**
	 * Gets all bookmarks from all browsers.
	 * TODO AB - Add support for Firefox and Opera browsers.
	 *  
	 * @return String[] - All bookmarks.
	 */
	private String[] getAllBookmarks()
	{
		String filter = Browser.BookmarkColumns.BOOKMARK + " = 1 AND " + Browser.BookmarkColumns.URL + " NOT NULL ";
		String[] defaultBrowserBookmarks = this.getDefaultBrowserBookmars(filter);
		String[] chromeBrowserBookmarks = this.getChromeBookmarks(filter);
		
		/*
		 * Add two arrays together.
		 */
		 String[] result = Arrays.copyOf(defaultBrowserBookmarks, defaultBrowserBookmarks.length + chromeBrowserBookmarks.length);
		 System.arraycopy(chromeBrowserBookmarks, 0, result, defaultBrowserBookmarks.length, chromeBrowserBookmarks.length);
		
		return result;
	}
	
	/**
	 * Get default Android bookmarks
	 * @return
	 */
	private String[] getDefaultBrowserBookmars(String filter)
	{
		 Uri browserBookMarks = android.provider.Browser.BOOKMARKS_URI;
	     Cursor cursor = this.getContentResolver().query(browserBookMarks, null, filter, null, null);
		
		return  this.getBookmarks(cursor);
	}
	
	/**
	 * Get Google Chrome Bookmarks
	 * @return
	 */
	private String[] getChromeBookmarks(String filter)
	{
		Uri chromeBookMarks = Uri.parse("content://com.google.android.apps.chrome.browser/bookmarks");
	    Cursor cursor = this.getContentResolver().query(chromeBookMarks,null, filter, null, null);
		
		return  this.getBookmarks(cursor);
	}
	
	/*
	 * Get bookmarls for a given DB Cursor.
	 */
	private String[] getBookmarks(Cursor cursor)
	{
		String[] chromebookmarks = new String[0];
		List<String> bookmarkTitleList = new ArrayList<String>();
		
		if (cursor != null)
		{
			cursor.moveToFirst();
			
			String title = "";
			String url = "";
	
			if (cursor.moveToFirst() && cursor.getCount() > 0) 
			{
			    boolean cont = true;
			    while (cursor.isAfterLast() == false && cont) 
			    {
			        title = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.TITLE));
			        url = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.URL));
					
			        _bookmarksList.put(title,url);
			        bookmarkTitleList.add(title);
			        
			        cursor.moveToNext();
			    }
			}
		}
		
		chromebookmarks = (String[])bookmarkTitleList.toArray(chromebookmarks);
		return chromebookmarks;
	}
	
	/**
	 * Returns the title of a given Bookmark URL
	 * @return String (Browser bookmark title).
	 */
	private String getBookmarkTitle(String urlKey)
	{
		String title = "";
		
		Object bookmarkTitle = _bookmarksList.get(urlKey);
		
		if (bookmarkTitle instanceof String)
		{
			title = (String)bookmarkTitle;
		}
		
		return title;
	}
}