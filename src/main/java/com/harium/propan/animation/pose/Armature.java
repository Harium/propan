package com.harium.propan.animation.pose;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;

public class Armature {

    public Joint[] joints = new Joint[2];

    public void rotate(Matrix4 rotation) {
        Quaternion q = rotation.getRotation(new Quaternion());
        for (Joint joint : joints) {
            joint.rotate(q);
        }
    }

    public void scale(float scale) {
        for (Joint joint : joints) {
            joint.scale(scale);
        }
    }

    public Joint findByName(String name) {
        for (Joint joint : joints) {
            if (joint.name.equals(name)) {
                return joint;
            }
        }
        return null;
    }

    public int numJoints() {
        return joints.length;
    }
}
