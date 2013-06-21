package com.anthonybennis.largebookmarkwidget;

import java.util.HashMap;
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
import android.widget.Toast;

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
		String[] defaultBrowserBookmarks = this.getDefaultBrowserBookmars();
//		String[] chromeBrowserBookmarks = this.getChromeBookmarks();
		
		/*
		 * Add two arrays together.
		 */
//		 String[] result = Arrays.copyOf(defaultBrowserBookmarks, defaultBrowserBookmarks.length + chromeBrowserBookmarks.length);
//		 System.arraycopy(chromeBrowserBookmarks, 0, result, defaultBrowserBookmarks.length, chromeBrowserBookmarks.length);
		
		return defaultBrowserBookmarks;
	}
	
	/**
	 * 
	 * @return
	 */
	private String[] getDefaultBrowserBookmars()
	{
		return this.getBookmarks(Browser.BOOKMARKS_URI);
	}
	
	/**
	 * 
	 * @return
	 */
	private String[] getChromeBookmarks()
	{
		Uri uriCustom = Uri.parse("content://com.android.browser/bookmarks");
		return  this.getBookmarks(uriCustom);
	}
	
	private String[] getBookmarks(Uri uriCustom)
	{
		String[] chromebookmarks = new String[0];
		
		String[] proj = new String[] { Browser.BookmarkColumns.TITLE,Browser.BookmarkColumns.URL };
		Toast.makeText(this.getApplicationContext(), "Proj[0] = " + proj[0], Toast.LENGTH_LONG).show();
		Toast.makeText(this.getApplicationContext(), "Proj[1] = " + proj[1], Toast.LENGTH_LONG).show();
		
		String sel = Browser.BookmarkColumns.BOOKMARK + " = 1"; // 0 = history, 1 = bookmark
		Toast.makeText(this.getApplicationContext(), "sel: " + sel, Toast.LENGTH_LONG).show();
		
		Cursor mCur = getContentResolver().query(uriCustom, proj, sel, null, null);
		
		if (mCur != null)
		{
			mCur.moveToFirst();
			
			String title = "";
			String url = "";
	
			if (mCur.moveToFirst() && mCur.getCount() > 0) 
			{
			    boolean cont = true;
			    while (mCur.isAfterLast() == false && cont) 
			    {
			        title = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.TITLE));
			        url = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.URL));
					
			        _bookmarksList.put(url,title);
			        
			        mCur.moveToNext();
			    }
			}
		}
		
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