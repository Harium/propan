package com.harium.propan.linear;

import com.badlogic.gdx.math.Vector3;
import org.junit.Assert;
import org.junit.Test;

public class AimPointTest {

    private static final double EPSILON = 0.001;

    @Test
    public void testForward() {
        AimPoint point = new AimPoint();
        point.angleX = 0;
        point.angleY = 0;
        point.angleZ = 0;

        Vector3 forward = point.forward();
        Assert.assertEquals(0, forward.x, EPSILON);
        Assert.assertEquals(0, forward.y, EPSILON);
        Assert.assertEquals(1, forward.z, EPSILON);
    }

    @Test
    public void testWithYaw() {
        AimPoint point = new AimPoint();
        point.angleX = 0;
        point.angleY = 180;
        point.angleZ = 0;

        Vector3 forward = point.forward();
        Assert.assertEquals(0, forward.x, EPSILON);
        Assert.assertEquals(0, forward.y, EPSILON);
        Assert.assertEquals(-1, forward.z, EPSILON);
    }

    @Test
    public void testWithYawAndPitch() {
        AimPoint point = new AimPoint();
        point.angleX = 90;
        point.angleY = 180;
        point.angleZ = 0;

        Vector3 forward = point.forward();
        Assert.assertEquals(0, forward.x, EPSILON);
        Assert.assertEquals(-1, forward.y, EPSILON);
        Assert.assertEquals(0, forward.z, EPSILON);
    }

}
