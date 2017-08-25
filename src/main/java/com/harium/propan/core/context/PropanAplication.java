package com.harium.propan.core.context;

import com.harium.propan.core.graphics.Graphics3D;


public interface PropanAplication {

	void init(Graphics3D g);
	
	void preDisplay(Graphics3D g);
	
	void display(Graphics3D g);
	
	void dispose(Graphics3D g);
	
	void reshape(Graphics3D g, int x, int y, int width, int height);
	
}
