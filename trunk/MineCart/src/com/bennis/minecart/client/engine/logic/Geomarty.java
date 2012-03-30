/*******************************************************************************
 * Copyright (c) 2012 Anthony Bennis
 * All rights reserved. This program and the accompanying materials
 * are fully owned by Anthony Bennis and cannot be used for commercial projects without prior permission from same.
 * http://www.anthonybennis.com
 *
 * Contributors:
 * Anthony Bennis.
 *******************************************************************************/
package com.bennis.minecart.client.engine.logic;

import com.bennis.minecart.client.engine.model.Line;

/**
 * Some useful methods for gemetric calculations
 * @author abennis
 *
 */
public class Geomarty 
{
	 public static double angleBetween2Lines(Line line1, Line line2)
	 {
	        double angle1 = Math.atan2(line1.getY() - line1.getY1(),
	                                   line1.getX() - line1.getX1());
	        double angle2 = Math.atan2(line2.getY() - line2.getY1(),
	                                   line2.getX() - line2.getX1());
	        return angle1-angle2;
	 }
}
