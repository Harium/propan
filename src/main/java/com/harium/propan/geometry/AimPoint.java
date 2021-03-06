package com.harium.propan.geometry;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class AimPoint extends Vector3 {

    private static final long serialVersionUID = -848999878735473707L;

    protected double angleX = 0;
    protected double angleY = 0;
    protected double angleZ = 0;

    public AimPoint() {
        super();
    }

    public AimPoint(float x, float y, float z) {
        super(x, y, z);
    }

    public AimPoint(Vector3 vector) {
        super(vector.x, vector.y, vector.z);
    }

    public double getAngleX() {
        return angleX;
    }

    public void setAngleX(double angleX) {
        this.angleX = angleX;
    }

    public void offsetAngleX(double offsetAngleX) {
        this.angleX += offsetAngleX;
    }

    public double getAngleY() {
        return angleY;
    }

    public void setAngleY(double angleY) {
        this.angleY = angleY;
    }

    public void offsetAngleY(double offsetAngleY) {
        this.angleY += offsetAngleY;
    }

    public double getAngleZ() {
        return angleZ;
    }

    public void setAngleZ(double angleZ) {
        this.angleZ = angleZ;
    }

    public void offsetAngleZ(double offsetAngleZ) {
        this.angleZ += offsetAngleZ;
    }

    public static double[][] rotationMatrixX(double angle, double px, double py, double pz) {

        double m[][] = new double[4][4];

        m[0][0] = 1;
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = px - m[0][0] * px - m[0][1] * py - m[0][2] * pz;

        m[1][0] = 0;
        m[1][1] = cos(angle);
        m[1][2] = -sin(angle);
        m[1][3] = py - m[1][0] * px - m[1][1] * py - m[1][2] * pz;

        m[2][0] = 0;
        m[2][1] = sin(angle);
        m[2][2] = cos(angle);
        m[2][3] = pz - m[2][0] * px - m[2][1] * py - m[2][2] * pz;

        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return m;
    }

    public static double[][] rotationMatrixY(double angle, double px, double py, double pz) {

        double m[][] = new double[4][4];

        m[0][0] = cos(angle);
        m[0][1] = 0;
        m[0][2] = sin(angle);
        m[0][3] = px - m[0][0] * px - m[0][1] * py - m[0][2] * pz;

        m[1][0] = 0;
        m[1][1] = 1;
        m[1][2] = 0;
        m[1][3] = py - m[1][0] * px - m[1][1] * py - m[1][2] * pz;

        m[2][0] = -sin(angle);
        m[2][1] = 0;
        m[2][2] = cos(angle);
        m[2][3] = pz - m[2][0] * px - m[2][1] * py - m[2][2] * pz;

        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return m;
    }

    public static double[][] rotationMatrixZ(double angle, double px, double py, double pz) {

        double m[][] = new double[4][4];

        m[0][0] = cos(angle);
        m[0][1] = -sin(angle);
        m[0][2] = 0;
        m[0][3] = px - m[0][0] * px - m[0][1] * py - m[0][2] * pz;

        m[1][0] = sin(angle);
        m[1][1] = cos(angle);
        m[1][2] = 0;
        m[1][3] = py - m[1][0] * px - m[1][1] * py - m[1][2] * pz;

        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = 1;
        m[2][3] = pz - m[2][0] * px - m[2][1] * py - m[2][2] * pz;

        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return m;
    }

    private static double cos(double angleDegree) {
        return Math.cos(Math.toRadians(angleDegree));
    }

    private static double sin(double angleDegree) {
        return Math.sin(Math.toRadians(angleDegree));
    }

    public void moveXZ(float distance) {
        x = (float) (x + AimPoint.sin(angleY) * distance);
        z = (float) (z - AimPoint.cos(angleY - 180) * distance);
    }

    public Vector3 origin() {
        return this;
    }

    public Vector3 forward() {
        float yaw = (float) Math.toRadians(angleY);
        float pitch = (float) Math.toRadians(angleX);
        //float roll = (float)Math.toRadians(angleZ);
        float roll = 0;
        Quaternion quaternion = new Quaternion().setEulerAnglesRad(yaw, pitch, roll);
        Vector3 v = new Vector3(0, 0, 1).mul(quaternion);
        return v;
    }

    public void moveForward(double distance) {
        Vector3 forward = forward();
        this.add(forward.scl((float)distance));
    }

    public void strafe(double distance) {
        Quaternion quaternion = quaternion();
        Vector3 forward = new Vector3(0, 0, 1).mul(quaternion);
        Vector3 up = new Vector3(0, 1, 0).mul(quaternion);
        up.crs(forward);

        this.add(up.scl((float)distance));
    }

    public Quaternion quaternion() {
        float yaw = (float) Math.toRadians(angleY);
        float pitch = (float) Math.toRadians(angleX);
        float roll = (float) Math.toRadians(angleZ);

        Quaternion quaternion = new Quaternion().setEulerAnglesRad(yaw, pitch, roll);

        return quaternion;
    }

}
