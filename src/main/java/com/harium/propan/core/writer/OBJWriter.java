package com.harium.propan.core.writer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.util.PathHelper;
import com.harium.etyl.util.StringUtils;
import com.harium.etyl.util.io.IOHelper;
import com.harium.propan.core.loader.mesh.OBJLoader;
import com.harium.propan.core.model.Face;
import com.harium.propan.core.model.Group;
import com.harium.propan.core.model.Model;

import java.io.*;
import java.util.List;


public class OBJWriter implements VBOWriter {

    private static final boolean optimize = false;
    private static final String FACE_SEPARATOR = "/";
    private static final String MTL_EXTENSION = ".mtl";

    @Override
    public void writeVBO(Model vbo, String path) throws IOException {
        Writer writer = null;

        try {
            String filename = PathHelper.filename(path);
            File file = IOHelper.getFile(path);

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), IOHelper.ENCODING_UTF_8));

            if (!optimize) {
                writeHeader(vbo, writer);
            }

            writeMaterialLibrary(vbo, writer, filename);
            writeVertexes(vbo, writer);

            writeTextures(vbo, writer);

            if (!optimize) {
                writer.write(StringUtils.NEW_LINE);
            }
            writeNormals(vbo, writer);

            writeFaces(writer, vbo.getFaces());

            for (Group group : vbo.getGroups()) {
                writeGroupSetup(writer, group);
                writeFaces(writer, group.getFaces());
            }

            if (!vbo.getMaterials().isEmpty()) {
                //exportMaterials();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    private void writeHeader(Model vbo, Writer writer) throws IOException {
        writer.write("# Created by Propan " + StringUtils.NEW_LINE);
        writer.write("# Vertices: " + verticesCount(vbo) + ",  Faces: " + facesCount(vbo) + StringUtils.NEW_LINE);
    }

    private int verticesCount(Model vbo) {
        return vbo.getVertices().size();
    }

    private int facesCount(Model vbo) {
        int sum = vbo.getFaces().size();
        for (Group group : vbo.getGroups()) {
            sum += group.getFaces().size();
        }
        return sum;
    }

    private void writeMaterialLibrary(Model vbo, Writer writer, String filename) throws IOException {
        writer.write(OBJLoader.MATERIAL_LIB + " " + filename + MTL_EXTENSION + StringUtils.NEW_LINE);
    }

    private void writeGroupSetup(Writer writer, Group group) throws IOException {
        writer.write(OBJLoader.GROUP + " " + group.getName() + StringUtils.NEW_LINE);

        // Define material after the group
        if (group.getMaterial() != null) {
            String materialName = group.getMaterial().getName();
            if (materialName.isEmpty()) {
                materialName = group.getName();
            }
            writer.write(OBJLoader.USE_MATERIAL + " " + materialName + StringUtils.NEW_LINE);
        }
    }

    private void writeFaces(Writer writer, List<Face> faces) throws IOException {
        for (Face face : faces) {

            boolean hasTexture = faceHasTexture(face);
            boolean hasNomals = faceHasNormal(face);

            StringBuilder sb = new StringBuilder();
            sb.append(OBJLoader.FACE + " ");

            for (int i = 0; i < face.getSides(); i++) {

                if (i > 0) {
                    sb.append(" ");
                }

                int vertexIndex = face.vertexIndex[i] + 1;
                sb.append(vertexIndex);

                if (hasTexture) {
                    sb.append(FACE_SEPARATOR);
                    sb.append(face.textureIndex[i] + 1);
                }

                if (hasNomals) {
                    sb.append(FACE_SEPARATOR);
                    if (!hasTexture) {
                        sb.append(FACE_SEPARATOR);
                    }
                    sb.append(face.normalIndex[i] + 1);
                }
            }

            sb.append(StringUtils.NEW_LINE);
            writer.write(sb.toString());
        }
    }

    private boolean faceHasTexture(Face face) {
        boolean validTexture = false;

        if (face.textureIndex != null) {
            for (int i = 0; i < face.getSides(); i++) {
                if (face.textureIndex[i] != 0) {
                    validTexture = true;
                }
            }
        }

        return validTexture;
    }

    private boolean faceHasNormal(Face face) {
        boolean validTexture = false;

        if (face.normalIndex != null) {
            for (int i = 0; i < face.getSides(); i++) {
                if (face.normalIndex[i] != 0) {
                    validTexture = true;
                }
            }
        }

        return validTexture;
    }

    private void writeTextures(Model vbo, Writer writer) throws IOException {
        for (Vector2 vector : vbo.getTextures()) {
            String text = OBJLoader.VERTEX_TEXCOORD + " " + vector.x + " " + vector.y + StringUtils.NEW_LINE;
            writer.write(text);
        }
    }

    private void writeNormals(Model vbo, Writer writer) throws IOException {
        for (Vector3 vector : vbo.getNormals()) {
            String text = OBJLoader.VERTEX_NORMAL + " " + vector.x + " " + vector.y + " " + vector.z + StringUtils.NEW_LINE;
            writer.write(text);
        }

        if (!optimize) {
            if (!vbo.getNormals().isEmpty()) {
                writer.write(StringUtils.NEW_LINE);
            }
        }
    }

    private void writeVertexes(Model vbo, Writer writer) throws IOException {
        for (Vector3 vector : vbo.getVertices()) {
            String text = OBJLoader.VERTEX + " " + vector.x + " " + vector.y + " " + vector.z + StringUtils.NEW_LINE;
            writer.write(text);
        }
    }

}
