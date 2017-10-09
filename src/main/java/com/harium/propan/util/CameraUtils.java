package com.harium.propan.util;

public class CameraUtils {

    public static double getFOVfromFocalLength(int focalLength, int cameraHeight) {
        double fovy = 2 * Math.atan(cameraHeight / 2 / focalLength);
        return fovy;
    }

}
