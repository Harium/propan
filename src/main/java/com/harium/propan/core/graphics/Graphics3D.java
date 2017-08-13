package com.harium.propan.core.graphics;

import com.harium.propan.linear.AimPoint;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.etyl.linear.Point3D;
import com.badlogic.gdx.math.Vector3;

public interface Graphics3D extends Graphics, GLGraphics, GLMatrixFunc {

    //Drawing Methods
    void drawLine(Point3D a, Point3D b);

    void drawLine(Point3D a, Vector3 b);

    void drawLine(Vector3 a, Vector3 b);

    void drawSphere(Vector3 point, double radius);

    void drawSphere(AimPoint point, double radius);

    void aimCamera(AimPoint aim);

    void drawGrid(float size, int width, int height);
}
