package com.harium.propan.core.writer.obj;

import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.util.StringUtils;
import com.harium.etyl.util.io.IOHelper;
import com.harium.propan.core.loader.mesh.OBJMaterialLoader;
import com.harium.propan.core.material.OBJMaterial;
import com.harium.propan.core.writer.MaterialWriter;

import java.io.*;

public class OBJMaterialWriter implements MaterialWriter<OBJMaterial> {

    @Override
    public void writeMaterial(OBJMaterial material, String filename) throws IOException {

        Writer writer = null;

        try {
            File file = IOHelper.getFile(filename);

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), IOHelper.ENCODING_UTF_8));

            writeHeader(material, writer);

            writer.write(OBJMaterialLoader.NEW_MATERIAL + " " + material.getName() + StringUtils.NEW_LINE);
            writeVector(writer, OBJMaterialLoader.DIFFUSE_COLOR, material.getKd());
            writeAttribute(writer, OBJMaterialLoader.DIFFUSE_TEX_MAP, material.getMapKd());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    private void writeAttribute(Writer writer, String objAttribute, String attribute) throws IOException {
        if (attribute.isEmpty()) {
            return;
        }
        writer.write(objAttribute + " " + attribute + StringUtils.NEW_LINE);
    }

    private void writeVector(Writer writer, String attribute, Vector3 v) throws IOException {
        if (v == null) {
            return;
        }
        writer.write(attribute + " " + v.x + " " + " " + v.y + " " + v.z + StringUtils.NEW_LINE);
    }

    private void writeHeader(OBJMaterial material, Writer writer) throws IOException {
        writer.write("# Created by Propan " + StringUtils.NEW_LINE);
    }

}
