package com.anthonybennis.homeheatingcosttracker;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Test class
 * @author abenniS
 */
public class CostView extends View 
{
    private static final String ICE_CREAM_SANDWHICH_BLUE = "#ff33b5e5";
	private static final int LARGE_MARGIN = 80;
    private static final int THIN_MARGIN = 20;
    private static final int ONE_MINUTE = 120;
    private static final int SECOND_LINE_LENGTH = 20;
    private static final int COST_TEXT_LENGTH = 60;
    private Paint _paint = new Paint();
	private Bitmap _backgroundImage;
	private Rect _sourceRect;
	private Rect _dstRect;
    
    /**
     * Constructor 1
     * 
     * @param context
     * @param set
     */
    public CostView(Context context, AttributeSet set)
    {
        super(context, set);
    }
    
    /**
     * Constructor 2
     * 
     * @param context
     */
    public CostView(Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) 
    {
        super.onDraw(canvas);
        _paint.setAntiAlias(true);
        
        float centerX = canvas.getWidth()/2;
    	float centerY = canvas.getHeight()/2;
    	int width = canvas.getWidth();
    	int height = canvas.getHeight();
    	
    	// Handle Landscape orientation (Flip width and height)
        if (width > height)
        {
        	width = height;
        	height = canvas.getWidth();
        }

        /*
         * Background Image
         */
        if (_backgroundImage == null)
        {
        	_backgroundImage = Utils.loadAndScaleImage(getResources(), R.drawable.background, width, height);
        	_sourceRect = new Rect(0,0,width,height);
            _dstRect = new Rect(0,0, width, height);
        }
        
        canvas.drawBitmap(_backgroundImage, _sourceRect, _dstRect, _paint);
        
        /*
         * Outer Circle (Gray)
         */
        _paint.setColor(Color.DKGRAY);
        float outerCircleRadius = (width/2) - THIN_MARGIN;
        canvas.drawCircle(centerX, centerY, outerCircleRadius, _paint);
        
        
        
        /*
         * Inner Circle (Black)
         */
        _paint.setColor(Color.BLACK);
        float innerCircleRadius = outerCircleRadius - THIN_MARGIN;
        canvas.drawCircle(centerX, centerY, innerCircleRadius, _paint);
        
        /*
         * Inner Circle (Blue)
         */
        _paint.setColor(Color.parseColor(ICE_CREAM_SANDWHICH_BLUE));
        float innerContentCircleRadius = innerCircleRadius - LARGE_MARGIN;
        canvas.drawCircle(centerX, centerY, innerContentCircleRadius, _paint);
        
        /*
         * Draw minute dashes around blue circle
         */
        long startTime = this.getStartTime();
        
        if (startTime != -1)
        {
        	this.paintLinesAroundEdgeOfCircle(canvas, innerContentCircleRadius, centerX, centerY);	
        }
        
        
        /*
         * Draw oil cost
         */
        _paint.setColor(Color.WHITE);
        _paint.setTextSize(48);
        String currentCost = CostCalculartor.calculateCost(startTime);
        if (startTime != -1)
        {
        	currentCost = CostCalculartor.calculateCost(startTime);
        }
        else
        {
        	currentCost = "�0.00";
        }
        
        // TODO AB Scale Text for larger Displays.
        canvas.drawText(currentCost, (centerX - COST_TEXT_LENGTH), centerY + 15, _paint);
        
        /*
         * Draw Duration
         */
        this.paintDurationOnCurve(canvas, innerCircleRadius, startTime, centerX, centerY);
    }
    
    /**
     * Paint Text On Curve
     * 
     * @param text
     * @param canvas
     * @param circleRadius
     * @param x
     * @param y
     */
    private void paintDurationOnCurve(Canvas canvas, float circleRadius, long startTime, float x, float y)
    {
        Path circle = new Path();
        circle.addCircle(x, y, circleRadius, Direction.CW);
        
        _paint.setStyle(Paint.Style.FILL_AND_STROKE);
        _paint.setColor(Color.RED);
        _paint.setTextSize(25f);
        
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        
        if (startTime != -1)
        {
	        long duration = System.currentTimeMillis() - startTime;
	        
	        hours = TimeUnit.MILLISECONDS.toHours(duration);
	        duration -= TimeUnit.HOURS.toMillis(hours);
	        minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
	        duration -= TimeUnit.MINUTES.toMillis(minutes);
	        seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        }

        StringBuilder sb = new StringBuilder(64);
        sb.append(hours);
        sb.append(" hrs ");
        sb.append(minutes);
        sb.append(" min ");
        sb.append(seconds);
        sb.append(" sec");

        String durationAsString = sb.toString();
        
        /*

        * #
        * hOffset - Start position of text (on circle).
         * 30f - Horizontal position of text
         */
        double hoffsetD = (1.5f*Math.PI*circleRadius);
        Double d = Double.valueOf(hoffsetD);
        float hOffset =  d.floatValue();
        hOffset = hOffset - 90;
        canvas.drawTextOnPath(durationAsString, circle, hOffset , 30f, _paint); 
    }
    
    /**
     * Draws a white line for every second in a minute.
     *  
     * @param canvas
     * @param radius
     * @param cx
     * @param cy
     */
    private void paintLinesAroundEdgeOfCircle(Canvas canvas, float radius, float cx, float cy)
    {
        double px1;
        double py1;
        double px2;
        double py2;
        _paint.setColor(Color.WHITE);
        radius = radius - 6; // Small Margin around tick lines.
        
        Date todaysDate = Calendar.getInstance().getTime();
        int seconds = todaysDate.getSeconds();
        int secondCounter = 0;
        seconds = seconds*4; // we've two ticks per second

        for (double aMax=(2*Math.PI), a=0,aStep=(Math.PI/ONE_MINUTE); a<aMax; a-=aStep)
        {
        	secondCounter++;
        	
        	if (secondCounter <= seconds)
        	{
	            px1 = cx+Math.sin(a)*radius;
	            py1 = cy+Math.cos(a)*radius;
	            px2 = cx+Math.sin(a)*(radius-SECOND_LINE_LENGTH);
	            py2 = cy+Math.cos(a)*(radius-SECOND_LINE_LENGTH);
	
	            //draw line between (px1,py1) and (px2,py2)
	            _paint.setStrokeWidth(1f);
	            canvas.drawLine((float)px1, (float)py1, (float)px2, (float)py2, _paint);
        	}
        	else
        	{
        		secondCounter = 0;
        		break;
        	}
        }
    }
    
    /*
     * We store Start time in preferences, as App can be killed
     * at any time.
     */
    private long getStartTime()
    {
    	long startTime = -1;
    	
    	String startTimeString = PreferencesUtil.loadPreference(PreferencesUtil.START_TIME, this.getContext());
    	if (!startTimeString.equals(""))
    	{
    		startTime = Long.parseLong(startTimeString);
    	}
    	
    	return startTime;
    }
}