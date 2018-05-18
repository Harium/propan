package com.harium.propan.core.light;

import com.harium.propan.geometry.AimPoint;

public class Lamp extends AimPoint {
	
	protected float intensity;
	
	public Lamp(float x, float y, float z) {
		super(x,y,z);
	}
	
	public float getIntensity() {
		return intensity;
	}
}
