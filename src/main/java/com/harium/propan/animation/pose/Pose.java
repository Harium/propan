package com.harium.propan.animation.pose;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

/**
 * Code from Ardor3D and Bones
 * https://github.com/Renanse/Ardor3D/blob/master/ardor3d-animation/src/main/java/com/ardor3d/extension/animation/skeletal/SkeletonPose.java
 * https://github.com/raftAtGit/Bones
 */
public class Pose {

    private Armature armature;

    /** transforms in object space */
    public final Matrix4[] globals;

    /** transforms in joint space */
    final Matrix4[] locals;

    /** the transform which can directly be applied to a mesh vertex */
    public final Matrix4[] palette;

    public Pose(Armature armature) {
        this.armature = armature;
        this.globals = createNMatrices(armature.numJoints());
        this.locals = createNMatrices(armature.numJoints());
        this.palette = createNMatrices(armature.numJoints());

        resetPose();
    }

    public void resetPose() {
        resetPose(this.armature);
    }

    /** Updates transforms to reset to bind-pose. */
    public void resetPose(Armature armature) {
        // go through our local transforms
        for (int i = 0; i < locals.length; i++) {
            // inverse of inverseBindPose = bindPose :)
            locals[i].set(armature.joints[i].bindPose);

            // At this point we are in model space, so we need to remove our parent's transform (if we have one.)
            if (armature.joints[i].hasParent()) {
                final int parentIndex = armature.joints[i].parentIndex;
                // We remove the parent's transform simply by multiplying by its inverse bind pose. Done! :)
                locals[i].mul(armature.joints[parentIndex].inverseBindPose);
            }
        }
    }

    /**
     * Matrices are updated based on changes on the armature
     */
    public void updateTransforms() {
        // we go in update array order, which ensures parent global transforms are updated before child.
        for (int index = 0; index < armature.joints.length; index++) {

            // find our parent
            if (armature.joints[index].hasParent()) {
                final int parentIndex = armature.joints[index].parentIndex;
                // we have a parent, so take us from local->parent->model space by multiplying
                // by parent's local->model space transform.
                globals[index].set(locals[index]);
                globals[index].mul(globals[parentIndex]);
            } else {
                // no parent so just set global to the local transform
                globals[index].set(locals[index]);
            }

            // at this point we have a local->model space transform for this joint, for skinning we multiply this by the
            // joint's inverse bind pose (joint->model space, inverted). This gives us a transform that can take a
            // vertex from bind pose (local space) to current pose (model space).
            palette[index].set(armature.joints[index].inverseBindPose);
            palette[index].mul(globals[index]);
        }
    }

    private static Matrix4[] createNMatrices(int length) {
        Matrix4[] result = new Matrix4[length];
        for (int i = 0; i < length; i++) {
            result[i] = new Matrix4();
        }
        return result;
    }


}
