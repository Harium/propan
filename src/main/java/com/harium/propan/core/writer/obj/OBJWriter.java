package com.harium.propan.core.writer.obj;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.util.PathHelper;
import com.harium.etyl.util.StringUtils;
import com.harium.etyl.util.io.IOHelper;
import com.harium.propan.core.loader.mesh.OBJLoader;
import com.harium.propan.core.material.OBJMaterial;
import com.harium.propan.core.model.Face;
import com.harium.propan.core.model.Group;
import com.harium.propan.core.model.Model;
import com.harium.propan.core.writer.VBOWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class OBJWriter implements VBOWriter {

    private static final String FACE_SEPARATOR = "/";

    private static final String EXTENSION_OBJ = ".obj";
    private static final String EXTENSION_MTL = ".mtl";

    private boolean compactMode = false;
    private OBJMaterialWriter materialWriter = new OBJMaterialWriter();

    @Override
    public void writeVBO(Model vbo, String path) throws IOException {
        Writer writer = null;

        try {
            String filename = PathHelper.filename(path);
            filename = filename.substring(1, filename.length() - 4);
            File file = IOHelper.getFile(path);

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), StandardCharsets.UTF_8));

            writeHeader(vbo, writer);

            if (!vbo.getMaterials().isEmpty()) {
                exportMaterials(path, vbo.getMaterials());
                writeMaterialLibrary(vbo, writer, filename);
            }

            writeVertexes(vbo, writer);
            writeTextures(vbo, writer);
            writeNormals(vbo, writer);
            writeGroups(vbo, writer);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    private void writeGroups(Model vbo, Writer writer) throws IOException {
        if (vbo.getGroups().isEmpty()) {
            writeFaces(writer, vbo.getFaces());
        } else {
            for (Group group : vbo.getGroups().values()) {
                writeGroupSetup(writer, group);
                writeFaces(writer, group.getFaces());
            }
        }
    }

    private void exportMaterials(String path, Map<String, OBJMaterial> materials) throws IOException {
        String dir = path;
        if (dir.endsWith(EXTENSION_OBJ)) {
            dir = dir.substring(0, dir.length() - EXTENSION_OBJ.length());
            dir += EXTENSION_MTL;
        }
        materialWriter.writeMaterials(materials, dir);
    }

    private void writeHeader(Model vbo, Writer writer) throws IOException {
        if (compactMode) {
            return;
        }
        writer.write("# Created by Propan" + StringUtils.NEW_LINE);
        writer.write("# Vertices: " + verticesCount(vbo) + ",  Faces: " + facesCount(vbo) + StringUtils.NEW_LINE);
    }

    private int verticesCount(Model vbo) {
        return vbo.getVertices().size();
    }

    private int facesCount(Model vbo) {
        int sum = vbo.getFaces().size();
        for (Group group : vbo.getGroups().values()) {
            sum += group.getFaces().size();
        }
        return sum;
    }

    private void writeMaterialLibrary(Model vbo, Writer writer, String filename) throws IOException {
        writer.write(OBJLoader.MATERIAL_LIB + " " + filename + EXTENSION_MTL + StringUtils.NEW_LINE);
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
        if (!compactMode) {
            writer.write(StringUtils.NEW_LINE);
        }
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
        if (!vbo.getTextures().isEmpty() && !compactMode) {
            writer.write(StringUtils.NEW_LINE);
        }
        for (Vector2 vector : vbo.getTextures()) {
            String text = OBJLoader.VERTEX_TEXCOORD + " " + vector.x + " " + vector.y + StringUtils.NEW_LINE;
            writer.write(text);
        }
    }

    private void writeNormals(Model vbo, Writer writer) throws IOException {
        if (!vbo.getNormals().isEmpty() && !compactMode) {
            writer.write(StringUtils.NEW_LINE);
        }
        for (Vector3 vector : vbo.getNormals()) {
            String text = OBJLoader.VERTEX_NORMAL + " " + vector.x + " " + vector.y + " " + vector.z + StringUtils.NEW_LINE;
            writer.write(text);
        }
    }

    private void writeVertexes(Model vbo, Writer writer) throws IOException {
        if (!compactMode) {
            writer.write(StringUtils.NEW_LINE);
        }
        for (Vector3 vector : vbo.getVertices()) {
            String text = OBJLoader.VERTEX + " " + vector.x + " " + vector.y + " " + vector.z + StringUtils.NEW_LINE;
            writer.write(text);
        }
    }

}
