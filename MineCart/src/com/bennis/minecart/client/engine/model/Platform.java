package com.bennis.minecart.client.engine.model;

import java.util.List;

import com.bennis.minecart.client.GUIConstants;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.touch.client.Point;

/**
 * This class represents the ground a sprite can walk on.
 * It's an array of lines. This can be one straight line,
 * or it can be a number of line segments joined together
 * to form one platform.
 * 
 * @author abennis
 */
abstract public class Platform implements ISprite 
{
	private static final int LINE_THICKNESS = 3;
	private Vector _location = new Vector();
	private boolean _selected = false;
	private boolean _disposed = false;
	private double _width = 0;
	List<Line> _lineSegments; // TODO AB Use 2D int array instead of objs.
	
	public Platform()
	{
		_lineSegments = this.createLineSegments();
		this.setupInitialLocation();
		_width = this.calculateWidth();
	}
	
	/**
	 * The initial location of this Sprite is the first x,y
	 * coordingates of the first line in the line segments.
	 */
	private void setupInitialLocation()
	{
		Line firstLine = _lineSegments.get(0);
		this.setLocation(firstLine.getX(), firstLine.getY());
	}
	
	/**
	 * Calculates the entire width of all segments
	 * @return
	 */
	private double calculateWidth()
	{
		double width = 0;
		
		for (Line line : _lineSegments) 
		{
			width = width + (line.getX1() - line.getX());
		}
		
		return width;
	}
	
	/**
	 * Gets the end point (x1) of all the lines in
	 * the line segments List.
	 * @return
	 */
	private double getEndPoint()
	{
		int lastLineIndex = _lineSegments.size()-1;
		Line lastLine = _lineSegments.get(lastLineIndex);
		return lastLine.getX1();
	}
	
	@Override
	public Vector getLocation() 
	{
		return _location;
	}

	@Override
	public void setLocation(Vector vector) 
	{
		_location = vector;
	}
	
	public void setLocation(double x, double y) 
	{
		_location.x = x;
		_location.y = y;
	}
	
	/**
	 * Scrolls the platform to the left.
	 */
	private void scrollLeft(int newXPosition)
	{
		int scrollAmount = (int)this.getLocation().x - newXPosition;
		
		for (Line line : _lineSegments) 
		{
			line.setX(line.getX() - scrollAmount);
			line.setX1(line.getX1() - scrollAmount);
		}
		
		this.setLocation(newXPosition, this.getLocation().y);
	}

	@Override
	public boolean isSelected() 
	{		
		return _selected;
	}
	
	@Override
	public boolean isDisposed() 
	{
		return _disposed;
	}
	
	@Override
	public void dispose() 
	{
		_disposed = true;	
	}

	@Override
	public void draw(Canvas canvas) 
	{
		for (Line line: _lineSegments) 
		{
			canvas.getContext2d().beginPath();
			canvas.getContext2d().setStrokeStyle("white");
			canvas.getContext2d().setLineWidth(LINE_THICKNESS);
			canvas.getContext2d().moveTo(line.getX(), line.getY());
			canvas.getContext2d().lineTo(line.getX1(), line.getY1());
			canvas.getContext2d().closePath();
			canvas.getContext2d().stroke();
		}
	}

	@Override
	public void update(InputEvent event) 
	{
		/*
		 * TODO AB
		 * X position should be managed by one ScrollingController, that
		 * updates all speeds. 
		 * This way, we can implement automatic scrolling, as in MineCart,
		 * Or scrolling based on Sprites position, user movements.
		 */
		if (!this.isDisposed() && _lineSegments != null)
		{
			double x = this.getLocation().x;
			// TODO AB - Make speed setable. Faster when levels progress.
			x = x - GUIConstants.MEDIUM_SCROLL_SPEED;
			
			/*
			 * Dispose Sprite if it's left the screen.
			 */
			if (x < (0 - this.getEndPoint()))
			{
//				this.dispose();
			}
			else
			{
				this.scrollLeft((int)x);
			}
		}
	}

	@Override
	public void handleCollision(ISprite collisionSprite,Collision collisionType) 
	{
		/*
		 * Nothing to do here. Platform doesn't collide with anything,
		 * Spites collide with this.
		 */
	}

	@Override
	public Layers getLayer() 
	{
		return Layers.MIDDLE;
	}
	
	/**
	 * Create a List<Line>. These
	 * will then be used to draw lines, and be used by
	 * other Sprites to find if they contact the platform.
	 * 
	 * @return List<Vector>
	 */
	abstract public List<Line> createLineSegments();

	@Override
	public Collision getCollisionType(ISprite sprite) 
	{
		return Collision.NONE;
	}
	
	@Override
	public Type getType() 
	{
		return Type.PLATFORM;
	}

	@Override
	public Rectangle getBounds() 
	{
		return new Rectangle(this.getLocation().x, this.getLocation().y, _width,1);
	}
	
	/**
	 * Returns the point where a given line intersects with this Platform.
	 * Useful when you want a sprite to align to a platform, or for
	 * collision.
	 * 
	 * @param line
	 * @return
	 */
	public Point getIntersectionPointOfVerticalLine(Line line)
	{
		/*
		 * Get the LineSegment
		 */
		Line lineSegment = this.getLineSegmentInRangeOfLine(line); // TODO AB - How do we get the current Line segement?
		
		/*
		 * Get intersection piint
		 * TODO AB - Which line do we pass as a paramater first? Try the arg line
		 */
		return Line.getLineLineIntersection(line.getX(), line.getY(), line.getX1(), line.getY1(), lineSegment.getX(), lineSegment.getY(), lineSegment.getX1(), lineSegment.getY1());
	}
	
	/**
	 * Finds the horizontal line segment of this Platform that
	 * is within range of the verticalLine.
	 * 
	 * Assumption: verticalLine X is = verticalLine X1
	 * 
	 * @param line
	 * @return Line
	 */
	private Line getLineSegmentInRangeOfLine(Line verticalLine)
	{
		Line lineInRange = null;
		
		for (Line line : _lineSegments) 
		{
			if (line.getX() <= verticalLine.getX() && line.getX1() >= verticalLine.getX())
			{
				lineInRange = line;
				break;
			}
		}
		
		return lineInRange;
	}
}