package com.harium.propan.linear;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.linear.Point3D;

public class ColoredPoint3D extends Point3D {
	
	protected Color color = Color.BLACK;
	
	public ColoredPoint3D() {
		super();
	}
	
	public ColoredPoint3D(double x, double y) {
		super(x, y, 0);
	}
	
	public ColoredPoint3D(double x, double y, double z) {
		super(x, y, z);
	}
	
	public ColoredPoint3D(Point3D point) {
		this(point.getX(), point.getY(), point.getZ());			
	}
	
	public void setCoordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
