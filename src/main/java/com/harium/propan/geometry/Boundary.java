package com.harium.propan.geometry;

import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Based on Dean Povey's answer at: http://stackoverflow.com/questions/8721406/how-to-determine-if-a-point-is-inside-a-2d-convex-polygon
 *
 * @license LGPLv3
 */

public class Boundary {

    private Vector3 position = new Vector3();

    protected List<Vector3> points = new ArrayList<>(); // Points making up the boundary

    public Boundary() {
        super();
    }

    public Boundary(int x, int y) {
        super();
        position.set(x, y, 0);
    }

    public Boundary(int x, int y, int z) {
        super();
        position.set(x, y, z);
    }

    public void add(float x, float y, float z) {
        add(new Vector3(x, y, z));
    }

    public void add(Vector3 vertex) {
        points.add(vertex);
    }

    /**
     * Return true if the given point is contained inside the boundary.
     * See: http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
     *
     * @param x - x vertex
     * @param y - y vertex
     * @return true if the point is inside the boundary, false otherwise
     */
    public boolean contains(int x, int y) {
        int i, j;
        boolean result = false;

        float px = x - position.x;
        float py = y - position.y;

        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).x > py) != (points.get(j).y > py) &&
                    (px < (points.get(j).x - points.get(i).x) * (py - points.get(i).y) / (points.get(j).y - points.get(i).y) + points.get(i).x)) {
                result = !result;
            }
        }

        return result;
    }

    public void draw(Graphics g) {
        int i;

        for (i = 0; i < points.size() - 1; i++) {
            g.drawLine((int) (position.x + points.get(i).x), (int) (position.y + points.get(i).y), (int) (position.x + points.get(i + 1).x), (int) (position.y + points.get(i + 1).y));
        }

        g.drawLine((int) (position.x + points.get(i).x), (int) (position.y + points.get(i).y), (int) (position.x + points.get(0).x), (int) (position.y + points.get(0).y));

    }

    public List<Vector3> getPoints() {
        return points;
    }

    public void setPoints(List<Vector3> points) {
        this.points = points;
    }


}
