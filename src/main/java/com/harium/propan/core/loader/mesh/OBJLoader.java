package com.harium.propan.core.loader.mesh;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.util.PathHelper;
import com.harium.propan.core.loader.VBOLoader;
import com.harium.propan.core.material.OBJMaterial;
import com.harium.propan.core.model.Face;
import com.harium.propan.core.model.Group;
import com.harium.propan.core.model.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Oskar
 * (This version was modified by yuripourre to support materials)
 * @license LGPLv3
 */

public class OBJLoader implements VBOLoader {

    public static final String VERTEX = "v";
    public static final String FACE = "f";
    public static final String GROUP = "g";
    public static final String VERTEX_TEXCOORD = "vt";
    public static final String VERTEX_NORMAL = "vn";
    public static final String OBJECT = "o";
    public static final String MATERIAL_LIB = "mtllib";
    public static final String USE_MATERIAL = "usemtl";

    private static final String SEPARATOR = "/";

    public Model loadModel(URL url, String path) throws IOException {
        String fpath = url.getPath();
        InputStream stream = url.openStream();

        return loadModel(fpath, path, stream);
    }

    public Model loadModel(String fpath, String path, InputStream stream) throws IOException {
        String modelFolder = PathHelper.upperDirectory(fpath);

        Model vbo = new Model(path);

        Map<String, Group> groups = new HashMap<>();
        Group group = new Group(DEFAULT_GROUP_NAME);

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String line;

        while ((line = reader.readLine()) != null) {

            line = fixLine(line);

            if (line.startsWith(GROUP + " ")) {

                String groupName = line.split(" ")[1];

                if (!DEFAULT_GROUP_NAME.equals(group.getName())) {
                    //Add last group
                    groups.put(group.getName(), group);
                } else if (!group.getFaces().isEmpty()) {
                    //If group has at least one face
                    groups.put(group.getName(), group);
                }
                group = new Group(groupName);

            } else if (line.startsWith(USE_MATERIAL + " ")) {

                String materialName = line.split(" ")[1];
                group.setMaterial(vbo.getMaterials().get(materialName));

            } else if (line.startsWith(VERTEX + " ")) {
                parseVertex(line, vbo);

            } else if (line.startsWith(VERTEX_NORMAL + " ")) {
                parseVertexNormal(line, vbo);

            } else if (line.startsWith(VERTEX_TEXCOORD + " ")) {
                parseVertexTexture(line, vbo);

            } else if (line.startsWith(FACE + " ")) {
                parseFace(vbo, group, line);

            } else if (line.startsWith(MATERIAL_LIB)) {
                parseMaterial(modelFolder, vbo, line);
            }
        }

        reader.close();

        //Add group to Model
        if (group != null) {
            groups.put(group.getName(), group);
        }

        vbo.setGroups(groups);

        return vbo;
    }

    private void parseMaterial(String modelFolder, Model vbo, String line) {
        List<OBJMaterial> materials = parseMaterial(modelFolder, line);

        for (OBJMaterial material : materials) {
            vbo.getMaterials().put(material.getName(), material);
        }
    }

    void parseFace(Model vbo, Group group, String line) {
        String[] splitLine = line.substring(2).split(" ");

        int sides = splitLine.length;

        int[] vIndexes = new int[sides];
        int[] nIndexes = new int[sides];
        int[] texIndexes = new int[sides];

        for (int i = 0; i < sides; i++) {

            String[] slices = splitLine[i].split(SEPARATOR);

            if (slices.length > 1) {
                // vIndexes starts in 0
                // OBJ Faces starts in 1
                vIndexes[i] = Integer.parseInt(slices[0]) - 1;

                if (!slices[1].isEmpty()) {
                    texIndexes[i] = Integer.parseInt(slices[1]) - 1;
                }

                if (slices.length > 2) {
                    nIndexes[i] = Integer.parseInt(slices[2]) - 1;
                }

            } else {
                //Save only the vertexes indexes
                vIndexes[i] = Integer.parseInt(splitLine[i]) - 1;
            }
        }

        Face face = new Face(vIndexes, texIndexes, nIndexes);
        face.setSides(sides);

        group.getFaces().add(face);
        vbo.getFaces().add(face);
    }

    private String fixLine(String line) {
        return line.replaceAll("  ", " ");
    }

    static void parseVertex(String line, Model vbo) {

        String[] parts = line.split(" ");

        float x = Float.valueOf(parts[1]);
        float y = Float.valueOf(parts[2]);
        float z = Float.valueOf(parts[3]);

        vbo.addVertex(new Vector3(x, y, z));
    }

    static void parseVertexNormal(String line, Model vbo) {

        String[] parts = line.split(" ");

        float x = Float.valueOf(parts[1]);
        float y = Float.valueOf(parts[2]);
        float z = Float.valueOf(parts[3]);

        vbo.getNormals().add(new Vector3(x, y, z));
    }

    static void parseVertexTexture(String line, Model vbo) {

        String[] parts = line.split(" ");

        float x = Float.valueOf(parts[1]);
        float y = Float.valueOf(parts[2]);

        vbo.getTextures().add(new Vector2(x, y));
    }

    private static List<OBJMaterial> parseMaterial(String folder, String line) {

        String filename = line.substring(line.indexOf(" ") + 1);

        List<OBJMaterial> materials = new ArrayList<>();
        try {
            materials.addAll(OBJMaterialLoader.loadMaterial(folder, filename));
        } catch (IOException e) {
            System.err.println("Material " + filename + " not found in: " + folder);
        }

        return materials;
    }

}
