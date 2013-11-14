package com.anthonybennis.homeheatingcosttracker;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
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
    private Paint _paint = new Paint();
    
    public CostView(Context context, AttributeSet set)
    {
        super(context, set);
        this.setBackgroundColor(Color.BLACK);
    }
    
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
        
        /*
         * Background
         */
        _paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), _paint);
        
        
        /*
         * Outer Circle (Gray)
         */
        _paint.setColor(Color.DKGRAY);
        float outerCircleRadius = (canvas.getWidth()/2) - THIN_MARGIN;
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
         * TODO AB Animate this per second
         */
        this.paintLinesAroundEdgeOfCircle(canvas, innerContentCircleRadius, centerX, centerY);
        
        /*
         * Draw oil cost
         */
        _paint.setColor(Color.WHITE);
        _paint.setTextSize(48);
        // TODO AB: Calculate cost based on time app running and cost of oil.
        // TODO € symbol should be changeable in settings.
        canvas.drawText("€4.58", (centerX - COST_TEXT_LENGTH), centerY + 15, _paint);
        
        /*
         * Draw Duration
         */
        this.paintTextOnCurve("1 hour - 5 minutes", canvas, innerCircleRadius, centerX, centerY);
    }
    
    private void paintTextOnCurve(String text, Canvas canvas, float circleRadius, float x, float y)
    {
        Path circle = new Path();
        circle.addCircle(x, y, circleRadius, Direction.CW);
        
        _paint.setStyle(Paint.Style.FILL_AND_STROKE);
        _paint.setColor(Color.RED);
        _paint.setTextSize(20f);
          
        canvas.drawTextOnPath("Time is " + System.currentTimeMillis(), circle, 60f, 20f, _paint); 
    }
    
    /**
     * Draws a white line for every second in a minute. 
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
        
        for (double a=0,aMax=(2*Math.PI),aStep=(Math.PI/ONE_MINUTE); a<aMax; a+=aStep)
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
