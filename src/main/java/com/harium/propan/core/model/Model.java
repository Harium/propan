package com.harium.propan.core.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.harium.propan.core.material.OBJMaterial;
import com.harium.propan.geometry.BoundingBox3D;

import java.util.*;

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

    public void triangulate() {
        ListIterator<Face> iterator = faces.listIterator();

        while (iterator.hasNext()) {
            Face face = iterator.next();
            if (face.getSides() == 3) {
                continue;
            } else {
                if (face.getSides() == 4) {
                    Face nextFace = new Face(3);

                    nextFace.vertexIndex[0] = face.vertexIndex[1];
                    nextFace.vertexIndex[1] = face.vertexIndex[2];
                    nextFace.vertexIndex[2] = face.vertexIndex[3];
                    face.vertexIndex = new int[]{face.vertexIndex[0], face.vertexIndex[1], face.vertexIndex[3]};

                    if (face.normalIndex != null) {
                        nextFace.normalIndex[0] = face.normalIndex[1];
                        nextFace.normalIndex[1] = face.normalIndex[2];
                        nextFace.normalIndex[2] = face.normalIndex[3];
                        face.normalIndex = new int[]{face.normalIndex[0], face.normalIndex[1], face.normalIndex[3]};
                    }

                    if (face.textureIndex != null) {
                        nextFace.textureIndex[0] = face.textureIndex[1];
                        nextFace.textureIndex[1] = face.textureIndex[2];
                        nextFace.textureIndex[2] = face.textureIndex[3];
                        face.textureIndex = new int[]{face.textureIndex[0], face.textureIndex[1], face.textureIndex[3]};
                    }

                    face.setSides(3);
                    iterator.add(nextFace);
                } else {
                    System.err.println("Propan cannot triangulate face with more than 4 faces.");
                    /*List<Face> triangles = computeTriangles(this, face);
                    iterator.remove();
                    for (Face f : triangles) {
                        iterator.add(f);
                    }*/
                }
            }
        }
    }

    /*private List<Face> computeTriangles(Model model, Face face) {
        Map<Vector3, Integer> position = new HashMap<>();
        Map<Vector3, Integer> normal = new HashMap<>();
        Map<Vector3, Integer> texture = new HashMap<>();

        List<Vector3> points = new ArrayList<>();

        boolean hasNormal = face.normalIndex == null;
        boolean hasTexture = face.textureIndex == null;

        double[] data = new double[face.getSides() * 3];

        for (int i = 0; i < face.getSides(); i++) {
            int index = face.vertexIndex[i];
            Vector3 p = model.getVertices().get(index);
            points.add(p);
            position.put(p, index);

            if (hasNormal) {
                normal.put(p, face.normalIndex[i]);
            }
            if (hasTexture) {
                texture.put(p, face.textureIndex[i]);
            }

            data[i * 3] = p.x;
            data[i * 3 + 1] = p.y;
            data[i * 3 + 2] = p.z;
        }

        List<Face> faces = new ArrayList<>();

        Triangulation triangulation = new DelaunayTriangulation();
        List<Triangle> triangles = triangulation.triangulate(points);

        for (Triangle tri : triangles) {
            Face f = new Face(3);
            updateA(f, tri.getA(), 0, position, normal, texture, hasNormal, hasTexture, tri);
            updateA(f, tri.getB(), 1, position, normal, texture, hasNormal, hasTexture, tri);
            updateA(f, tri.getC(), 2, position, normal, texture, hasNormal, hasTexture, tri);
        }

        return faces;
    }

    private void updateA(Face f, Vector3 point, int index, Map<Vector3, Integer> position, Map<Vector3, Integer> normal, Map<Vector3, Integer> texture, boolean hasNormal, boolean hasTexture, Triangle tri) {
        f.vertexIndex[index] = position.get(point);
        if (hasNormal) {
            f.normalIndex[index] = normal.get(point);
        }
        if (hasTexture) {
            f.textureIndex[index] = texture.get(point);
        }
    }*/

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
