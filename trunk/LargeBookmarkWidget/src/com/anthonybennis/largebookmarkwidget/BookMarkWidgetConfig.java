package com.anthonybennis.largebookmarkwidget;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
	    String[] bookmarkURLs = this.getBookmarks();
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
				 Toast.makeText(getApplicationContext(), "selectedBookmark is" + selectedBookmark, Toast.LENGTH_LONG).show();
				 if (selectedBookmark instanceof String)
				 {
					 String selectedURL = (String)selectedBookmark;
					 Toast.makeText(getApplicationContext(), "Settings Extras::::: " + selectedURL, Toast.LENGTH_LONG).show();
					 resultValue.putExtra(CONSTANTS.URL, selectedURL);
					 resultValue.putExtra(CONSTANTS.TITLE, "A bookmark title");
					 setResult(RESULT_OK, resultValue);
					 
					 /*
			          * Update widget
			          */
					 Context context = getApplicationContext();
					 Toast.makeText(context, "Coinfiguration complete. Update widget " + _appWidgetId + " with bookmark: " + selectedURL, Toast.LENGTH_LONG).show();
					 
					 RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
					 AppWidgetManager.getInstance(context).updateAppWidget(_appWidgetId, views);
				 }
				 finish();
	         }
	   });
	  
	     /*
	      * Not sure what this is about or if it's needed?
	      */
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
	 * 
	 * @return
	 */
	private String[] getBookmarks()
	{
		String[] bookmarks = new String[0];
		
		  String[] proj = new String[] { Browser.BookmarkColumns.TITLE, Browser.BookmarkColumns.URL };
		    String sel = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history, 1 = bookmark
		    Cursor mCur = this.managedQuery(Browser.BOOKMARKS_URI, proj, sel, null, null);
		    this.startManagingCursor(mCur);
		    mCur.moveToFirst();

		    String title = "";
		    String url = "";

		    if (mCur.moveToFirst() && mCur.getCount() > 0) 
		    {
		        while (mCur.isAfterLast() == false) 
		        {
		            title = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.TITLE));
		            url = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.URL));
		            _bookmarksList.put(url,title);
		            mCur.moveToNext();
		        }
		    }
		    
		    Set<String> keys = _bookmarksList.keySet();
		    bookmarks = new String[_bookmarksList.size()];
		    bookmarks = keys.toArray(bookmarks);
		    
		    return bookmarks;
	}
}