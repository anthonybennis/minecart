package com.bennis.minecart.client.engine.model;


/**
 * Represents a rectangle.
 */
public class Rectangle
{
        private double x, y, width, height;
        
        /**
         * Constructor
         */
        public Rectangle() 
        {
        }
        
        public Rectangle(Rectangle rhs) 
        {
                this(rhs.getX(), rhs.getY(), rhs.getWidth(), rhs.getHeight());
        }
        
        public Rectangle(Vector position, double width, double height) 
        {
                this(position.x, position.y, width, height);
        }
        
        public Rectangle(double x, double y, double width, double height) 
        {
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
        }
        
        /**
         * Creates a rectangle at the given center.
         * 
         * @param x x-coordinate of the center of the rectangle.
         * @param y y-coordinate of the center of the rectangle.
         * @param halfWidth half the width of the rectangle.
         * @param halfHeight half the height of the rectangle.
         * @return the rectangle created.
         */
        public static final Rectangle createAtCenter(double x, double y, double halfWidth, 
                        double halfHeight) 
        {
                return new Rectangle(x - halfWidth, y - halfHeight, x + halfWidth, y + halfHeight);
        }
        
        /**
         * Creates a rectangle at the given center.
         * 
         * @param center the center of the rectangle.
         * @param halfWidth half the width of the rectangle.
         * @param halfHeight half the height of the rectangle.
         * @return the rectangle created.
         */
        public static final Rectangle createAtCenter(Vector center, double halfWidth, 
                        double halfHeight) 
        {
                return createAtCenter(center.x, center.y, halfWidth, halfHeight);
        }
        
        /**
         * Gets the top-left x-coordinate of the rectangle.
         */
        public final double getX() 
        {
                return x;
        }
        
        /**
         * Sets the top-left x-coordinate of the rectangle.
         */     
        public void setX(double x) 
        {
                this.x = x;
        }
        
        /**
         * Gets the top-left y-coordinate of the rectangle.
         */
        public final double getY() 
        {
                return y;
        }
        
        /**
         * Sets the top-left y-coordinate of the rectangle.
         */     
        public void setY(double y) 
        {
                this.y = y;
        }
        
        /**
         * Gets the width of the rectangle.
         */
        public final double getWidth() 
        {
                return width;
        }
        
        /**
         * Sets the width of the rectangle.
         */     
        public void setWidth(double width) 
        {
                this.width = width;
        }
        
        /**
         * Gets the height of the rectangle.
         */
        public double getHeight() 
        {
                return height;
        }
        
        /**
         * Sets the height of the rectangle.
         */     
        public void setHeight(double height) 
        {
                this.height = height;
        }
        
        /**
         * Moves the top left corner of this rectangle to (x, y).
         * 
         * @param x
         * @param y
         */
        public final void move(double x, double y) 
        {
                setX(x);
                setY(y);
        }
        
        /**
         * Moves the top left corner of this rectangle to the given position.
         * 
         * @param position the position to move the rectangle to.
         */
        public final void move(Vector position) 
        {
                move(position.x, position.y);
        }
        
        @Override
        public final boolean equals(Object obj) 
        {
                return (obj instanceof Rectangle) ? equals((Rectangle) obj) : false;
        }
        
        public final boolean equals(Rectangle rhs) 
        {
                return getX() == rhs.getX() && getY() == rhs.getY() && getWidth() == rhs.getWidth()
                                && getHeight() == rhs.getHeight();
        }
        
        /**
         * Written by AB
         * 
         * @param rectangle
         * @return true, if rectangles intersect.
         */
        public boolean intersects(Rectangle   r) 
        {
        	int tw = (int)this.width;
        	int th = (int)this.height;
        	int rw = (int)r.width;
        	int rh = (int)r.height;
        	
        	if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) 
        	{
        		return false;
        	}
        	
        	int tx = (int)this.x;
        	int ty = (int)this.y;
        	int rx = (int)r.x;
        	int ry = (int)r.y;
        	rw += rx;
        	rh += ry;
        	tw += tx;
        	th += ty;
        	
        	// overflow || intersect
        	return ((rw < rx || rw > tx) &&
        	(rh < ry || rh > ty) &&
        	(tw < tx || tw > rx) &&
        	(th < ty || th > ry));
        }
        
        /**
         * 
         * @return
         */
        public Vector getCenter()
        {
        	Vector center = new Vector();
        	center.x = (this.getX() + (this.getWidth()/2));
        	center.y = (this.getY() + (this.getHeight()/2));
        	
        	return center;
        }
        
        public boolean contains(double xPos, double yPos) 
        {
        	if(xPos < x)
        	{
        	  return false;
        	}
        	 
        	if(xPos > x + this.getWidth())
        	{
        	  return false;
        	}
        	
        	if(yPos < y)
        	{
        	  return false;
        	}
        	
        	if(yPos > y + this.getHeight())
        	{
        	  return false;
        	}
        	 
        	 return true;
        }
}
