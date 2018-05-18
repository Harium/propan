package com.harium.propan.geometry;

import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.commons.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuripourre
 * @license LGPLv3
 */

public class Face3D {

    protected Color color;
    protected List<Vector3> points;

    public Face3D() {
        color = Color.BLACK;
        points = new ArrayList<>(3);
    }

    public void addPoint(int x, int y) {
        points.add(new ColoredVector3(x, y));
    }

    public void addPoint(int x, int y, int z) {
        points.add(new ColoredVector3(x, y, z));
    }

    public void addPoint(ColoredVector3 p) {
        points.add(p);
    }

    public int sides() {
        return points.size();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(int r, int g, int b) {
        color = new Color(r, g, b);
    }

    public void offset(float offsetX, float offsetY) {
        for (Vector3 point : points) {
            point.x += offsetX;
            point.y += offsetY;
        }
    }

    public void offset(float offsetX, float offsetY, float offsetZ) {
        for (Vector3 point : points) {
            point.x += offsetX;
            point.y += offsetY;
            point.z += offsetZ;
        }
    }

    /**
     * Spin by angle based on http://ca.answers.yahoo.com/question/index?qid=20100403151916AAbJHxV
     *
     * @param angle
     */
    public void rotate(Vector3 axis, float degrees) {

        for (Vector3 point : points) {
            point.rotate(axis, degrees);
        }
    }

    public List<Vector3> getPoints() {
        return points;
    }

    @Override
    public String toString() {

        String text = "";

        for (int p = 0; p < points.size(); p++) {
            Vector3 point = points.get(p);
            text += "Point " + Integer.toString(p) + ": " + point.toString();
            text += "\n";
        }

        return text;
    }

}
