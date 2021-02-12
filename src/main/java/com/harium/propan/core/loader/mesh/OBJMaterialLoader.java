package com.harium.propan.core.loader.mesh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector3;

import com.harium.propan.core.material.OBJMaterial;

/**
 * Based on MTL specification from: http://paulbourke.net/dataformats/mtl/
 */
public class OBJMaterialLoader {

    public static final String NEW_MATERIAL = "newmtl";
    public static final String AMBIENT_COLOR = "Ka";
    public static final String AMBIENT_TEX_MAP = "map_Kd";
    public static final String DIFFUSE_COLOR = "Kd";
    public static final String DIFFUSE_TEX_MAP = "map_Kd";
    public static final String DISSOLVE = "d";
    public static final String DISSOLVE_TEX_MAP = "map_d";
    public static final String SPECULAR_COLOR = "Ks";
    public static final String SPECULAR_TEX_MAP = "map_Ks";
    public static final String TRANSMISSION_FILTER = "Tf";
    public static final String ILLUMINATION = "illum";

    public static List<OBJMaterial> loadMaterial(String folder, String filename) throws IOException{

        File f = new File(folder+filename);

        BufferedReader reader = new BufferedReader(new FileReader(f));

        List<OBJMaterial> materials = new ArrayList<OBJMaterial>();

        OBJMaterial mat = new OBJMaterial();

        String line;

        while ((line = reader.readLine()) != null) {

            line = line.trim();

            String[] splitLine = line.split(" ");
            String prefix = splitLine[0];

            if (NEW_MATERIAL.equalsIgnoreCase(prefix)) {

                if (mat!=null) {
                    materials.add(mat);
                    mat = new OBJMaterial();
                }

                mat.setName(splitLine[1]);

            } else if (AMBIENT_COLOR.equalsIgnoreCase(prefix)) {
                mat.setKa(readVector(splitLine));
            } else if (DIFFUSE_COLOR.equalsIgnoreCase(prefix)) {
                mat.setKd(readVector(splitLine));
            } else if (DISSOLVE.equalsIgnoreCase(prefix)) {
                mat.setD(readFloat(splitLine));
            } else if (SPECULAR_COLOR.equalsIgnoreCase(prefix)) {
                mat.setKs(readVector(splitLine));
            } else if (ILLUMINATION.equalsIgnoreCase(prefix)) {
                mat.setIllum(readInt(splitLine));
            } else if (AMBIENT_TEX_MAP.equalsIgnoreCase(prefix)) {
                mat.setMapKa(folder + splitLine[1]);
            } else if (DIFFUSE_TEX_MAP.equalsIgnoreCase(prefix)) {
                mat.setMapKd(folder + splitLine[1]);
            } else if (DISSOLVE_TEX_MAP.equalsIgnoreCase(prefix)) {
                mat.setMapD(folder + splitLine[1]);
            } else if (SPECULAR_TEX_MAP.equalsIgnoreCase(prefix)) {
                mat.setMapKs(folder + splitLine[1]);
            }
        }

        reader.close();

        if (mat != null) {
            materials.add(mat);
        }

        return materials;
    }

    private static int readInt(String[] splitLine) {
        return Integer.parseInt(splitLine[1]);
    }

    private static float readFloat(String[] splitLine) {
        return Float.parseFloat(splitLine[1]);
    }

    private static Vector3 readVector(String[] splitLine) {
        float x = Float.parseFloat(splitLine[1]);
        float y = Float.parseFloat(splitLine[2]);
        float z = Float.parseFloat(splitLine[3]);
        return new Vector3(x, y, z);
    }

}
