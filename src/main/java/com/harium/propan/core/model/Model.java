package com.harium.propan.core.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.harium.propan.core.material.OBJMaterial;
import com.harium.propan.geometry.BoundingBox3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    private String name = "";
    private String path = "";

    private BoundingBox3D boundingBox = new BoundingBox3D();

    // Original vertices positions
    private List<Vector3> vertices = new ArrayList<Vector3>();
    private List<Vector3> normals = new ArrayList<Vector3>();
    private List<Vector2> textures = new ArrayList<Vector2>();

    private List<Face> faces = new ArrayList<Face>();
    private List<Group> groups = new ArrayList<Group>();

    private Map<String, OBJMaterial> materials = new HashMap<String, OBJMaterial>();

    private List<String> materialLibs = new ArrayList<String>();

    public Model() {
        super();
    }

    public Model(String path) {
        super();
        this.path = path;
    }

    public List<Vector3> getVertices() {
        return vertices;
    }

    public int addVertex(Vector3 vertex) {
        vertices.add(vertex);
        boundingBox.add(vertex);
        return vertices.size();
    }

    public List<Vector3> getNormals() {
        return normals;
    }

    public void setNormals(List<Vector3> normals) {
        this.normals = normals;
    }

    public List<Vector2> getTextures() {
        return textures;
    }

    public void setTextures(List<Vector2> textures) {
        this.textures = textures;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Map<String, OBJMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(Map<String, OBJMaterial> materials) {
        this.materials = materials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BoundingBox3D getBoundingBox() {
        return boundingBox;
    }

    public Vector3 centroid() {
        float cx = 0, cy = 0, cz = 0;

        int n = 1;
        for (Vector3 v : vertices) {
            cx = cx * (n - 1) / n + v.x / n;
            cy = cy * (n - 1) / n + v.y / n;
            cz = cz * (n - 1) / n + v.z / n;
            n++;
        }

        return new Vector3(cx, cy, cz);
    }

    public Vector3 centroid(Face face) {
        float cx = 0, cy = 0, cz = 0;

        int n = 1;
        for (int i : face.vertexIndex) {
            Vector3 vertex = vertices.get(i);
            cx = cx * (n - 1) / n + vertex.x / n;
            cy = cy * (n - 1) / n + vertex.y / n;
            cz = cz * (n - 1) / n + vertex.z / n;
            n++;
        }

        return new Vector3(cx, cy, cz);
    }

    public void rotate(Vector3 axis, float angle, Vector3 origin) {
        for (Vector3 v : vertices) {
            v.sub(origin);
            v.rotate(axis, angle);
            v.add(origin);
        }
    }

    public void rotate(Vector3 axis, float angle) {
        for (Vector3 v : vertices) {
            v.rotate(axis, angle);
        }
    }

    public void scale(float factor) {
        for (Vector3 v : vertices) {
            v.scl(factor);
        }
    }

    public void scale(float x, float y, float z) {
        for (Vector3 v : vertices) {
            v.scl(x, y, z);
        }
    }

    public void translate(Vector3 displacement) {
        for (Vector3 v : vertices) {
            v.add(displacement);
        }
    }

    public void translate(float x, float y, float z) {
        for (Vector3 v : vertices) {
            v.add(x, y, z);
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getMaterialLibs() {
        return materialLibs;
    }

    public void setMaterialLibs(List<String> materialLibs) {
        this.materialLibs = materialLibs;
    }

}
