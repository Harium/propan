package com.harium.propan.core.model;

import java.util.ListIterator;

public class ModelUtils {

    public static void triangulate(Model model) {
        ListIterator<Face> iterator = model.faces.listIterator();
        iterate(iterator);

        for (Group group : model.groups.values()) {
            iterator = group.getFaces().listIterator();
            iterate(iterator);
        }
    }

    private static void iterate(ListIterator<Face> iterator) {
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

}
