package com.harium.propan.core.loader.mesh.collada.node;

public class SourceNode {
    public String id;
    public String floatArrayId;
    public float[] floatArray;
    public AccessorNode accessor = new AccessorNode();

    public int offsetPosition;
    public int offsetNormal;
    public int offsetTexture;
}
