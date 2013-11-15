package com.anthonybennis.homeheatingcosttracker;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.View;

/**
 * Test class
 * @author abenniS
 */
public class CostView extends View 
{
    private static final int LARGE_MARGIN = 80;
    private static final int THIN_MARGIN = 20;
    private static final int ONE_MINUTE = 120;
    private static final int SECOND_LINE_LENGTH = 20;
    private static final int COST_TEXT_LENGTH = 60;
    private static long _startTime = -1;
    private Paint _paint = new Paint();
    
    /**
     * Constructor 1
     * 
     * @param context
     * @param set
     */
    public CostView(Context context, AttributeSet set)
    {
        super(context, set);
        this.setBackgroundColor(Color.BLACK);
    }
    
    /**
     * Constructor 2
     * 
     * @param context
     */
    public CostView(Context context)
    {
        super(context);
        this.setBackgroundColor(Color.BLACK);
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
    	
    	// Handle phone orientation
    	int orientation = Utils.getScreenOrientation(this.getContext());
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
         	width = height; // Radius of circles is calculated by width
        }

        
        /*
         * Background
         */
        _paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, canvas.getWidth(), height, _paint);
        
        /*
         * Outer Circle (Gray)
         */
        _paint.setColor(Color.DKGRAY);
        float outerCircleRadius = (width/2) - THIN_MARGIN;
//        _paint.setShadowLayer(outerCircleRadius, 6.0f, 60.0f, Color.BLACK);
        canvas.drawCircle(centerX, centerY, outerCircleRadius, _paint);
        
        _paint.clearShadowLayer();
        /*
         * Inner Circle (Black)
         */
        _paint.setColor(Color.BLACK);
        float innerCircleRadius = outerCircleRadius - THIN_MARGIN;
        canvas.drawCircle(centerX, centerY, innerCircleRadius, _paint);
        
        /*
         * Inner Circle (Blue)
         */
        _paint.setColor(Color.BLUE);
        float innerContentCircleRadius = innerCircleRadius - LARGE_MARGIN;
        canvas.drawCircle(centerX, centerY, innerContentCircleRadius, _paint);
        
        /*
         * Draw minute dashes around blue circle
         */
        if (_startTime != -1)
        {
        	this.paintLinesAroundEdgeOfCircle(canvas, innerContentCircleRadius, centerX, centerY);	
        }
        
        
        /*
         * Draw oil cost
         */
        _paint.setColor(Color.WHITE);
        _paint.setTextSize(48);
        String currentCost = CostCalculartor.calculateCost(_startTime);
        if (_startTime != -1)
        {
        	currentCost = CostCalculartor.calculateCost(_startTime);
        }
        else
        {
        	currentCost = "€0.00";
        }
        
        canvas.drawText(currentCost, (centerX - COST_TEXT_LENGTH), centerY + 15, _paint);
        
        /*
         * Draw Duration
         */
        this.paintDurationOnCurve(canvas, innerCircleRadius, centerX, centerY);
    }
    
    /**
     * Start time (From when Heating is turned on, for example).
     * @param startTime
     */
    protected void setStartTime(long startTime)
    {
    	_startTime = startTime;
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
    private void paintDurationOnCurve(Canvas canvas, float circleRadius, float x, float y)
    {
        Path circle = new Path();
        circle.addCircle(x, y, circleRadius, Direction.CW);
        
        _paint.setStyle(Paint.Style.FILL_AND_STROKE);
        _paint.setColor(Color.RED);
        _paint.setTextSize(20f);
        
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        
        if (_startTime != -1)
        {
	        long duration = System.currentTimeMillis() - _startTime;
	        
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
         * hOffset - Start position of text (on circle).
         * 30f - Horizontal position of text
         */
        double hoffsetD = (1.5f*Math.PI*circleRadius);
        Double d = Double.valueOf(hoffsetD);
        float hOffset =  d.floatValue();
        hOffset = hOffset - 60;
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
        
        
//        for (int i = 0; i < array.length; i++) {
		
        // for (double a=0,aMax=(2*Math.PI),aStep=(Math.PI/ONE_MINUTE); a<aMax; a+=aStep)
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
}