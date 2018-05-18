package com.harium.propan.geometry;

import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.commons.graphics.Color;

public class ColoredVector3 extends Vector3 {
	
	protected Color color = Color.BLACK;
	
	public ColoredVector3() {
		super();
	}
	
	public ColoredVector3(float x, float y) {
		super(x, y, 0);
	}
	
	public ColoredVector3(float x, float y, float z) {
		super(x, y, z);
	}
	
	public ColoredVector3(Vector3 point) {
		this(point.x, point.y, point.z);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
