package com.harium.propan.core.loader.mesh.collada;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.harium.propan.core.loader.mesh.collada.helper.ColladaParserHelper;
import com.harium.propan.core.loader.mesh.collada.model.ColladaScene;
import com.harium.propan.core.loader.mesh.collada.model.Contributor;
import com.harium.propan.core.loader.mesh.collada.node.*;
import com.harium.propan.core.loader.mesh.collada.sax.ParseContext;
import com.harium.propan.core.loader.mesh.collada.sax.ParseListener;
import com.harium.propan.core.loader.mesh.collada.sax.StdParser;
import com.harium.propan.core.loader.mesh.collada.sax.contributor.Author;
import com.harium.propan.core.loader.mesh.collada.sax.contributor.AuthoringTool;
import com.harium.propan.core.loader.mesh.collada.sax.scene.CreatedDate;
import com.harium.propan.core.loader.mesh.collada.sax.scene.ModifiedDate;
import com.harium.propan.core.model.Group;
import com.harium.propan.core.model.Model;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class ColladaParser extends DefaultHandler {

    public static final String GEOMETRY = "geometry"; //Equivalent to Object in OBJ
    public static final String SOURCE = "source";
    public static final String FLOAT_ARRAY = "float_array";
    public static final String VERTICES = "vertices";
    public static final String TRIANGLES = "triangles";
    public static final String LINES = "lines";
    public static final String PRIMITIVE = "p";
    public static final String INPUT = "input";
    public static final String ACCESSOR = "accessor";

    // Scene related
    public static final String CONTRIBUTOR = "contributor";
    public static final String AUTHOR = "author";

    public static final String ATTRIBUTE_COUNT = "count";
    public static final String ATTRIBUTE_MATERIAL = "material";
    public static final String ATTRIBUTE_OFFSET = "offset";
    public static final String ATTRIBUTE_SEMANTIC = "semantic";

    public static final String SEMANTIC_NORMAL = "NORMAL";
    public static final String SEMANTIC_POSITION = "POSITION";
    public static final String SEMANTIC_VERTEX = "VERTEX";
    public static final String SEMANTIC_TEXTCOORD = "TEXCOORD";

    private ParseContext context;
    private ParseListener listener;

    public ColladaParser() {
        context = new ParseContext();
        listener = new StdParser(context);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        context.lastName = context.currentName;
        context.currentName = qName;

        System.out.println("<" + qName + ">");

        if (GEOMETRY.equals(qName)) {
            String id = attributes.getValue("id");
            String name = attributes.getValue("name");
            context.currentGeometry = new GeometryNode(name);
            context.currentGroup = new Group(id);
            context.scene.geometries.put(id, context.currentGeometry);
        } else if (SOURCE.equals(qName)) {
            context.currentSource = new SourceNode();
            context.currentSource.id = attributes.getValue("id");

            context.currentGeometry.sources.put("#" + context.currentSource.id, context.currentSource);
        } else if (FLOAT_ARRAY.equals(qName)) {
            context.currentId = attributes.getValue("id");
            System.out.print(" (ID:" + context.currentId + ")");
            context.count = Integer.parseInt(attributes.getValue(ATTRIBUTE_COUNT));

            System.out.println("VertexSize: " + context.vertices.size());
            context.scene.sourceOffsets.put(context.sourceId, context.vertices.size());
            context.currentSource.floatArrayId = context.currentId;
        } else if (TRIANGLES.equals(qName)) {
            context.currentPrimitive = TRIANGLES;
            context.scene.inputs.clear();
            //Face Group
            String material = attributes.getValue(ATTRIBUTE_MATERIAL);
            context.count = Integer.parseInt(attributes.getValue(ATTRIBUTE_COUNT));
        } else if (LINES.equals(qName)) {
            context.currentPrimitive = LINES;
        } else if (VERTICES.equals(qName)) {
            context.currentId = attributes.getValue("id");
            context.currentVertices = new VerticesNode();
        } else if (ACCESSOR.equals(qName)) {
            AccessorNode accessorNode = new AccessorNode();
            accessorNode.count = Integer.parseInt(attributes.getValue("count"));
            accessorNode.source = attributes.getValue(SOURCE);
            accessorNode.stride = Integer.parseInt(attributes.getValue("stride"));

            context.currentSource.accessor = accessorNode;

        } else if (INPUT.equals(qName)) {
            InputNode input = ColladaParserHelper.parseInput(attributes);

            if (VERTICES.equals(context.lastName)) {

                if (SEMANTIC_POSITION.equals(input.semantic)) {
                    String sourceId = input.source;
                    SourceNode source = context.currentGeometry.sources.get(sourceId);
                    source.offsetPosition = context.vertices.size();
                    float[] array = source.floatArray;
                    AccessorNode accessor = source.accessor;

                    context.currentVertices.position = array;

                    //3d vector
                    if (accessor.stride == 3) {
                        List<Vector3> list = new ArrayList<Vector3>(accessor.count);
                        ColladaParserHelper.parseVertex3D(source, array, list);
                        context.vertices.addAll(list);
                    } else if (accessor.stride == 2) {
                        List<Vector2> list = new ArrayList<Vector2>(accessor.count);
                        ColladaParserHelper.parseVertex2D(source, array, list);
                        for (Vector2 v : list) {
                            context.vertices.add(new Vector3(v.x, v.y, 0));
                        }
                    }

                } else if (SEMANTIC_NORMAL.equals(input.semantic)) {
                    String sourceId = input.source;
                    SourceNode source = context.currentGeometry.sources.get(sourceId);
                    source.offsetNormal = context.normals.size();
                    float[] array = source.floatArray;

                    AccessorNode accessor = source.accessor;

                    context.currentVertices.normal = array;

                    if (accessor.stride == 3) {
                        List<Vector3> list = new ArrayList<Vector3>(accessor.count);
                        ColladaParserHelper.parseVertex3D(source, array, list);
                        context.normals.addAll(list);
                    } else if (accessor.stride == 2) {
                        List<Vector2> list = new ArrayList<Vector2>(accessor.count);
                        ColladaParserHelper.parseVertex2D(source, array, list);
                        for (Vector2 v : list) {
                            context.normals.add(new Vector3(v.x, v.y, 0));
                        }
                    }
                } else if (SEMANTIC_TEXTCOORD.equals(input.semantic)) {
                    String sourceId = input.source;
                    SourceNode source = context.currentGeometry.sources.get(sourceId);
                    source.offsetTexture = context.textures.size();
                    float[] array = source.floatArray;

                    AccessorNode accessor = source.accessor;

                    if (accessor.stride == 2) {
                        List<Vector2> list = new ArrayList<Vector2>(accessor.count);
                        ColladaParserHelper.parseVertex2D(source, array, list);
                        context.textures.addAll(list);
                    }
                }
            } else {
                context.scene.inputs.put(input.offset, input);
            }
        } else if (CONTRIBUTOR.equals(qName)) {
            context.contributor = new Contributor();
            context.scene.setContributor(context.contributor);
        } else if (Author.NAME.equals(qName)) {
            context.characterListener = new Author(context.scene);
        } else if (AuthoringTool.NAME.equals(qName)) {
            context.characterListener = new AuthoringTool(context.scene);
        } else if (CreatedDate.NAME.equals(qName)) {
            context.characterListener = new CreatedDate(context.scene);
        } else if (ModifiedDate.NAME.equals(qName)) {
            context.characterListener = new ModifiedDate(context.scene);
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (GEOMETRY.equals(qName)) {
            context.vbo.getVertices().addAll(context.vertices);
            context.vbo.getNormals().addAll(context.normals);
            context.vbo.getTextures().addAll(context.textures);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (context.characterListener != null) {
            context.characterListener.characters(ch, start, length);
            context.characterListener = null;
        }

        // Move to own characterListener
        String text = new String(ch, start, length);

        if (!hasText(text)) {
            return;
        }

        if (FLOAT_ARRAY.equals(context.currentName)) {
            text = fixLine(text);
            if (text.isEmpty())
                return;
            ((StdParser)listener).parseFloatArray(text);
        } else if (PRIMITIVE.equals(context.currentName)) {
            if (TRIANGLES.equals(context.currentPrimitive)) {
                text = fixLine(text);
                if (text.isEmpty())
                    return;
                ((StdParser)listener).parseTriangles(text);
            } else if (LINES.equals(context.currentPrimitive)) {
                //Ignore for now
            }
        }
    }

    private String fixLine(String line) {
        if (line.startsWith("\n")) {
            return line.replaceAll("\n", "").trim();
        }
        return line;
    }

    private boolean hasText(String string) {
        return !string.isEmpty();
    }

    public Model getModel() {
        return context.vbo;
    }

    public ColladaScene getScene() {
        return context.scene;
    }
}
