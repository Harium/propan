package com.harium.propan.core.loader.mesh.collada.sax;

import com.harium.propan.core.loader.mesh.collada.node.InputNode;
import com.harium.propan.core.model.Face;

import static com.harium.propan.core.loader.mesh.collada.ColladaParser.SEMANTIC_NORMAL;
import static com.harium.propan.core.loader.mesh.collada.ColladaParser.SEMANTIC_TEXTCOORD;
import static com.harium.propan.core.loader.mesh.collada.ColladaParser.SEMANTIC_VERTEX;

public abstract class ParseListenerImpl implements ParseListener {

    protected ParseContext context;

    public ParseListenerImpl(ParseContext context) {
        this.context = context;
    }

    public void parseFloatArray(String text) {
        int partsLength = countParts(text);

        System.out.println((context.partsCount + partsLength) + "/" + context.count);

        context.floatBuilder.append(text);

        if (context.partsCount + partsLength < context.count) {
            partsLength--;
            context.partsCount += partsLength;
            return;
        }

        String string = context.floatBuilder.toString();
        context.floatBuilder = new StringBuilder();

        String[] parts = string.split(" ");
        float[] array = new float[context.count];

        int i = 0;
        for (; i < context.count; i++) {
            float n = Float.parseFloat(parts[i]);
            array[i] = n;
        }

        context.currentGeometry.floatArrays.put(context.sourceId, array);
        context.currentSource.floatArray = array;

        context.partsCount = 0;
    }

    public void parseTriangles(String text) {
        int inputsCount = context.scene.inputs.size();
        int partsLength = countParts(text);

        System.out.println((context.partsCount + partsLength) + "/" + context.count + "(*" + inputsCount + "*3) = " + (context.count * inputsCount * 3));

        context.triangleBuilder.append(text);

        if (context.partsCount + partsLength < context.count * inputsCount * 3) {
            partsLength--;
            context.partsCount += partsLength;
            return;
        }

        String string = context.triangleBuilder.toString();
        context.triangleBuilder = new StringBuilder();

        String[] parts = string.split(" ");

        int i = 0;
        for (; i < context.count * inputsCount * 3; i += inputsCount * 3) {
            Face face = new Face(3);

            for (InputNode input : context.scene.inputs.values()) {
                int vertexOffset;
                int offset = input.offset;

                if (SEMANTIC_VERTEX.equals(input.semantic)) {
                    vertexOffset = context.currentSource.offsetPosition;
                    for (int k = 0; k < 3; k++) {
                        face.vertexIndex[k] = Integer.parseInt(parts[i + k * inputsCount + offset]) + vertexOffset;
                    }
                } else if (SEMANTIC_NORMAL.equals(input.semantic)) {
                    vertexOffset = context.currentSource.offsetNormal;
                    for (int k = 0; k < 3; k++) {
                        face.normalIndex[k] = Integer.parseInt(parts[i + k * inputsCount + offset]) + vertexOffset;
                    }
                } else if (SEMANTIC_TEXTCOORD.equals(input.semantic)) {
                    vertexOffset = context.currentSource.offsetTexture;
                    for (int k = 0; k < 3; k++) {
                        face.textureIndex[k] = Integer.parseInt(parts[i + k * inputsCount + offset]) + vertexOffset;
                    }
                }
            }

            context.currentGroup.getFaces().add(face);
        }

        context.vbo.getGroups().add(context.currentGroup);

        context.partsCount = 0;
    }

    private static int countParts(String text) {
        return countOccurrences(text, ' ') + 1;
    }

    public static int countOccurrences(String text, char needle) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }
}
