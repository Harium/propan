package com.harium.propan.core.model;

import com.badlogic.gdx.math.Vector3;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {

    private static final float EPSILON = 0.1f;
    Model model;

    @Before
    public void setUp() {
        model = new Model();
    }

    @Test
    public void testCentroid() {
        model.addVertex(new Vector3(1,1,1));
        model.addVertex(new Vector3(-1,-1,-1));

        Vector3 centroid = model.centroid();
        Assert.assertEquals(0, centroid.x, EPSILON);
        Assert.assertEquals(0, centroid.y, EPSILON);
        Assert.assertEquals(0, centroid.z, EPSILON);
    }

    @Test
    public void testRotate() {
        float angle = 90;

        model.addVertex(new Vector3(1,0,0));
        model.rotate(Vector3.Y, angle);

        Assert.assertEquals(0, model.getVertices().get(0).x, EPSILON);
        Assert.assertEquals(0, model.getVertices().get(0).y, EPSILON);
        Assert.assertEquals(-1, model.getVertices().get(0).z, EPSILON);
    }

    @Test
    public void testRotateWithOrigin() {
        float angle = 90;

        model.addVertex(new Vector3(1,0,0));
        model.rotate(Vector3.Y, angle, new Vector3(1,0,0));

        Assert.assertEquals(1, model.getVertices().get(0).x, EPSILON);
        Assert.assertEquals(0, model.getVertices().get(0).y, EPSILON);
        Assert.assertEquals(-1, model.getVertices().get(0).z, EPSILON);
    }

    @Test
    public void testScale() {
        model.addVertex(new Vector3(1,1,1));
        model.scale(2);

        Assert.assertEquals(2, model.getVertices().get(0).x, EPSILON);
        Assert.assertEquals(2, model.getVertices().get(0).y, EPSILON);
        Assert.assertEquals(2, model.getVertices().get(0).z, EPSILON);
    }


    @Test
    public void testTranslate() {
        model.addVertex(new Vector3(0,0,0));
        model.translate(1,0,0);

        Assert.assertEquals(1, model.getVertices().get(0).x, EPSILON);
        Assert.assertEquals(0, model.getVertices().get(0).y, EPSILON);
        Assert.assertEquals(0, model.getVertices().get(0).z, EPSILON);
    }

}
