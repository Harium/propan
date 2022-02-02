package com.harium.propan.animation.pose;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;

public class Joint {

    public static final int NO_PARENT = -1;

    public String name;
    public int index;
    public int parentIndex = NO_PARENT;

    public Matrix4 bindPose;
    public Matrix4 inverseBindPose;

    public Joint(int index, int parentIndex, String name, Matrix4 bindPose) {
        this.name = name;
        this.index = index;
        this.parentIndex = parentIndex;
        this.bindPose = bindPose;
        this.inverseBindPose = new Matrix4(bindPose).inv();
    }

    public boolean hasParent() {
        return parentIndex != NO_PARENT;
    }

    public void rotate(Quaternion rotation) {
        bindPose.rotate(rotation);
        inverseBindPose.set(bindPose).inv();
    }

    public void scale(float scale) {
        float tx = bindPose.val[6];
        float ty = bindPose.val[7];

        // Set translation only
        bindPose.val[6] = tx * scale;
        bindPose.val[7] = ty * scale;

        inverseBindPose.set(bindPose).inv();
    }

    @Override
    public String toString() {
        return "Joint2D{" + "name='" + name + "'\n" + bindPose + "}";
    }
}
