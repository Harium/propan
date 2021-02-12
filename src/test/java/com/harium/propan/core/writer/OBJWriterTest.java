package com.harium.propan.core.writer;

import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.util.PathHelper;
import com.harium.propan.core.loader.mesh.OBJLoader;
import com.harium.propan.core.material.OBJMaterial;
import com.harium.propan.core.model.Face;
import com.harium.propan.core.model.Model;
import com.harium.propan.core.writer.obj.OBJWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public class OBJWriterTest {

    private static final String FILENAME = "test.obj";

    private Model vbo;

    @Before
    public void setUp() {
        vbo = new Model();

        vbo.getVertices().add(new Vector3(0, 0, 0));
        vbo.getVertices().add(new Vector3(0, 0, 1));
        vbo.getVertices().add(new Vector3(0, 1, 0));
        vbo.getVertices().add(new Vector3(0, 1, 1));
        vbo.getVertices().add(new Vector3(1, 0, 0));
        vbo.getVertices().add(new Vector3(1, 0, 1));
        vbo.getVertices().add(new Vector3(1, 1, 0));
        vbo.getVertices().add(new Vector3(1, 1, 1));

        vbo.getNormals().add(new Vector3(0, 0, 1));
        vbo.getNormals().add(new Vector3(0, 0, -1));
        vbo.getNormals().add(new Vector3(0, 1, 0));
        vbo.getNormals().add(new Vector3(0, -1, 0));
        vbo.getNormals().add(new Vector3(1, 0, 0));
        vbo.getNormals().add(new Vector3(-1, 0, 0));

        vbo.getFaces().add(new Face(3).addVertexes(1, 7, 5).addNormals(2, 2, 2));
        vbo.getFaces().add(new Face(3).addVertexes(1, 3, 7).addNormals(2, 2, 2));
        vbo.getFaces().add(new Face(3).addVertexes(1, 4, 3).addNormals(6, 6, 6));
        vbo.getFaces().add(new Face(3).addVertexes(1, 2, 4).addNormals(6, 6, 6));
        vbo.getFaces().add(new Face(3).addVertexes(3, 8, 7).addNormals(3, 3, 3));
        vbo.getFaces().add(new Face(3).addVertexes(3, 4, 8).addNormals(3, 3, 3));
        vbo.getFaces().add(new Face(3).addVertexes(5, 7, 8).addNormals(5, 5, 5));
        vbo.getFaces().add(new Face(3).addVertexes(5, 8, 6).addNormals(5, 5, 5));
        vbo.getFaces().add(new Face(3).addVertexes(1, 5, 6).addNormals(4, 4, 4));
        vbo.getFaces().add(new Face(3).addVertexes(1, 6, 2).addNormals(4, 4, 4));
        vbo.getFaces().add(new Face(3).addVertexes(2, 6, 8).addNormals(1, 1, 1));
        vbo.getFaces().add(new Face(3).addVertexes(2, 8, 4).addNormals(1, 1, 1));

        OBJMaterial material = new OBJMaterial();
        material.setName("material1");
        material.setKd(new Vector3(0.3f, 0.3f, 0.3f));
        vbo.addMaterial(material);

        OBJMaterial material2 = new OBJMaterial();
        material2.setName("material2");
        material2.setKd(new Vector3(0.5f, 0, 0));
        vbo.addMaterial(material2);
    }

    @Test
    public void testVBOWriter() throws IOException {
        String path = PathHelper.currentFileDirectory() + "/src/test/resources/";

        VBOWriter writer = new OBJWriter();
        writer.writeVBO(vbo, path + FILENAME);

        URL url = new URL(path + FILENAME);

        Model loaded = new OBJLoader().loadModel(url, FILENAME);

        Assert.assertEquals(8, loaded.getVertices().size());
        Assert.assertEquals(6, loaded.getNormals().size());
        Assert.assertEquals(12, loaded.getFaces().size());
    }

}
