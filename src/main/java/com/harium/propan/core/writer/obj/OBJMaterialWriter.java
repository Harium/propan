package com.harium.propan.core.writer.obj;

import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.util.StringUtils;
import com.harium.etyl.util.io.IOHelper;
import com.harium.propan.core.loader.mesh.OBJMaterialLoader;
import com.harium.propan.core.material.OBJMaterial;
import com.harium.propan.core.writer.MaterialWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class OBJMaterialWriter implements MaterialWriter<OBJMaterial> {

    private boolean compactMode = false;

    @Override
    public void writeMaterial(OBJMaterial material, String filename) throws IOException {
        Map<String, OBJMaterial> map = new HashMap<>();
        map.put(material.getName(), material);
        writeMaterials(map, filename);
    }

    @Override
    public void writeMaterials(Map<String, OBJMaterial> materials, String filename) throws IOException {
        File file = IOHelper.getFile(filename);

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), IOHelper.ENCODING_UTF_8));

        if (!compactMode) {
            writeHeader(writer);
        }

        for (OBJMaterial material : materials.values()) {
            if (!compactMode) {
                writer.write(StringUtils.NEW_LINE);
            }
            writer.write(OBJMaterialLoader.NEW_MATERIAL + " " + material.getName());
            if (!compactMode) {
                writer.write(StringUtils.NEW_LINE);
            }
            writeVector(writer, OBJMaterialLoader.AMBIENT_COLOR, material.getKa());
            writeAttribute(writer, OBJMaterialLoader.AMBIENT_TEX_MAP, material.getMapKa());
            writeVector(writer, OBJMaterialLoader.DIFFUSE_COLOR, material.getKd());
            writeAttribute(writer, OBJMaterialLoader.DIFFUSE_TEX_MAP, material.getMapKd());
            writeFloat(writer, OBJMaterialLoader.DISSOLVE, material.getD());
            writeAttribute(writer, OBJMaterialLoader.DISSOLVE_TEX_MAP, material.getMapD());
            writeVector(writer, OBJMaterialLoader.SPECULAR_COLOR, material.getKs());
            writeAttribute(writer, OBJMaterialLoader.SPECULAR_TEX_MAP, material.getMapKs());
            writeInteger(writer, OBJMaterialLoader.ILLUMINATION, material.getIllum());
        }

        writer.close();
    }

    private void writeInteger(Writer writer, String attribute, Integer value) throws IOException {
        if (value == null) {
            return;
        }
        writer.write(attribute + " " + value + StringUtils.NEW_LINE);
    }

    private void writeFloat(Writer writer, String attribute, Float value) throws IOException {
        if (value == null) {
            return;
        }
        writer.write(attribute + " " + value + StringUtils.NEW_LINE);
    }

    private void writeAttribute(Writer writer, String objAttribute, String attribute) throws IOException {
        if (attribute == null || attribute.isEmpty()) {
            return;
        }
        writer.write(objAttribute + " " + attribute + StringUtils.NEW_LINE);
    }

    private void writeVector(Writer writer, String attribute, Vector3 v) throws IOException {
        if (v == null) {
            return;
        }
        writer.write(attribute + " " + v.x + " " + v.y + " " + v.z + StringUtils.NEW_LINE);
    }

    private void writeHeader(Writer writer) throws IOException {
        writer.write("# Created by Propan");
    }

}
