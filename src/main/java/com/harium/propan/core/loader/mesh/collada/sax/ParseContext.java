package com.harium.propan.core.loader.mesh.collada.sax;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.harium.propan.core.loader.mesh.collada.ColladaParser;
import com.harium.propan.core.loader.mesh.collada.model.ColladaScene;
import com.harium.propan.core.loader.mesh.collada.model.Contributor;
import com.harium.propan.core.loader.mesh.collada.node.GeometryNode;
import com.harium.propan.core.loader.mesh.collada.node.SourceNode;
import com.harium.propan.core.loader.mesh.collada.node.VerticesNode;
import com.harium.propan.core.model.Group;
import com.harium.propan.core.model.Model;

import java.util.ArrayList;
import java.util.List;

public class ParseContext {

    public String lastName;
    public String currentName;
    public String sourceId;
    public String currentId;
    public int count;
    public int accessorCount;

    public String currentPrimitive = ColladaParser.TRIANGLES;

    public Group currentGroup;
    public GeometryNode currentGeometry;
    public VerticesNode currentVertices;
    public SourceNode currentSource;
    public Contributor contributor;

    public List<Vector3> vertices = new ArrayList<Vector3>();
    public List<Vector3> normals = new ArrayList<Vector3>();
    public List<Vector2> textures = new ArrayList<Vector2>();

    public ColladaScene scene = new ColladaScene();
    public Model vbo = new Model();

    public int partsCount = 0;

    public StringBuilder floatBuilder = new StringBuilder();
    public StringBuilder triangleBuilder = new StringBuilder();

    public CharacterListener characterListener = null;


}
