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
        model.addVertex(new Vector3(1, 1, 1));
        model.addVertex(new Vector3(-1, -1, -1));

        Vector3 centroid = model.centroid();
        Assert.assertEquals(0, centroid.x, EPSILON);
        Assert.assertEquals(0, centroid.y, EPSILON);
        Assert.assertEquals(0, centroid.z, EPSILON);
    }

    @Test
    public void testRotate() {
        float angle = 90;

        model.addVertex(new Vector3(1, 0, 0));
        model.rotate(Vector3.Y, angle);

        Assert.assertEquals(0, model.getVertices().get(0).x, EPSILON);
        Assert.assertEquals(0, model.getVertices().get(0).y, EPSILON);
        Assert.assertEquals(-1, model.getVertices().get(0).z, EPSILON);
    }

    @Test
    public void testRotateWithOrigin() {
        float angle = 90;

        model.addVertex(new Vector3(2, 0, 0));
        model.rotate(Vector3.Y, angle, new Vector3(1, 0, 0));

        Assert.assertEquals(1, model.getVertices().get(0).x, EPSILON);
        Assert.assertEquals(0, model.getVertices().get(0).y, EPSILON);
        Assert.assertEquals(-1, model.getVertices().get(0).z, EPSILON);
    }

    @Test
    public void testScale() {
        model.addVertex(new Vector3(1, 1, 1));
        model.scale(2);

        Assert.assertEquals(2, model.getVertices().get(0).x, EPSILON);
        Assert.assertEquals(2, model.getVertices().get(0).y, EPSILON);
        Assert.assertEquals(2, model.getVertices().get(0).z, EPSILON);
    }


    @Test
    public void testTranslate() {
        model.addVertex(new Vector3(0, 0, 0));
        model.translate(1, 0, 0);

        Assert.assertEquals(1, model.getVertices().get(0).x, EPSILON);
        Assert.assertEquals(0, model.getVertices().get(0).y, EPSILON);
        Assert.assertEquals(0, model.getVertices().get(0).z, EPSILON);
    }

    @Test
    public void testTriangulateSquare() {
        int[] squareOrder = new int[]{0, 1, 2, 3};
        Face square = new Face(4);
        square.vertexIndex = squareOrder;
        square.normalIndex = squareOrder;
        square.textureIndex = squareOrder;

        model.getFaces().add(square);
        Assert.assertEquals(1, model.getFaces().size());

        model.triangulate();
        Assert.assertEquals(2, model.getFaces().size());
        Assert.assertEquals(3, model.getFaces().get(0).getSides());
        Assert.assertEquals(3, model.getFaces().get(1).getSides());

        Assert.assertArrayEquals(new int[]{0, 1, 3}, model.getFaces().get(0).vertexIndex);
        Assert.assertArrayEquals(new int[]{0, 1, 3}, model.getFaces().get(0).normalIndex);
        Assert.assertArrayEquals(new int[]{0, 1, 3}, model.getFaces().get(0).textureIndex);

        Assert.assertArrayEquals(new int[]{1, 2, 3}, model.getFaces().get(1).vertexIndex);
        Assert.assertArrayEquals(new int[]{1, 2, 3}, model.getFaces().get(1).normalIndex);
        Assert.assertArrayEquals(new int[]{1, 2, 3}, model.getFaces().get(  1).textureIndex);
    }

    @Test
    public void testTriangulateGroup() {
        int[] squareOrder = new int[]{0, 1, 2, 3};
        Face square = new Face(4);
        square.vertexIndex = squareOrder;
        square.normalIndex = squareOrder;
        square.textureIndex = squareOrder;

        Group group = new Group("square");
        group.getFaces().add(square);

        model.getGroups().add(group);
        Assert.assertEquals(1, group.getFaces().size());

        model.triangulate();
        Assert.assertEquals(2, group.getFaces().size());
        Assert.assertEquals(3, group.getFaces().get(0).getSides());
        Assert.assertEquals(3, group.getFaces().get(1).getSides());

        Assert.assertArrayEquals(new int[]{0, 1, 3}, group.getFaces().get(0).vertexIndex);
        Assert.assertArrayEquals(new int[]{0, 1, 3}, group.getFaces().get(0).normalIndex);
        Assert.assertArrayEquals(new int[]{0, 1, 3}, group.getFaces().get(0).textureIndex);

        Assert.assertArrayEquals(new int[]{1, 2, 3}, group.getFaces().get(1).vertexIndex);
        Assert.assertArrayEquals(new int[]{1, 2, 3}, group.getFaces().get(1).normalIndex);
        Assert.assertArrayEquals(new int[]{1, 2, 3}, group.getFaces().get(  1).textureIndex);
    }


    /*@Test
    public void testTriangulatePolygon() {
        model.getVertices().add(new Vector3(0,0,0));
        model.getVertices().add(new Vector3(1,1,0));
        model.getVertices().add(new Vector3(2,2,0));
        model.getVertices().add(new Vector3(3,3,0));
        model.getVertices().add(new Vector3(4,4,0));

        Face polygon = new Face(5);
        polygon.vertexIndex = new int[]{0, 1, 2, 3, 4};
        polygon.normalIndex = new int[]{5, 6, 7, 8, 9};
        polygon.textureIndex = new int[]{10, 11, 12, 13, 14};

        model.getFaces().add(polygon);
        Assert.assertEquals(1, model.getFaces().size());

        model.triangulate();
        Assert.assertEquals(3, model.getFaces().size());
        Assert.assertEquals(3, model.getFaces().get(0).getSides());
        Assert.assertEquals(3, model.getFaces().get(1).getSides());

        Assert.assertArrayEquals(new int[]{0, 1, 3}, model.getFaces().get(0).vertexIndex);
        Assert.assertArrayEquals(new int[]{0, 1, 3}, model.getFaces().get(0).normalIndex);
        Assert.assertArrayEquals(new int[]{0, 1, 3}, model.getFaces().get(0).textureIndex);

        Assert.assertArrayEquals(new int[]{1, 2, 3}, model.getFaces().get(1).vertexIndex);
        Assert.assertArrayEquals(new int[]{1, 2, 3}, model.getFaces().get(1).normalIndex);
        Assert.assertArrayEquals(new int[]{1, 2, 3}, model.getFaces().get(1).textureIndex);
    }*/

}
