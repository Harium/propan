package com.harium.propan.core.loader.mesh.collada.helper;

import com.harium.propan.core.loader.mesh.collada.ColladaParser;
import com.harium.propan.core.loader.mesh.collada.node.InputNode;
import com.harium.propan.core.loader.mesh.collada.node.SourceNode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.xml.sax.Attributes;

import java.util.List;

public class ColladaParserHelper {

    public static void parseVertex3D(SourceNode source, float[] array, List<Vector3> list) {
        for (int i = 0; i < source.accessor.count; i++) {
            float x = array[i*0];
            float y = array[i*1];
            float z = array[i*2];

            Vector3 v = new Vector3(x,y,z);
            list.add(v);
        }
    }

    public static InputNode parseInput(Attributes attributes) {
        InputNode input = new InputNode();

        String offset = attributes.getValue(ColladaParser.ATTRIBUTE_OFFSET);

        if (offset != null) {
            input.offset = Integer.parseInt(offset);
        }

        String semantic = attributes.getValue(ColladaParser.ATTRIBUTE_SEMANTIC);
        String source = attributes.getValue(ColladaParser.SOURCE);
        input.semantic = semantic;
        input.source = source;

        return input;
    }

    public static void parseVertex2D(SourceNode source, float[] array, List<Vector2> list) {
        for (int i = 0; i < source.accessor.count; i++) {
            float x = array[i*0];
            float y = array[i*1];

            Vector2 v = new Vector2(x,y);
            list.add(v);
        }
    }
}
